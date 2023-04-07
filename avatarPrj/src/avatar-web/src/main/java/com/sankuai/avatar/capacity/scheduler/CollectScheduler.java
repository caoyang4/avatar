package com.sankuai.avatar.capacity.scheduler;

import com.cip.crane.client.spring.annotation.Crane;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.sankuai.avatar.capacity.calculator.CalculatorResult;
import com.sankuai.avatar.capacity.collect.CollectChain;
import com.sankuai.avatar.capacity.dto.UtilizationOptimizeDTO;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.service.ICapacityCollectService;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.utils.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author chenxinli
 */
@Slf4j
@Configuration
@Component
public class CollectScheduler {

    private static final Logger logger = LoggerFactory.getLogger("logger_com.sankuai.avatar.web");

    @Autowired
    private ICapacityCollectService capacityCollect;

    @Crane("com.sankuai.avatar.web.capacity_mafka")
    public void capacityPush() throws Exception {
        List<List<AppKeyNode>> appKeyNodeList = capacityCollect.getAllCalculateNodes();
        List<UtilizationOptimizeDTO> utilizationOptimizeDTOList = Collections.synchronizedList(new ArrayList<>());
        log.info("本次采集count: {}", appKeyNodeList.size());

        ExecutorService pool = Executors.newFixedThreadPool(25, new NamedThreadFactory("Capacity-Push"));
        appKeyNodeList.forEach(appKeyNodes -> {
            pool.execute(() -> {
                List<UtilizationOptimizeDTO> utilization = new ArrayList<>();
                long dealStart = System.currentTimeMillis();
                for (AppKeyNode appKeyNode : appKeyNodes) {
                    String appkey = appKeyNode.getAppkey();
                    String set = StringUtils.isNotEmpty(appKeyNode.getSetName().getSetName())
                            ? appKeyNode.getSetName().getSetName()
                            : "主干道";
                    try {
                        capacityCollect.collectAttr(appKeyNode);
                    } catch (Exception e) {
                        logger.warn("appKey: {}, set:{}, 采集容灾属性异常, 异常{}", appkey, set, e.getMessage());
                        return;
                    }
                    CalculatorResult appKeyNodeCalculateResult = new CalculatorResult();
                    Transaction t = Cat.newTransaction("CapacityCalculate", appKeyNode.getAppkey());
                    try {
                        long start = System.currentTimeMillis();
                        appKeyNodeCalculateResult = capacityCollect.getAppKeyNodeCalculateResult(appKeyNode);
                        long cost = System.currentTimeMillis() - start;
                        Cat.logMetricForDuration("calculate", cost);
                        UtilizationOptimizeDTO utilOptimizeDTO = capacityCollect.getUtilOptimizeDTO(appKeyNodeCalculateResult);
                        utilization.add(utilOptimizeDTO);
                        if (utilOptimizeDTO.getIsNeedOptimize()){
                            utilizationOptimizeDTOList.add(utilOptimizeDTO);
                        }
                        t.setSuccessStatus();
                        t.addData(String.format("appkey: %s 容灾等级计算成功，耗时 %s ms", appKeyNode.getAppkey(), cost));
                        logger.info("appkey:{}, set:{}, result:{}", appkey, set, JsonUtil.bean2Json(utilOptimizeDTO));
                    } catch (Exception e) {
                        logger.warn("appKey:{} 计算容灾失败, 异常信息:{}", appkey, e.getMessage());
                        t.setStatus(e);
                        t.addData(String.format("appKey: %s 容灾等级计算失败, 异常信息：%s", appKeyNode.getAppkey(), e.getMessage()));
                    } finally {
                        t.complete();
                    }
                }
                if (CollectionUtils.isNotEmpty(utilization)) {
                    capacityCollect.produce(utilization);
                }
                Cat.logMetricForDuration("DealResult", (System.currentTimeMillis() - dealStart) / 1000);
            });
        });
        pool.shutdown();
        while (!pool.isTerminated()) {

        }
    }

    @Crane("com.sankuai.avatar.capacity.collect_cache_data")
    public void collectAndRefreshAllItemsData() {
        List<ICapacityCollectionItem> handlers = CollectChain.getHandlers();
        for (ICapacityCollectionItem handler : handlers) {
            try {
                handler.collect();
            } catch (Exception e) {
                logger.warn(String.format("%s collect data failed, msg: %s", handler.getClass().getName(), e.getMessage()));
                continue;
            }
            try {
                handler.refresh();
            } catch (Exception e) {
                logger.warn(String.format("%s refresh data failed, msg: %s", handler.getClass().getName(), e.getMessage()));
            }
        }
    }

}
