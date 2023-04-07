package com.sankuai.avatar.capacity.core;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.calculator.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
@Service
public class AppkeyCalculate extends AbstractCalculate {
    @Override
    public List<Calculator> getCalculatorList() {
        AppkeyCapacityNoCalculator appkeyCapacityNoCalculator = new AppkeyCapacityNoCalculator();
        AppkeyCapacitySpecialFiveCalculator appkeyCapacitySpecialFiveCalculator = new AppkeyCapacitySpecialFiveCalculator();
        AppkeyCapacityZeroCalculator appkeyCapacityZeroCalculator = new AppkeyCapacityZeroCalculator();
        AppkeyCapacityOneCalculator appkeyCapacityOneCalculator = new AppkeyCapacityOneCalculator();
        AppkeyCapacityTwoCalculator appkeyCapacityTwoCalculator = new AppkeyCapacityTwoCalculator();
        AppkeyCapacityThreeCalculator appkeyCapacityThreeCalculator = new AppkeyCapacityThreeCalculator();
        AppkeyCapacityElasticFiveCalculator appkeyCapacityElasticFiveCalculator = new AppkeyCapacityElasticFiveCalculator();
        AppkeyCapacityFourCalculator appkeyCapacityFourCalculator = new AppkeyCapacityFourCalculator();
        AppkeyCapacityFiveCalculator appkeyCapacityFiveCalculator = new AppkeyCapacityFiveCalculator();
        return Lists.newArrayList(
                appkeyCapacitySpecialFiveCalculator,
                appkeyCapacityNoCalculator,
                appkeyCapacityZeroCalculator, appkeyCapacityOneCalculator,
                appkeyCapacityTwoCalculator, appkeyCapacityElasticFiveCalculator,
                appkeyCapacityThreeCalculator,
                appkeyCapacityFourCalculator, appkeyCapacityFiveCalculator);
    }
}
