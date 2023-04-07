package com.sankuai.avatar.web.request;

import com.meituan.servicecatalog.api.annotations.FieldDoc;
import com.meituan.servicecatalog.api.annotations.Requiredness;
import com.meituan.servicecatalog.api.annotations.TypeDoc;
import com.sankuai.avatar.common.vo.PageRequest;
import com.sankuai.avatar.resource.emergency.constant.OperationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author caoyang
 * @create 2022-12-30 17:20
 */
@TypeDoc(description = "紧急资源查询参数")
@EqualsAndHashCode(callSuper = true)
@Data
public class EmergencyResourcePageRequest extends PageRequest {

    @FieldDoc(name = "env", description = "环境", requiredness = Requiredness.OPTIONAL, example = "prod")
    private String env;

    @FieldDoc(name = "appkey", description = "appkey", requiredness = Requiredness.OPTIONAL, example = "com.sankuai.avatar.web")
    private String appkey;

    private OperationType operationType;

    @FieldDoc(name = "flowUuid", description = "流程uuid", requiredness = Requiredness.OPTIONAL)
    private String flowUuid;

    @FieldDoc(name = "template", description = "流程类型", requiredness = Requiredness.OPTIONAL, example = "service_expand")
    private String template;

    @FieldDoc(name = "startTime", description = "起始时间", requiredness = Requiredness.OPTIONAL, example = "1949-10-01 12:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @FieldDoc(name = "endTime", description = "截止时间", requiredness = Requiredness.OPTIONAL, example = "1999-01-01 12:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
