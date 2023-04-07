package com.sankuai.avatar.resource.user.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.*;

import java.util.List;

/**
 * user 人员信息条件查询对象
 * @author caoyang
 * @create 2022-10-20 14:35
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestBO extends PageRequest {
    /**
     * user 主键 id
     */
    private Integer id;

    /**
     * 人员的大象 mis 号
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
