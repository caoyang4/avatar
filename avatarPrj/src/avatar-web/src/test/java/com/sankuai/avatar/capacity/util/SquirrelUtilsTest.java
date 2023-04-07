package com.sankuai.avatar.capacity.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class SquirrelUtilsTest {

    private static final String CATEGORY_KEY = "crane_appkeys";

    private static final String CLUSTER_NAME_KEY = "mdp.squirrel[0].clusterName";

    @Test
    public void getPropertyValue() {
        String clusterName = SquirrelUtils.getPropertyValue(CLUSTER_NAME_KEY);
        Assert.assertNotNull(clusterName);
        log.info("clusterName {}", clusterName);
    }

    @Test
    public void get() {
        String appKeys = SquirrelUtils.get(CATEGORY_KEY);
        Assert.assertNotNull(appKeys);
        log.info("crane appKeys {}", appKeys);
    }
}
