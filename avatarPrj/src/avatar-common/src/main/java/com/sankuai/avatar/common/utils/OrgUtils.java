package com.sankuai.avatar.common.utils;

/**
 * @author Jie.li.sh
 * @create 2022-10-19
 **/
public class OrgUtils {
    private static final String SPLIT_CHAR = ",";

    public static String formatOrgIds(String orgIds) {
        if (orgIds.contains(SPLIT_CHAR)) {
            return orgIds.substring(orgIds.lastIndexOf(SPLIT_CHAR)+1);
        }
        return orgIds;
    }
}
