package com.sankuai.avatar.web.dto.orgtree;

import com.sankuai.avatar.resource.orgtree.bo.OrgTreeNodeBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * OrgTree节点DTO对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/16
 **/
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrgTreeNodeDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 下层子节点
     */
    private List<OrgTreeNodeDTO> children;

    /**
     * 显示中文名称
     */
    private String displayName;

    /**
     * 是否有子节点
     */
    private Boolean hasChild;

    /**
     * 父节点
     */
    private List<OrgTreeNodeDTO> ancestors;

}
