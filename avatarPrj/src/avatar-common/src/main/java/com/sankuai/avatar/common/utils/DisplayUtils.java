package com.sankuai.avatar.common.utils;

/**
 * @author qinwei05
 * @date 2023/2/14 17:05
 * @version 1.0
 */

public class DisplayUtils {

    private DisplayUtils() {
    }

    /**
     * 格式化URL跳转链接
     *
     * @param url      url
     * @param linkWord 关键字
     * @return {@link String}
     */
    public static String formatHrefLink(String url, String linkWord) {
        return String.format("'<a href=%s class=\"href-link\" target=\"_blank\">%s</a>'", url, linkWord);
    }
}
