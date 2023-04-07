package com.sankuai.avatar.workflow.server.utils;

import com.sankuai.avatar.workflow.server.dal.entity.CasUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zhaozhifan02
 */
public class UserUtils {
    public static CasUser getCurrentCasUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return (CasUser) session.getAttribute("CAS_USER");
    }

    public static void setCurrentCasUser(CasUser casUser) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("CAS_USER", casUser);
    }
}
