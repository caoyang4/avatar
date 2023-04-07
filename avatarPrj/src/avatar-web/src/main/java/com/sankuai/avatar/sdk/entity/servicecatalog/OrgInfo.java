package com.sankuai.avatar.sdk.entity.servicecatalog;

import lombok.Data;

/**
 * @author Jie.li.sh
 * @create 2020-03-17
 **/

@Data
public class OrgInfo {
    /**
     * 服务数
     */
    Integer appKeyCount;
    /**
     * 应用数
     */
    Integer applicationCount;
    /**
     * 负责人
     */
    User leader;
}
