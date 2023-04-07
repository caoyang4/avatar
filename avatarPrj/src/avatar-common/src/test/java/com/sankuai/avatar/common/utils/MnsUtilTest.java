package com.sankuai.avatar.common.utils;

import com.sankuai.sgagent.thrift.model.SGService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MnsUtilTest {

    @Test
    public void getAllOctoAppKeys() {
        MnsUtil mnsUtil = new MnsUtil();
        List<SGService> nodes = mnsUtil.getOriginOctoNodes("avatar-workflow-web", "http");
        System.out.println(nodes);
//        ArrayList<String> allOctoAppKeys = mnsUtil.getAllOctoAppKeys();
//        System.out.println(allOctoAppKeys.size());
    }
}