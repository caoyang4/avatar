package com.sankuai.avatar.resource.activity.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caoyang
 * @create 2023-03-15 16:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WindowPeriodRequestBO extends PageRequest {

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
