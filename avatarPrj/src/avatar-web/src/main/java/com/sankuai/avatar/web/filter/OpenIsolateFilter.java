package com.sankuai.avatar.web.filter;

import com.dianping.cat.Cat;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.meituan.mdp.boot.starter.web.filter.MdpWebFilter;
import com.sankuai.avatar.web.dal.entity.CasType;
import com.sankuai.avatar.web.dal.entity.CasUser;
import com.sankuai.avatar.web.exception.AuthDeniedException;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.oceanus.http.filter.OceanusHttpUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * 接口open鉴权隔离：服务类用户仅允许Open类接口, mis用户仅允许访问非Open类型
 * @author caoyang
 */
@MdpWebFilter(filterName = "OpenIsolateFilter", urlPatterns = {"/api/v2/avatar/*", "/open/api/*"}, order = 3)
@Component
public class OpenIsolateFilter implements Filter {
    /**
     * open 接口前缀
     */
    private static final String OPEN_API_PREFIX = "/open/api/";

    /**
     * open 接口隔离开关
     */
    @MdpConfig("OPEN_ISOLATE_SWITCH:false")
    private Boolean openIsolateSwitch;

    /**
     *  open 接口隔离的白名单
     */
    @MdpConfig("OPEN_ISOLATE_WHITELIST:[]")
    private String[] openIsolateWhiteList;


    @Override
    public void init(FilterConfig filterConfig) {
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
        try {
            openIsolate(req);
        } catch (AuthDeniedException e) {
            Cat.logError(e);
            res.sendError(403, e.getMessage());
            return;
        } catch (Exception e){
            Cat.logError(e);
            res.sendError(500, e.getMessage());
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
    /**
     * 隔离鉴权处理
     *
     * @param request 请求
     */
    private void openIsolate(HttpServletRequest request){
        // 鉴权隔离开关未打开，不进行管控
        if (!openIsolateSwitch) {
            return;
        }
        // 隔离open
        boolean isOpenUri = request.getRequestURI().startsWith(OPEN_API_PREFIX);
        CasUser casUser = UserUtils.getCurrentCasUser();
        if (Objects.isNull(casUser)) {
            throw new AuthDeniedException("鉴权失败，如有疑问请联系avatar团队");
        }
        // 白名单跳过
        String user = casUser.getCasType().equals(CasType.APPKEY)
                      ? OceanusHttpUtils.getClientAppkey()
                      : casUser.getLoginName();
        if (Arrays.asList(openIsolateWhiteList).contains(user)){
            return;
        }
        if (isOpenUri && casUser.getCasType().equals(CasType.MIS)) {
            throw new AuthDeniedException("普通Mis用户不允许调用open接口，如有疑问请联系avatar团队");
        }
        if (isOpenUri && casUser.getCasType().equals(CasType.MWS)) {
            throw new AuthDeniedException("前端MWS不允许调用open接口，如有疑问请联系avatar团队");
        }
        // 隔离非open
        if (!isOpenUri && casUser.getCasType().equals(CasType.APPKEY)) {
            throw new AuthDeniedException("服务不允许调用非open接口，如有疑问请联系avatar团队");
        }
    }

}
