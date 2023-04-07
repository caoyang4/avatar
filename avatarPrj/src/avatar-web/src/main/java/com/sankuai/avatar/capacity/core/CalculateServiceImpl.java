package com.sankuai.avatar.capacity.core;

import com.sankuai.avatar.capacity.calculator.CalculatorResult;
import com.sankuai.avatar.capacity.collect.CollectChain;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.util.DayuUtils;
import com.sankuai.avatar.capacity.util.OpsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-17
 **/
@Service
public class CalculateServiceImpl implements CalculateService {
    @Autowired
    private AppkeyCalculate appkeyCalculate;
    @Autowired
    private AppkeySetCalculate appkeySetCalculate;

    @Autowired
    private CollectChain collectChain;

    @Override
    public List<CalculatorResult> calculate(String appKey) {
        List<CalculatorResult> calculatorResultList = new ArrayList<>();
        List<AppKeyNode> appKeyNodeList = OpsUtils.getSingleOpsCapacityService(appKey);
        appKeyNodeList.forEach(
                appKeyNode -> {
                    try {
                        collectChain.doHandle(appKeyNode);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CalculatorResult calculatorResult = new CalculatorResult();
                    try {
                        calculatorResult =  calculate(appKeyNode);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    calculatorResultList.add(calculatorResult);
                }
        );
        return calculatorResultList;
    }

    @Override
    public CalculatorResult calculate(AppKeyNode appkeyNode) throws Exception {
        // 是否计算
        boolean isCalculate = appkeyNode.getCalculate();
        CalculatorResult calculatorResult = CalculatorResult.builder().appKeyNode(appkeyNode).build();
        //主干道计算
        if (appkeyNode.getSetName().isDefault()) {
            calculatorResult = appkeyCalculate.calculateAppkeyNode(appkeyNode);
        }
        // lite set 干道计算 = 主干道
        if (appkeyNode.getSetName().isLiteSet()) {
            calculatorResult = appkeyCalculate.calculateAppkeyNode(appkeyNode);
        }
        // set 干道计算
        if (appkeyNode.getSetName().isSet()) {
            // set接入逻辑站点，遵循 set 容灾规范，否则遵循主干道容灾规范
            boolean exist = DayuUtils.isExistLogicSite(appkeyNode.getSetName().getSetName());
            if (exist) {
                calculatorResult = appkeySetCalculate.calculateAppkeyNode(appkeyNode);
            } else {
                calculatorResult = appkeyCalculate.calculateAppkeyNode(appkeyNode);
            }
        }
        calculatorResult.setIsCalculate(isCalculate);
        return calculatorResult;
    }
}
