package com.sankuai.avatar.common.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author caoyang
 * @create 2022-10-13 11:36
 */
public class ObjectUtilsTest {

    @Test
    public void testCheckObjAllFieldsIsNull() {
        Assert.assertTrue(ObjectUtils.checkObjAllFieldsIsNull(null));
    }

    @Test
    public void null2Empty() {
        Assert.assertEquals("", ObjectUtils.null2Empty(null));
    }

    @Test
    public void null2EmptyList() {
        Assert.assertEquals(0, ObjectUtils.null2EmptyList(null).size());
    }

    @Test
    public void null2zero(){
        Assert.assertEquals(0, ObjectUtils.null2zero(null));
    }
}