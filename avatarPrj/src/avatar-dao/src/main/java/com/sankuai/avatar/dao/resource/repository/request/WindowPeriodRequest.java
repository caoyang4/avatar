package com.sankuai.avatar.dao.resource.repository.request;

import lombok.Data;

/**
 * @author caoyang
 * @create 2023-03-15 16:01
 */
@Data
public class WindowPeriodRequest {

    private Integer id;

    /**
     * 窗口期名称
     */
    private String name;

    /**
     *  申请人
     */
    private String createUser;

}
