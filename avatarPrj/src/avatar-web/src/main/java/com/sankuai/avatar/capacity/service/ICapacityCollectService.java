package com.sankuai.avatar.capacity.service;

import com.sankuai.avatar.capacity.calculator.CalculatorResult;
import com.sankuai.avatar.capacity.dto.CapacityDTO;
import com.sankuai.avatar.capacity.dto.UtilizationOptimizeDTO;
import com.sankuai.avatar.capacity.node.AppKeyNode;

import java.util.List;

/**
 * @author chenxinli
 */
public interface ICapacityCollectService {

    List<String> getAllAppKeys();

    /**
     * 获取所有AppKeyNode
     *
     * @return list
     */
    List<List<AppKeyNode>> getAllCalculateNodes();

    /**
     * 获取appKey的Nodes
     *
     * @param appKey appKey
     * @return list
     */
    List<AppKeyNode> getAppKeyNodes(String appKey);

    /**
     * /**
     * 采集容灾数据
     *
     * @param appKeyNode node
     * @return appKeyNode
     * @throws Exception exception
     */
    void collectAttr(AppKeyNode appKeyNode) throws Exception;

    /**
     * 获取计算结果
     *
     * @param appKey com.sankuai.avatar.web
     * @return List<CalculatorResult>
     */
    List<CalculatorResult> getCalculateResult(String appKey);

    /**
     * 获取计算结果by appKeyNode
     *
     * @param appKeyNode appKeyNode
     * @return result
     * @throws Exception 错误
     */
    CalculatorResult getAppKeyNodeCalculateResult(AppKeyNode appKeyNode) throws Exception;

    boolean isPaas(String appkey);

    /**
     * 处理结果
     *
     * @param calculatorResult result
     */
    void dealCalculateResult(CalculatorResult calculatorResult);

    void dealCalculateResult(List<CalculatorResult> calculatorResults);

    void produce(UtilizationOptimizeDTO utilizationOptimizeDTO);

    void produce(List<UtilizationOptimizeDTO> utilizationOptimizeDTOs);

    /**
     * result --> capacity dto
     *
     * @param calculatorResult results
     */
    UtilizationOptimizeDTO getCapacityDTO(CalculatorResult calculatorResult);

    UtilizationOptimizeDTO getUtilOptimizeDTO(CalculatorResult calculatorResult);

    UtilizationOptimizeDTO getUtilOptimizeDTO(UtilizationOptimizeDTO utilizationOptimizeDTO);
}
