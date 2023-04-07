package com.sankuai.avatar.web.vo.orgtree;

import com.sankuai.avatar.web.dto.orgtree.OrgTreeUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrgTree组织信息VO对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/16
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrgTreeOrgInfoVO {
    /**
     * 服务数
     */
    private Integer appKeyCount;

    /**
     * 应用数
     */
    private Integer applicationCount;

    /**
     * 负责人
     */
    private OrgTreeUserDTO leader;
}
