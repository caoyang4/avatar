package com.sankuai.avatar.web.util;

import com.sankuai.avatar.web.dal.entity.CasUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserUtils {

    public static CasUser getCurrentCasUser(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        CasUser casUser = (CasUser)session.getAttribute("CAS_USER");
        return casUser;
    }
}
