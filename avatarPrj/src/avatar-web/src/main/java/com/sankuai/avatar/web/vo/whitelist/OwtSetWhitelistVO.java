package com.sankuai.avatar.web.vo.whitelist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meituan.servicecatalog.api.annotations.FieldDoc;
import com.meituan.servicecatalog.api.annotations.Requiredness;
import com.meituan.servicecatalog.api.annotations.TypeDoc;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author caoyang
 * @create 2022-11-10 14:26
 */
@TypeDoc(
        description = "PaaS上报信息",
        fields = {
                @FieldDoc(name = "app", description = "白名单类型(capacity/容灾，idc/机房，auto-migration/自动迁移，utilization/资源利用率......)", requiredness = Requiredness.REQUIRED, example = "capcity"),
                @FieldDoc(name = "owt", description = "部门owt", requiredness = Requiredness.REQUIRED, example = "meituan.sre"),
                @FieldDoc(name = "setName", description = "链路名称", requiredness = Requiredness.REQUIRED, example = "banma-exp"),
                @FieldDoc(name = "reason", description = "加白原因", requiredness = Requiredness.OPTIONAL, example = "测试无需关注"),
                @FieldDoc(name = "applyBy", description = "加白申请人", requiredness = Requiredness.OPTIONAL, example = "cc"),
                @FieldDoc(name = "startTime", description = "生效起始时间", requiredness = Requiredness.REQUIRED, example = "1949-10-01 12:00:00"),
                @FieldDoc(name = "endTime", description = "生效截止时间", requiredness = Requiredness.REQUIRED, example = "9999-10-01 12:00:00"),
        }
)
@Data
public class OwtSetWhitelistVO {


    private Integer id;

    /**
     * 白名单类型(capacity/容灾，idc/机房，auto-migration/自动迁移，utilization/资源利用率......)
     */
    @NotBlank(message = "白名单类型不允许为空")
    private String app;

    /**
     * 加白所属 owt
     */
    @NotBlank(message = "owt不允许为空")
    private String owt;

    /**
     * 指定加白的 set
     */
    @NotBlank(message = "set不允许为空")
    private String setName;

    /**
     * 加白原因
     */
    private String reason;

    /**
     * 申请人
     */
    private String applyBy;

    /**
     * 白名单生效起始时间
     */
    @NotNull(message = "请指定白名单生效起始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 白名单生效截止时间
     */
    @NotNull(message = "请指定白名单生效到期时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

}
