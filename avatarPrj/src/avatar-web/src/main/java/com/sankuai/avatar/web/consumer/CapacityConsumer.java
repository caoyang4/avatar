package com.sankuai.avatar.web.consumer;

import com.meituan.mafka.client.consumer.ConsumeStatus;
import com.meituan.mdp.boot.starter.mafka.consumer.anno.MdpMafkaMsgReceive;
import com.sankuai.avatar.capacity.dto.CapacityDTO;
import com.sankuai.avatar.capacity.dto.OpsCapacity;
import com.sankuai.avatar.capacity.dto.UtilizationOptimizeDTO;
import com.sankuai.avatar.web.dal.entity.CapacityDO;
import com.sankuai.avatar.web.service.AppkeyCapacityService;
import com.sankuai.avatar.web.service.ICapacityService;
import com.sankuai.avatar.web.transfer.CapacityTransfer;
import com.sankuai.avatar.web.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author chenxinli
 */
@Slf4j
@Service("CapacityConsumer")
public class CapacityConsumer {


    private final ICapacityService capacityService;
    private final AppkeyCapacityService appkeyCapacityService;

    @Autowired
    public CapacityConsumer(ICapacityService capacityService,
                            AppkeyCapacityService appkeyCapacityService) {

        this.capacityService = capacityService;
        this.appkeyCapacityService = appkeyCapacityService;
    }

    /**
     * 注意：服务端对单ip创建相同主题相同队列的消费者实例数有限制，超过100个拒绝创建.
     */
    @MdpMafkaMsgReceive
    protected ConsumeStatus consume(List<String> msgs) throws Exception {
        log.info("开始处理容灾数据：" + msgs.toString());
        Map<String, List<CapacityDO>> capacityMap = getCapacityMap(msgs);
        List<CapacityDO> insertList = Collections.synchronizedList(new ArrayList<>());
        List<CapacityDO> updateList = Collections.synchronizedList(new ArrayList<>());
        if (capacityMap.isEmpty()) {
            return ConsumeStatus.CONSUME_SUCCESS;
        }
        for (String appkey : capacityMap.keySet()) {
            List<CapacityDO> capacityDOList = capacityMap.get(appkey);
            capacityDOList.forEach(capacityDO -> {
                Boolean exist = appkeyCapacityService.isExistAppkeySetCapacity(capacityDO.getAppkey(), capacityDO.getSetName());
                if (Boolean.TRUE.equals(exist)) {
                    updateList.add(capacityDO);
                } else {
                    insertList.add(capacityDO);
                }
            });
        }

        boolean insertSuccess = false;
        boolean updateSuccess = false;
        List<String> appKeys = insertList.stream().map(CapacityDO::getAppkey).collect(Collectors.toList());
        if (!insertList.isEmpty()) {
            try {
                final Integer successInsertCount = capacityService.batchInsert(insertList);
                log.warn("[容灾信息] 新增{}个, appkeys: {}", insertList.size(), String.join(",", appKeys));
                insertSuccess = successInsertCount == insertList.size();
            } catch (Exception e) {
                log.error("批量新增appKeys {}失败, 失败详情{}", String.join(",", appKeys), e.getMessage());
            }
        } else {
            insertSuccess = true;
        }
        List<String> updateAppKeys = updateList.stream().map(CapacityDO::getAppkey).collect(Collectors.toList());
        if (!updateList.isEmpty()) {
            try {
                final Integer updateRet = capacityService.batchUpdate(updateList);
                log.warn("[容灾信息] 更新{}个, appkeys: {}", updateList.size(), String.join(",", appKeys));
                updateSuccess = updateRet == 1;
            } catch (Exception e) {
                log.error("批量更新appKeys {}失败, 失败详情{}", String.join(",", updateAppKeys), e.getMessage());
            }
        } else {
            updateSuccess = true;
        }
        return insertSuccess && updateSuccess ? ConsumeStatus.CONSUME_SUCCESS : ConsumeStatus.RECONSUME_LATER;
    }

    private Map<String, List<CapacityDO>> getCapacityMap(List<String> msgs) {
        Map<String, List<CapacityDO>> map = Collections.synchronizedMap(new HashMap<>());
        msgs.parallelStream().forEach(msgBody -> {
            try {
                UtilizationOptimizeDTO[] utilizationOptimizeDTOS = GsonUtils.deserialization(msgBody, UtilizationOptimizeDTO[].class);
                List<CapacityDTO> capacityDTOS = Arrays.asList(utilizationOptimizeDTOS);
                List<CapacityDO> capacityDOS = CapacityTransfer.INSTANCE.toDOs(capacityDTOS);
                System.out.println(capacityDTOS.get(0).getAppkey() +" 容灾数据: "+capacityDOS);
                map.put(capacityDTOS.get(0).getAppkey(), capacityDOS);
                updateOpsCapacity(capacityDTOS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return map;
    }
    
    private void updateOpsCapacity(List<CapacityDTO> capacityDTOS){
        int level = Integer.MAX_VALUE;
        OpsCapacity fromCapacityDTO = null;
        String appkey = null;
        boolean isTotalWhite = true;
        for (CapacityDTO capacityDTO : capacityDTOS) {
            if (!Boolean.TRUE.equals(capacityDTO.getIsWhite())) {
                isTotalWhite = false;
                break;
            }
        }
        for (CapacityDTO capacityDTO : capacityDTOS) {
            appkey = capacityDTO.getAppkey();
            if (capacityDTO.getCapacityLevel() != -1
                && capacityDTO.getCapacityLevel() < level
                && (isTotalWhite || !Boolean.TRUE.equals(capacityDTO.getIsWhite()))) {
                level = capacityDTO.getCapacityLevel();
                fromCapacityDTO = OpsCapacity.getFromCapacityDTO(capacityDTO);
                if(level == 0){break;}
            }
        }
        if (StringUtils.isNotEmpty(appkey) && fromCapacityDTO != null) {
            if (!appkeyCapacityService.updateOpsCapacity(appkey, fromCapacityDTO.getReason(), fromCapacityDTO.getCapacity())) {
                log.error("ops capacity更新失败, appkey: {}, data: {}", appkey, fromCapacityDTO);
            }
        }
    }
}
