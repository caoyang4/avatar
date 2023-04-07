package com.sankuai.avatar.web.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageRequest  extends PageRequest {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 组织id
     */
    private String orgId;

    /**
     * 用户名或mis号
     */
    private String loginName;

    /**
     * org编号全路径
     */
    private String orgPath;

    /**
     * 是否本部门
     */
    private Boolean isGroup;
}
