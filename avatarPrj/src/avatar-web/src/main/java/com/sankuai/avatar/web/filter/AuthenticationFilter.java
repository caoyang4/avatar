package com.sankuai.avatar.web.filter;

import com.dianping.cat.Cat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.meituan.mdp.boot.starter.web.filter.MdpWebFilter;
import com.sankuai.avatar.web.dal.entity.CasType;
import com.sankuai.avatar.web.dal.entity.CasUser;
import com.sankuai.avatar.web.dto.MwsAuthUser;
import com.sankuai.avatar.web.exception.AuthDeniedException;
import com.sankuai.avatar.web.exception.AuthRedirectException;
import com.sankuai.it.sso.sdk.utils.WebUtil;
import com.sankuai.meituan.auth.util.UserUtils;
import com.sankuai.meituan.auth.vo.User;
import com.sankuai.oceanus.http.filter.OceanusHttpUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@MdpWebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/api/v2/avatar/*", "/open/api/*"}, order = 2)
@Component
public class AuthenticationFilter implements Filter {

    private CasUser casUser;

    @MdpConfig("IAM_TOKEN:client_secret")
    private String mwsClientSecret;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 做过滤器
     *
     * @param request  请求
     * @param response 响应
     * @param chain    链
     * @throws IOException      io异常
     * @throws ServletException servlet异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);
        try {
            // sso、mws、oceanus鉴权
            if (ssoAuth(req) || mwsAuth(req) || httpAuth(req)){
                Cat.logEvent("WebAuth", casUser.getLoginName());
            } else {
                // 鉴权不通过
                throw new AuthDeniedException();
            }
        } catch (AuthRedirectException e) {
            res.sendError(401, e.getMessage());
            return;
        } catch (AuthDeniedException e) {
            Cat.logError(e);
            res.sendError(403, e.getMessage());
            return;
        } catch (Exception e){
            Cat.logError(e);
            res.sendError(500, e.getMessage());
            return;
        }
        session.setAttribute("CAS_USER", casUser);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    /**
     * http身份验证
     * oceanus http 认证
     *
     * @param req request
     * @return boolean
     */
    private boolean httpAuth(HttpServletRequest req) {
        String userBase64 = req.getHeader("oceanus-auth");
        if (userBase64 == null){
            return false;
        }
        String appkey = OceanusHttpUtils.getClientAppkey();
        if (StringUtils.isEmpty(appkey)) {
            return false;
        }
        casUser = new CasUser();
        casUser.setLoginName(String.format("__%s", appkey));
        casUser.setName(String.format("__%s", appkey));
        casUser.setCasType(CasType.APPKEY);
        return true;
    }

    /**
     * sso身份验证
     *
     * @param req 请求
     * @return boolean
     */
    private boolean ssoAuth(HttpServletRequest req) {
        User user = null;
        List<Cookie> cookieList = WebUtil.getCookieContains(req, "ssoid");
        cookieList.add(WebUtils.getCookie(req, "sso_id"));
        for (Cookie cookie: cookieList) {
            try {
                user = UserUtils.getUser(cookie.getValue());
            } catch (NullPointerException e) {
                continue;
            }
            if (user != null) {
                break;
            }
        }
        if (user == null) {
            return false;
        }
        casUser = new CasUser();
        casUser.setCode(user.getCode());
        casUser.setLoginName(user.getLogin());
        casUser.setName(user.getName());
        casUser.setCasType(CasType.MIS);
        return true;
    }

    /**
     * mws验证
     *
     * @param request 请求
     * @return boolean
     */
    private boolean mwsAuth(HttpServletRequest request) {
        String userBase64 = request.getHeader("PORTAL-PROXY-USER");
        if (userBase64 == null){
            return false;
        }
        String sign = request.getHeader("PORTAL-PROXY-SIGN");
        String date = request.getHeader("PORTAL-PROXY-DATE");
        String userInfoString = new String(Base64.decodeBase64(userBase64));
        String encryptStr = request.getMethod() + " " + request.getRequestURI() + " " + date + " " + userBase64;
        String tmpSign = getSign(encryptStr);
        if (!sign.equals(tmpSign)) {
            throw new AuthDeniedException();
        }
        try {
            MwsAuthUser mwsAuthUser = new ObjectMapper().readValue(userInfoString, MwsAuthUser.class);
            casUser = new CasUser();
            casUser.setCode(mwsAuthUser.getCode());
            casUser.setLoginName(mwsAuthUser.getLogin());
            casUser.setName(mwsAuthUser.getName());
            casUser.setCasType(CasType.MWS);
            return true;
        } catch (IOException e) {
            throw new AuthDeniedException(e.getMessage());
        }
    }

    /**
     * 获取签名
     *
     * @param encryptStr 加密串
     * @return {@link String}
     */
    private String getSign(String encryptStr) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKey secretKey = new SecretKeySpec(mwsClientSecret.getBytes(), "HmacSHA1");
            mac.init(secretKey);
            return Base64.encodeBase64String(mac.doFinal(encryptStr.getBytes()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to sign encryptStr: " + encryptStr, e);
        }
    }

}
