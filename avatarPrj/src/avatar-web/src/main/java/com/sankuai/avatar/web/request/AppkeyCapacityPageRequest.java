package com.sankuai.avatar.web.request;

import com.meituan.servicecatalog.api.annotations.FieldDoc;
import com.meituan.servicecatalog.api.annotations.Requiredness;
import com.meituan.servicecatalog.api.annotations.TypeDoc;
import com.sankuai.avatar.common.vo.PageRequest;
import lombok.*;

/**
 * @author caoyang
 * @create 2022-09-21 16:37
 */
@TypeDoc(description = "业务容灾查询参数")
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppkeyCapacityPageRequest extends PageRequest {

    /**
     * 是否返回全部字段
     */
    @FieldDoc(name = "isFullField", description = "是否返回全部字段", rule = "布尔值", requiredness = Requiredness.OPTIONAL, defaultValue = "false", example = "true")
    private Boolean isFullField;

    /**
     * 服务名称
     */
    @FieldDoc(name = "appkey", description = "appkey", requiredness = Requiredness.OPTIONAL, example = "com.sankuai.avatar.web")
    private String appkey;

    /**
     * set 名称：包含（set/liteset）
     */
    @FieldDoc(name = "setName", description = "链路名称", requiredness = Requiredness.OPTIONAL, example = "banma-exp")
    private String setName;

    /**
     * 容灾是否达标
     */
    @FieldDoc(name = "isCapacityStandard", description = "容灾是否达标", rule = "布尔值", requiredness = Requiredness.OPTIONAL, example = "true")
    private Boolean isCapacityStandard;

    /**
     * 根据 org 模糊搜索
     */
    @FieldDoc(name = "org", description = "部门id，用于模糊匹配",  requiredness = Requiredness.OPTIONAL, example = "1021866")
    private String org;

}
