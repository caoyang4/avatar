package com.sankuai.avatar.capacity.core;

import com.sankuai.avatar.capacity.calculator.CalculatorResult;
import com.sankuai.avatar.capacity.node.AppKeyNode;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-17
 **/
public interface CalculateService {
    /**
     * appkey 计算
     * @param appkey 输入名称
     * @return list
     */
    List<CalculatorResult> calculate(String appkey);

    /**
     * appkeyNode 计算
     * @param appkeyNode 输入node
     * @return result
     * @throws Exception
     */
    CalculatorResult calculate(AppKeyNode appkeyNode) throws Exception;
}
