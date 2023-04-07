package com.sankuai.avatar.web.request;

import com.meituan.servicecatalog.api.annotations.FieldDoc;
import com.meituan.servicecatalog.api.annotations.Requiredness;
import com.meituan.servicecatalog.api.annotations.TypeDoc;
import com.sankuai.avatar.common.vo.PageRequest;
import lombok.*;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-14 20:21
 */
@TypeDoc(description = "服务白名单查询参数")
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhitelistPageRequest extends PageRequest {

    @FieldDoc(name = "id", description = "主键id", requiredness = Requiredness.OPTIONAL, example = "233")
    private Integer id;

    /**
     * 白名单类型(capacity/容灾，idc/机房，auto-migration/自动迁移，utilization/资源利用率......)
     */
    @FieldDoc(name = "app", description = "白名单类型(capacity/容灾，idc/机房，auto-migration/自动迁移，utilization/资源利用率......)", requiredness = Requiredness.OPTIONAL, example = "capcity")
    private String app;

    /**
     * 加白 appkey
     */
    @FieldDoc(name = "appkeys", description = "多个服务批量查询", requiredness = Requiredness.OPTIONAL, example = "com.sankuai.avatar.web,com.sankuai.avatar.develop")
    private List<String> appkeys;

    /**
     * 加白 appkey 所属应用
     */
    @FieldDoc(name = "application", description = "加白 appkey 所属应用", requiredness = Requiredness.OPTIONAL, example = "avatar")
    private String application;

    /**
     * 加白 appkey 所属 org
     */
    @FieldDoc(name = "orgIds", description = "加白 appkey 所属 org", requiredness = Requiredness.OPTIONAL, example = "100046,150042,1573,150044,1021866")
    private String orgIds;

    /**
     * 按照 appkey 进行搜索
     */
    @FieldDoc(name = "query", description = "搜索加白appkey", requiredness = Requiredness.OPTIONAL, example = "avatar.web")
    private String query;

}
