package com.sankuai.avatar.workflow.server.filter;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.meituan.mdp.boot.starter.web.filter.MdpWebFilter;
import com.sankuai.avatar.common.dto.MwsAuthUser;
import com.sankuai.avatar.common.exception.AuthDeniedException;
import com.sankuai.avatar.common.exception.AuthRedirectException;
import com.sankuai.avatar.workflow.server.dal.entity.CasType;
import com.sankuai.avatar.workflow.server.dal.entity.CasUser;
import com.sankuai.it.sso.sdk.utils.WebUtil;
import com.sankuai.meituan.auth.util.UserUtils;
import com.sankuai.meituan.auth.vo.User;
import com.sankuai.oceanus.http.filter.OceanusHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
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

/**
 * @author zhaozhifan02
 */
@Slf4j
@MdpWebFilter(
        filterName = "AuthenticationFilter",
        urlPatterns = {"/api/v2/avatar/*", "/open/api/v2/avatar/*"},
        initParams = {},
        order = Integer.MAX_VALUE - 1
)
@Component
public class AuthenticationFilter implements Filter {
    @MdpConfig("IAM_TOKEN:client_secret")
    private static String mwsClientSecret;

    private List<AuthenticationStrategy> strategies;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        strategies = Lists.newArrayList();
        strategies.add(new MwsAuthenticationStrategy());
        strategies.add(new SsoAuthenticationStrategy());
        strategies.add(new HttpAuthenticationStrategy());
        strategies.add(new OtherSystemAuthenticationStrategy());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);

        CasUser casUser = null;
        for (AuthenticationStrategy strategy : strategies) {
            Transaction t = Cat.newTransaction("Authentication", strategy.getClass().getSimpleName());
            try {
                casUser = strategy.auth(req);
                if (casUser != null) {
                    t.setSuccessStatus();
                    break;
                }
            } catch (AuthRedirectException e) {
                res.sendError(401, e.getMessage());
                log.error("AuthRedirectException: ", e);
                t.setStatus(e);
            } catch (AuthDeniedException e) {
                t.setStatus(e);
                log.error("AuthDeniedException: ", e);
                res.sendError(403, e.getMessage());
            } catch (Exception e) {
                t.setStatus(e);
                log.error("Exception: ", e);
                res.sendError(500, e.getMessage());
            } finally {
                t.complete();
            }
        }

        if (casUser == null) {
            AuthDeniedException e = new AuthDeniedException();
            Cat.logError(e);
            log.error("AuthDeniedException: ", e);
            res.sendError(403, e.getMessage());
            return;
        }

        session.setAttribute("CAS_USER", casUser);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private static String getSign(String encryptStr) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKey secretKey = new SecretKeySpec(mwsClientSecret.getBytes(), "HmacSHA1");
            mac.init(secretKey);
            return Base64.encodeBase64String(mac.doFinal(encryptStr.getBytes()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to sign encryptStr: " + encryptStr, e);
        }
    }

    /**
     * 认证策略
     */
    private interface AuthenticationStrategy {
        /**
         * 认证，通过后返回
         *
         * @param request HttpServletRequest
         * @return CasUser
         */
        CasUser auth(HttpServletRequest request);
    }

    /**
     * SSO认证策略
     */
    private static class SsoAuthenticationStrategy implements AuthenticationStrategy {
        @Override
        public CasUser auth(HttpServletRequest request) {
            User user = null;
            List<Cookie> cookieList = WebUtil.getCookieContains(request, "ssoid");
            cookieList.add(WebUtils.getCookie(request, "sso_id"));
            for (Cookie cookie : cookieList) {
                try {
                    user = UserUtils.getUser(cookie.getValue());
                } catch (Exception e) {
                    continue;
                }
                if (user != null) {
                    break;
                }
            }

            if (user == null) {
                return null;
            }

            UserUtils.bind(user);

            CasUser casUser = new CasUser();
            casUser.setCode(user.getCode());
            casUser.setLoginName(user.getLogin());
            casUser.setName(user.getName());
            casUser.setCasType(CasType.MIS);
            log.info("SSo auth, uri: {}, Client user: {}", request.getRequestURI(), casUser);

            return casUser;
        }
    }

    /**
     * MWS认证策略
     */
    private static class MwsAuthenticationStrategy implements AuthenticationStrategy {
        @Override
        public CasUser auth(HttpServletRequest request) {
            String userBase64 = request.getHeader("PORTAL-PROXY-USER");
            if (userBase64 == null) {
                return null;
            }

            String sign = request.getHeader("PORTAL-PROXY-SIGN");
            String date = request.getHeader("PORTAL-PROXY-DATE");
            String userInfoString = new String(Base64.decodeBase64(userBase64));
            String encryptStr = request.getMethod() + " " + request.getRequestURI() + " " + date + " " + userBase64;
            String tmpSign = getSign(encryptStr);
            if (!sign.equals(tmpSign)) {
                log.warn("Request sign: {}, calculated sign: {}, encryptStr: {}, userInfo: {}",
                        sign, tmpSign, encryptStr, userInfoString);
                throw new AuthDeniedException();
            } else {
                log.info("Request sign: {}, calculated sign: {}, encryptStr: {}, userInfo: {}",
                        sign, tmpSign, encryptStr, userInfoString);
            }

            try {
                MwsAuthUser mwsAuthUser = new ObjectMapper().readValue(userInfoString, MwsAuthUser.class);

                User user = new User();
                user.setId(mwsAuthUser.getId());
                user.setLogin(mwsAuthUser.getLogin());
                user.setName(mwsAuthUser.getName());
                user.setCode(mwsAuthUser.getCode());

                UserUtils.bind(user);

                CasUser casUser = new CasUser();
                casUser.setCode(mwsAuthUser.getCode());
                casUser.setLoginName(mwsAuthUser.getLogin());
                casUser.setName(mwsAuthUser.getName());
                casUser.setCasType(CasType.MIS);
                log.info("Mws auth, uri: {}, Client user: {}", request.getRequestURI(), casUser);

                return casUser;
            } catch (IOException e) {
                throw new AuthDeniedException(e.getMessage());
            }
        }
    }

    /**
     * Oceanus HTTP认证策略, 提供服务认证
     */
    private static class HttpAuthenticationStrategy implements AuthenticationStrategy {
        @Override
        public CasUser auth(HttpServletRequest request) {
            String userBase64 = request.getHeader("oceanus-auth");
            if (userBase64 == null) {
                return null;
            }

            String appkey = OceanusHttpUtils.getClientAppkey();
            log.info("Uri: {}, Client appKey: {}", request.getRequestURI(), appkey);
            if (StringUtils.isBlank(appkey)) {
                return null;
            }

            CasUser casUser = new CasUser();
            casUser.setLoginName(String.format("__%s", appkey));
            casUser.setName(String.format("__%s", appkey));
            casUser.setCasType(CasType.APPKEY);

            return casUser;
        }
    }

    /**
     * 第三方系统认证策略
     */
    private static class OtherSystemAuthenticationStrategy implements AuthenticationStrategy {
        @Override
        public CasUser auth(HttpServletRequest request) {
            String accessToken = request.getHeader("access-token");
            log.info("Access token: {}", accessToken);
            if (accessToken == null) {
                return null;
            }

            try {
                User user = UserUtils.getUser(accessToken);
                log.info("Access token: {}, user: {}", accessToken, user);
                if (user == null) {
                    return null;
                }

                UserUtils.bind(user);

                CasUser casUser = new CasUser();
                casUser.setCode(user.getCode());
                casUser.setLoginName(user.getLogin());
                casUser.setName(user.getName());
                casUser.setCasType(CasType.MIS);

                return casUser;
            } catch (Exception e) {
                log.warn("Get user failed: ", e);
                return null;
            }
        }
    }
}

