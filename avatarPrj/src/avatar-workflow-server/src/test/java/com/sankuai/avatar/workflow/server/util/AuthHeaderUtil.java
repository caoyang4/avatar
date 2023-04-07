package com.sankuai.avatar.workflow.server.util;

import com.sankuai.avatar.common.dto.MwsAuthUser;
import com.sankuai.avatar.common.utils.MwsHeader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import java.util.Date;

/**
 * 构建http头信息
 *  1，json类型
 *  2，添加字段 mws token
 *
 * @author xk
 */
public class AuthHeaderUtil {
    /**
     * 测试认证http头信息
     *
     * @param method http方法
     * @param uri uri
     * @return HttpHeaders
     */
    public static HttpHeaders authHeader(String method, String uri)  {
        HttpHeaders httpHeaders = null;
        try {
            httpHeaders = MwsHeader.getMwsHttpHeaders(method, getUrlPath(uri), new Date(), AuthUser());
            httpHeaders.set("Content-Type", "application/json;charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpHeaders;
    }

    /**
     * 测试认证用户
     */
    private static MwsAuthUser AuthUser() {
        MwsAuthUser mwsAuthUser = new MwsAuthUser();
        mwsAuthUser.setCode("0");
        mwsAuthUser.setLogin("kui.xu");
        mwsAuthUser.setName("许奎");
        return mwsAuthUser;
    }

    private static String getUrlPath(String url) {
        if (StringUtils.isNotEmpty(url)) {
            String[] split = url.split("\\?");
            return split[0];
        }
        return url;
    }
}
