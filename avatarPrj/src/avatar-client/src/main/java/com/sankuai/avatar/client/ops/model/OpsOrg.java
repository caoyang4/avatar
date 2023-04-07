package com.sankuai.avatar.client.ops.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

/**
 * OPS的ORG数据对象
 * @author zhangxiaoning07
 * @create 2022/10/27
 **/
@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpsOrg {
    /**
     * id
     */
    private Integer id;

    /**
     * org id
     */
    private Integer orgID;

    /**
     * 组织架构路径，从"公司/美团"开始
     */
    private String path;

}
