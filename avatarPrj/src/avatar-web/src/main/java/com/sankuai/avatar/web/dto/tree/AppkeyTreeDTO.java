package com.sankuai.avatar.web.dto.tree;

import lombok.Data;


/**
 * OPS树返回体
 *
 * @author qinwei05
 * @date 2022/12/18
 */
@Data
public class AppkeyTreeDTO {

    /**
     * owt
     */
    private AppkeyTreeOwtDTO owt;

    /**
     * pdl
     */
    private AppkeyTreePdlDTO pdl;

    /**
     * srv
     */
    private AppkeyTreeSrvDTO srv;
}
