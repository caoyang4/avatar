package com.sankuai.avatar.resource.tree.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * OPS树结构
 *
 * @author qinwei05
 * @date 2022/12/18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppkeyTreeBO {

    /**
     * srv
     */
    private AppkeyTreeSrvBO srv;

    /**
     * pdl
     */
    private AppkeyTreePdlBO pdl;

    /**
     * owt
     */
    private AppkeyTreeOwtBO owt;

    /**
     * 集团
     */
    private AppkeyTreeBgBO corp;
}
