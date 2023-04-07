package com.sankuai.avatar.web.dto.application;

import com.sankuai.avatar.web.dto.orgtree.OrgTreeNodeDTO;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 团队DTO对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/24
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    /**
     *  组织架构节点
     */
    private OrgTreeNodeDTO org;

    /**
     * 用户列表
     */
    private List<OrgTreeUserDTO> users;

}
