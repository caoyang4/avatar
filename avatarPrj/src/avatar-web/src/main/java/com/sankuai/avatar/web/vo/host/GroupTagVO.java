package com.sankuai.avatar.web.vo.host;

import lombok.Data;

/**
 * 业务分组对象属性
 *
 * @author qinwei05
 */
@Data
public class GroupTagVO {

    /**
     * 机器数目
     */
    private Long hostCount;

    /**
     * 业务分组名称
     */
    private String groupTagsName;
}
