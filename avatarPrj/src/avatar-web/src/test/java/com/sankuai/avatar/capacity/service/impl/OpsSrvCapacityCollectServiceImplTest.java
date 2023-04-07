package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.calculator.CalculatorResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author caoyang
 * @create 2022-07-12 14:45
 */

@Slf4j
public class OpsSrvCapacityCollectServiceImplTest extends com.sankuai.avatar.TestBase {

    @Autowired
    private OpsSrvCapacityCollectServiceImpl ops;

    @Test
    public void testGetCalculateResult() {
        String appkey = "avatar-workflow-web";
        CalculatorResult calculateResult = ops.getCalculateResult(appkey).get(0);
        log.info(calculateResult.getDescription());
        log.info("avatar-workflow-web: {}", calculateResult.getCapacityLevel().getNum());
    }
}
