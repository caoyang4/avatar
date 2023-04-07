package com.sankuai.avatar.dao.resource.repository.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * user 人员信息条件查询对象
 * @author caoyang
 * @create 2022-10-19 11:58
 */
@Builder
@Data
public class UserRequest {

    /**
     * user 主键 id
     */
    private Integer id;

    /**
     * 人员 mis
     */
    private List<String> misList;

    /**
     * 所在部门 id
     */
    private String orgId;

    /**
     * org编号路径
     */
    private String orgPath;

    /**
     * mis号或中文名模糊搜索
     */
    private String search;
}
