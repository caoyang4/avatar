package com.sankuai.avatar.web.request;

import lombok.Data;

/**
 * @author caoyang
 * @create 2023-01-10 10:37
 */
@Data
public class UserBgRequest {

    private String user;

    /**
     * 是否用于新增服务流程
     */
    private Boolean addSrv;

}
