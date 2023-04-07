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
 * @create 2022-11-10 14:25
 */
@TypeDoc(
        description = "PaaS上报信息",
        fields = {
                @FieldDoc(name = "app", description = "白名单类型(capacity/容灾，idc/机房，auto-migration/自动迁移，utilization/资源利用率......)", requiredness = Requiredness.REQUIRED, example = "capcity"),
                @FieldDoc(name = "appkey", description = "appkey", requiredness = Requiredness.REQUIRED, example = "com.sankuai.avatar.web"),
                @FieldDoc(name = "reason", description = "加白原因", requiredness = Requiredness.OPTIONAL, example = "测试无需关注"),
                @FieldDoc(name = "inputUser", description = "加白申请人", requiredness = Requiredness.OPTIONAL, example = "cc"),
                @FieldDoc(name = "setName", description = "链路名称，支持多个链路", requiredness = Requiredness.REQUIRED, example = "banma-exp,banma-south"),
                @FieldDoc(name = "addTime", description = "生效起始时间", requiredness = Requiredness.REQUIRED, example = "1949-10-01 12:00:00"),
                @FieldDoc(name = "endTime", description = "生效截止时间", requiredness = Requiredness.REQUIRED, example = "9999-10-01 12:00:00"),
        }
)
@Data
public class ServiceWhitelistVO {

    private Integer id;

    /**
     * 白名单类型(capacity/容灾，idc/机房，auto-migration/自动迁移，utilization/资源利用率......)
     */
    @NotBlank(message = "白名单类型不允许为空")
    private String app;

    /**
     * 容灾类型中文名称
     */
    private String cname;

    /**
     * 加白原因
     */
    private String reason;

    /**
     * 加白 appkey
     */
    @NotBlank(message = "appkey不允许为空")
    private String appkey;

    /**
     * 加白 appkey 所属应用
     */
    private String application;

    /**
     * 加白 appkey 所属 org
     */
    private String orgIds;

    /**
     * 指定加白的 set
     */
    private String setName;

    /**
     * 加白申请人
     */
    @NotBlank(message = "加白申请人不能为空")
    private String inputUser;

    /**
     * 白名单添加时间
     */
    @NotNull(message = "请指定白名单生效起始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;

    /**
     * 白名单生效截止时间
     */
    @NotNull(message = "请指定白名单生效到期时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
}
