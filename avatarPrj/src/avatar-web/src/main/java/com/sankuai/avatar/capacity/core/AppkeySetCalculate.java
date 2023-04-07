package com.sankuai.avatar.capacity.core;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.calculator.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-17
 **/
@Service
public class AppkeySetCalculate extends AbstractCalculate {
    @Override
    public List<Calculator> getCalculatorList() {
        AppkeySetCapacityNoCalculator appkeySetCapacityNoCalculator = new AppkeySetCapacityNoCalculator();
        AppkeySetCapacityZeroCalculator appkeySetCapacityZeroCalculator = new AppkeySetCapacityZeroCalculator();
        AppkeySetCapacityOneCalculator appkeySetCapacityOneCalculator = new AppkeySetCapacityOneCalculator();
        AppkeySetCapacityTwoCalculator appkeySetCapacityTwoCalculator = new AppkeySetCapacityTwoCalculator();
        AppkeySetCapacityThreeCalculator appkeySetCapacityThreeCalculator = new AppkeySetCapacityThreeCalculator();
        AppkeySetCapacityFourCalculator appkeySetCapacityFourCalculator = new AppkeySetCapacityFourCalculator();
        AppkeySetCapacityFiveCalculator appkeySetCapacityFiveCalculator = new AppkeySetCapacityFiveCalculator();
        return Lists.newArrayList(appkeySetCapacityNoCalculator,
                appkeySetCapacityZeroCalculator, appkeySetCapacityOneCalculator,
                appkeySetCapacityTwoCalculator, appkeySetCapacityThreeCalculator,
                appkeySetCapacityFourCalculator, appkeySetCapacityFiveCalculator
        );
    }
}
