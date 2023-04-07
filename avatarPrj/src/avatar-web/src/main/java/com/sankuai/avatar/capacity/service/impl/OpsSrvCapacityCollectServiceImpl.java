package com.sankuai.avatar.capacity.service.impl;

import com.dianping.cat.Cat;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.capacity.calculator.CalculatorResult;
import com.sankuai.avatar.capacity.collect.CollectChain;
import com.sankuai.avatar.capacity.constant.WhiteApp;
import com.sankuai.avatar.capacity.core.CalculateService;
import com.sankuai.avatar.capacity.dto.UtilizationOptimizeDTO;
import com.sankuai.avatar.capacity.mafka.Producer;
import com.sankuai.avatar.capacity.node.*;
import com.sankuai.avatar.capacity.service.ICapacityCollectService;
import com.sankuai.avatar.capacity.util.OpsUtils;
import com.sankuai.avatar.capacity.util.RocketUtil;
import com.sankuai.avatar.capacity.util.SquirrelUtils;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.web.service.AppkeyCapacityService;
import com.sankuai.avatar.web.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author chenxinli
 */
@Slf4j
@Service
public class OpsSrvCapacityCollectServiceImpl implements ICapacityCollectService {
    private static final Logger logger = LoggerFactory.getLogger("logger_com.sankuai.avatar.web");
    private static final String cKey = "appkey_cells";
    @Autowired
    private CalculateService calculateService;

    @Autowired
    private AppkeyCapacityService appkeyCapacityService;

    @Autowired
    private PaasCollectionItemImpl paasCollectionItem;

    @Autowired
    private CollectChain collectChain;

    @Autowired
    private Producer producer;

    @MdpConfig("IGNORE_PAAS_LIST:[]")
    private static String ignorePaasList;

    @Override
    public List<String> getAllAppKeys() {
        List<String> appKeys = Collections.synchronizedList(new ArrayList<>());
        List<Map<String, ?>> srvs = OpsUtils.getFullSrv();
        srvs.parallelStream().forEach((srv) -> {
            appKeys.add((String) srv.get("appkey"));
        });
        return appKeys;
    }

    @Override
    public List<List<AppKeyNode>> getAllCalculateNodes() {
        List<List<AppKeyNode>> appKeyNodeList = Collections.synchronizedList(new LinkedList<>());
        List<Map<String, ?>> srvs = OpsUtils.getFullSrv();
        srvs.parallelStream().forEach(
                (srv) -> {
                    List<AppKeyNode> list = new ArrayList<>();
                    String key = (String) srv.get("key");
                    String appKey = (String) srv.get("appkey");
                    if (StringUtils.isBlank(appKey)) {
                        return;
                    }
                    Boolean singleHostRestart = (Boolean) srv.get("single_host_restart");
                    // set节点
                    int hKey = appKey.hashCode() % 1000;
                    String hashKey = cKey + hKey;
                    Set<String> setList = SquirrelUtils.hget(hashKey, appKey);
                    if (CollectionUtils.isEmpty(setList)) {
                        setList =  RocketUtil.listSet(appKey);
                    }
                    // 主干道
                    setList.add("");
                    String owt = OpsUtils.getOwtFromSrv(key);
                    for (String name : setList) {
                        AppKeyNode appKeyNode = new AppKeyNode(appKey, key, owt, (String) srv.get("rank"), singleHostRestart, new SetName(name));
                        list.add(appKeyNode);
                    }
                    appKeyNodeList.add(list);
                }
        );
        return appKeyNodeList;
    }

    @Override
    public List<AppKeyNode> getAppKeyNodes(String appKey) {
        return OpsUtils.getSingleOpsCapacityService(appKey);
    }

    @Override
    public void collectAttr(AppKeyNode appKeyNode) throws Exception {
        collectChain.doHandle(appKeyNode);
    }

    @Override
    public List<CalculatorResult> getCalculateResult(String appKey) {
        List<CalculatorResult> results = new ArrayList<>();
        List<AppKeyNode> appKeyNodes = getAppKeyNodes(appKey);
        appKeyNodes.forEach(appKeyNode -> {
            try {
                collectAttr(appKeyNode);
                CalculatorResult result = this.getAppKeyNodeCalculateResult(appKeyNode);
                results.add(result);
                String set = appKeyNode.getSetName().isDefault() ? "主干道" : appKeyNode.getSetName().getSetName();
                logger.info("appkey:{}, set:{}, result:{}", appKey, set, JsonUtil.bean2Json(result));
            } catch (Exception e) {
                Cat.logError(e);
            }
        });
        return results;
    }

    @Override
    public CalculatorResult getAppKeyNodeCalculateResult(AppKeyNode appKeyNode) throws Exception {
        return calculateService.calculate(appKeyNode);
    }

    @Override
    public boolean isPaas(String appkey) {
        List<AppKeyNode> appKeyNodes = getAppKeyNodes(appkey);
        if (appKeyNodes.isEmpty()) {
            return false;
        }
        AppKeyNode appKeyNode = appKeyNodes.get(0);
        paasCollectionItem.setAppkeyCapacityProperty(appKeyNode);
        return appKeyNode.isPaas() && !appKeyNode.getCalculate();
    }

    @Override
    public void produce(UtilizationOptimizeDTO utilizationOptimizeDTO) {
        try {
            log.info(String.format("push msg: %s", utilizationOptimizeDTO.toString()));
            producer.sendMsg(utilizationOptimizeDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void produce(List<UtilizationOptimizeDTO> utilizationOptimizeDTOs) {
        try {
            log.info(String.format("push msg: %s", utilizationOptimizeDTOs.toString()));
            producer.sendMsg(utilizationOptimizeDTOs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dealCalculateResult(CalculatorResult calculatorResult) {
        UtilizationOptimizeDTO utilOptimizeDTO = getUtilOptimizeDTO(calculatorResult);
        try {
            producer.sendMsg(utilOptimizeDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dealCalculateResult(List<CalculatorResult> calculatorResults) {
        if (CollectionUtils.isEmpty(calculatorResults)) {
            return;
        }
        List<UtilizationOptimizeDTO> utilizationOptimizeDTOS = new ArrayList<>();
        for (CalculatorResult calculatorResult : calculatorResults) {
            utilizationOptimizeDTOS.add(getUtilOptimizeDTO(calculatorResult));
        }
        try {
            producer.sendMsg(utilizationOptimizeDTOS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public UtilizationOptimizeDTO getUtilOptimizeDTO(CalculatorResult calculatorResult) {
        UtilizationOptimizeDTO utilizationOptimizeDTO = this.getCapacityDTO(calculatorResult);
        return getUtilOptimizeDTO(utilizationOptimizeDTO);
    }

    @Override
    public UtilizationOptimizeDTO getUtilOptimizeDTO(UtilizationOptimizeDTO utilizationOptimizeDTO) {
        patchUtilizationOptimizeData(utilizationOptimizeDTO);
        if (utilizationOptimizeDTO.getIsNeedOptimize()) {
            calculateUtilOptimize(utilizationOptimizeDTO);
        }
        return utilizationOptimizeDTO;
    }

    private void calculateUtilOptimize(UtilizationOptimizeDTO util) {
        util.setCpuSumCount(util.getHosts().stream().map(Host::getCpuCount).reduce(Integer::sum).get());
        util.setMemSumCount(util.getHosts().stream().map(Host::getMemSize).reduce(Integer::sum).get());
        util.setMemPerCount(util.getHosts().stream().mapToInt(Host::getMemSize).average().getAsDouble());
        util.setCpuPerCount(util.getHosts().stream().mapToInt(Host::getCpuCount).average().getAsDouble());
        int hostSumCount = util.getHosts().size();
        Double yearPeekValue = util.getResourceUtil().getYearPeekValue();
        double utilTargetValue = "".equals(util.getCellName()) ? 0.3 : 0.2;
        double optimizeTargetValue = 1 - yearPeekValue / utilTargetValue;
        double canReduceHostCount = Math.floor(hostSumCount * (optimizeTargetValue));
        double canReduceCpuCount = Math.floor(util.getCpuSumCount() * optimizeTargetValue);
        double canReduceMemCount = Math.floor(util.getMemSumCount() * optimizeTargetValue);
        util.setReduceHostCount((int) canReduceHostCount);
        util.setReduceCpuCount((int) canReduceCpuCount);
        util.setReduceMemCount((int) canReduceMemCount);
        String optimizeMsg = "";
        if (canReduceHostCount >= 1) {
            String hulkLink = String.format("<a href=%s target='_blank'>弹性伸缩</a>", "http://sc.sankuai.com/#/wheel/homepage?id=5861");
            String nestLink = String.format("<a href=%s target='_blank'>Nest平台</a>", "http://sc.sankuai.com/#/wheel/homepage?id=6930");
            if (util.isSet()) {
                optimizeMsg = String.format("此服务周资源利用率未达标，%s set建议下线%s核%sG机器%s台,并开启%s或者接入%s", util.getCellName(), util.getCpuPerCount(), util.getMemPerCount(), (int) canReduceHostCount, hulkLink, nestLink);
            } else {
                optimizeMsg = String.format("此服务周资源利用率未达标，建议下线%s核%sG机器%s台,并开启%s或者接入%s", util.getCpuPerCount(), util.getMemPerCount(), (int) canReduceHostCount, hulkLink, nestLink);
            }
        }
        util.setOptimizeMsg(optimizeMsg);
    }

    private void patchUtilizationOptimizeData(UtilizationOptimizeDTO utilDTO) {
        long cpuUniqueCount = utilDTO.getHosts().stream().map(Host::getCpuCount).distinct().count();
        long memUniqueCount = utilDTO.getHosts().stream().map(Host::getMemSize).distinct().count();
        if (CollectionUtils.isNotEmpty(utilDTO.getHosts()) && (cpuUniqueCount != 1 || memUniqueCount != 1)) {
            utilDTO.setIsUniqueConfig(false);
            String url = "https://seer.mws.sankuai.com/risk?id=53&plugin_id=1010&type=view";
            String hrefLink = String.format("<a href=%s class='href-link' target='_blank'>先知风险</a>", url);
            utilDTO.setOptimizeMsg("同服务下主机配置不一致，无法输出准确的优化建议，详见" + hrefLink);
        } else {
            utilDTO.setIsUniqueConfig(true);
        }

        Double yearPeekValue = utilDTO.getResourceUtil().getYearPeekValue();
        double utilTargetValue = "".equals(utilDTO.getCellName()) ? 0.3 : 0.2;
        if ("UN_STANDARD".equals(utilDTO.getUtilizationStandard()) && Boolean.FALSE.equals(utilDTO.getStateful())
                && utilDTO.getHosts().size() >= 8 && utilDTO.getIsUniqueConfig()
                && (yearPeekValue / utilTargetValue) < 1) {
            utilDTO.setIsNeedOptimize(true);
        } else {
            utilDTO.setIsNeedOptimize(false);
        }
    }

    private boolean ignorePaasOwt(String srv){
        List<String> paasList = Arrays.asList(GsonUtils.deserialization(ignorePaasList, String[].class));
        for (String owt : paasList) {
            if (srv.startsWith(owt)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UtilizationOptimizeDTO getCapacityDTO(CalculatorResult calculatorResult) {
        UtilizationOptimizeDTO capacity = new UtilizationOptimizeDTO();
        Integer currentLevel;
        if (calculatorResult.getAppKeyNode().getPaas() && !calculatorResult.getAppKeyNode().getCalculate()
            && !ignorePaasOwt(calculatorResult.getAppKeyNode().getSrv())) {
            Map<String, ?> srv = OpsUtils.getSrvByAppKey(calculatorResult.getAppKeyNode().getAppkey());
            currentLevel = -1;
            if (srv.get("capacity") != null) {
                Double capacityLevel = (Double) srv.get("capacity");
                String reason = (String) srv.get("capacity_reason");
                calculatorResult.setReason(reason);
                currentLevel = capacityLevel.intValue();
            }
        } else {
            currentLevel = calculatorResult.getCapacityLevel().getNum();
        }
        Integer standardLevel = calculatorResult.getAppKeyNode().getStandardLevel();
        boolean isCapacityStandard;
        Boolean hasCapacityWhite = calculatorResult.getAppKeyNode().isWhiteApp(WhiteApp.CAPACITY);
        Boolean hasUtilWhite = calculatorResult.getAppKeyNode().isWhiteApp(WhiteApp.UTILIZATION);
        Double lastWeekValueWithoutSet = calculatorResult.getAppKeyNode().getResourceUtil().getLastWeekValueWithoutSet();
        int hostCount = calculatorResult.getAppKeyNode().getHostList().size();
        isCapacityStandard = hasCapacityWhite || currentLevel >= standardLevel || currentLevel == -1;
        if (hasCapacityWhite || hasUtilWhite || hostCount <= 2 || !isCapacityStandard || lastWeekValueWithoutSet <= 0) {
            capacity.setUtilizationStandard(UtilizationStandard.SKIP_STANDARD.name());
        } else if (lastWeekValueWithoutSet < 0.2) {
            capacity.setUtilizationStandard(UtilizationStandard.UN_STANDARD.name());
        } else {
            capacity.setUtilizationStandard(UtilizationStandard.STANDARD.name());
        }
        capacity.setIsWhite(hasCapacityWhite);
        capacity.setAppkey(calculatorResult.getAppKeyNode().getAppkey());
        capacity.setOrgPath(calculatorResult.getAppKeyNode().getOrgPath());
        capacity.setOrgDisplayName(calculatorResult.getAppKeyNode().getOrgDisplayName());
        capacity.setStateful(calculatorResult.getAppKeyNode().getStateful());
        capacity.setCapacityLevel(currentLevel);
        capacity.setCellName(calculatorResult.getAppKeyNode().getSetName().getSetName());
        capacity.setStandardLevel(standardLevel);
        capacity.setIsCapacityStandard(isCapacityStandard);
        capacity.setResourceUtil(calculatorResult.getAppKeyNode().getResourceUtil());
        capacity.setStandardTips(calculatorResult.getSuggestion());
        String reason = calculatorResult.getReason();
        if (hasCapacityWhite && (currentLevel < standardLevel)){
            reason = "【该服务链路已加入容灾等级白名单，默认达标】，真实容灾判定信息："+reason;
        }
        capacity.setStandardReason(reason);
        capacity.setWhitelists(calculatorResult.getAppKeyNode().getWhiteInfoList());
        capacity.setHosts(calculatorResult.getAppKeyNode().getHostList());
        capacity.setMiddleware(calculatorResult.getAppKeyNode().getMiddleWareInfoList());
        capacity.setOctoHttpProvider(calculatorResult.getAppKeyNode().getOctoHttpProviderList());
        capacity.setOctoThriftProvider(calculatorResult.getAppKeyNode().getOctoThriftProviderList());
        capacity.setIsPaas(calculatorResult.getAppKeyNode().getPaas());
        capacity.setCanSingleHostRestart(calculatorResult.getAppKeyNode().getSingleHostRestart());
        List<AccessComponent> accessComponentList = new ArrayList<>();
        accessComponentList.add(AccessComponent.builder()
                .name("elastic")
                .cName("弹性伸缩")
                .access(calculatorResult.getAppKeyNode().isElastic())
                .build());
        accessComponentList.add(AccessComponent.builder()
                .name("plus")
                .cName("是否plus发布过")
                .access(calculatorResult.getAppKeyNode().isPlusDeployed())
                .build());
        accessComponentList.add(AccessComponent.builder()
                .name("nest")
                .cName("是否为nest服务")
                .access(calculatorResult.getAppKeyNode().getNest())
                .build());
        capacity.setAccessComponent(accessComponentList);
        capacity.setUpdateBy("avatar");
        capacity.setUpdateTime(new Date());
        return capacity;
    }
}
