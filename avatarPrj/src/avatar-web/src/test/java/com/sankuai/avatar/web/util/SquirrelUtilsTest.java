package com.sankuai.avatar.web.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;


@Slf4j
public class SquirrelUtilsTest {

    private static final String CATEGORY_KEY = "dx_avatar_url";

    private static final String MIS = "zhaozhifan02";

    private static final String CLUSTER_NAME_KEY = "mdp.squirrel[0].clusterName";

    private static final String AVATAR_URL = "https://s3plus-img.meituan.net/v1/" +
            "mss_491cda809310478f898d7e10a9bb68ec/profile11/38ddef08-71d2-4ffa-84d4-e2634140d247";

    @Test
    public void getPropertyValue() {
        String clusterName = SquirrelUtils.getPropertyValue(CLUSTER_NAME_KEY);
        Assert.assertNotNull(clusterName);
        log.info("clusterName {}", clusterName);
    }

    @Test
    public void set() {
        String key = String.format("%s_%s", CATEGORY_KEY, MIS);
        SquirrelUtils.set(key, AVATAR_URL);
        log.info("set {} value {} success", key, AVATAR_URL);
    }

    @Test
    public void get() {
        String avatarUrl = SquirrelUtils.get(String.format("%s_%s", CATEGORY_KEY, MIS));
        Assert.assertNotNull(avatarUrl);
        log.info("{} user avatarUrl {}", MIS, avatarUrl);
    }
}
