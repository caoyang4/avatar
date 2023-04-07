package com.sankuai.avatar.web.dto.orgtree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrgTree组织信息DTO对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/16
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrgTreeOrgInfoDTO {
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
