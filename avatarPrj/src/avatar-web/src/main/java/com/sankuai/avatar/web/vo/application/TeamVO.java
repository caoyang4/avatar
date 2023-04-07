package com.sankuai.avatar.web.vo.application;

import com.sankuai.avatar.web.vo.orgtree.OrgTreeNodeVO;
import com.sankuai.avatar.web.vo.orgtree.OrgTreeUserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 团队VO对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/24
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamVO {
    /**
     *  组织架构节点
     */
    private OrgTreeNodeVO org;

    /**
     * 用户列表
     */
    private List<OrgTreeUserVO> users;

}
