package com.sankuai.avatar.web.request;

import com.meituan.servicecatalog.api.annotations.FieldDoc;
import com.meituan.servicecatalog.api.annotations.Requiredness;
import com.meituan.servicecatalog.api.annotations.TypeDoc;
import com.sankuai.avatar.common.vo.PageRequest;
import lombok.*;

/**
 * @author caoyang
 * @create 2022-11-15 11:40
 */
@TypeDoc(description = "owt-set白名单查询参数")
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwtSetWhitelistPageRequest extends PageRequest {

    @FieldDoc(name = "id", description = "主键id", requiredness = Requiredness.OPTIONAL, example = "233")
    private Integer id;

    /**
     * 白名单类型(capacity/容灾，idc/机房，auto-migration/自动迁移，utilization/资源利用率......)
     */
    @FieldDoc(name = "app", description = "白名单类型(capacity/容灾，idc/机房，auto-migration/自动迁移，utilization/资源利用率......)", requiredness = Requiredness.OPTIONAL, example = "capcity")
    private String app;

    /**
     * 加白所属 owt
     */
    @FieldDoc(name = "owt", description = "部门owt", requiredness = Requiredness.OPTIONAL, example = "meituan.sre")
    private String owt;

    /**
     * 指定加白的 set
     */
    @FieldDoc(name = "setName", description = "链路名称", requiredness = Requiredness.OPTIONAL, example = "banma-exp")
    private String setName;
}
