package com.sankuai.avatar.web.request;

import com.meituan.servicecatalog.api.annotations.FieldDoc;
import com.meituan.servicecatalog.api.annotations.Requiredness;
import com.meituan.servicecatalog.api.annotations.TypeDoc;
import com.sankuai.avatar.common.vo.PageRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author caoyang
 * @create 2022-09-21 16:39
 */
@TypeDoc(description = "PaaS容灾查询参数")
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppkeyPaasCapacityPageRequest extends PageRequest {

    /**
     * 大于 id
     */
    @FieldDoc(name = "idGtn", description = "标签id，过滤大于id的信息，优化查询，减少滚动分页", requiredness = Requiredness.OPTIONAL)
    private Integer idGtn;

    /**
     * appkey
     */
    @FieldDoc(name = "appkey", description = "客户端appkey", requiredness = Requiredness.OPTIONAL, example = "com.sankuai.avatar.web")
    private String appkey;

    /**
     * paas appkey
     */
    @FieldDoc(name = "paasAppkey", description = "paas appkey", requiredness = Requiredness.OPTIONAL)
    private String paasAppkey;

    /**
     * paas组件
     */
    @FieldDoc(name = "paasName", description = "paas名称，例如：Mafka，Eagle等", requiredness = Requiredness.OPTIONAL, example = "Eagle")
    private String paasName;

    /**
     * 容灾实体
     */
    @FieldDoc(name = "typeName", description = "容灾维度的实际标识名称，比如集群名、服务名等，后续字段以集群为例说明",  requiredness = Requiredness.OPTIONAL)
    private String typeName;

    /**
     * 容灾实体
     */
    @FieldDoc(name = "clientRole", description = "容灾实体角色，例如mafka可分为producer/consumer",  requiredness = Requiredness.OPTIONAL)
    private String clientRole;

    /**
     * 是否达标
     */
    @FieldDoc(name = "isCapacityStandard", description = "容灾是否达标", rule = "布尔值", requiredness = Requiredness.OPTIONAL, example = "true")
    private Boolean isCapacityStandard;

    /**
     * 是否加白
     */
    @FieldDoc(name = "isWhite", description = "是否是容灾白名单", rule = "是否是容灾白名单，是则传true，如果平台暂不支持白名单功能，传false即可", requiredness = Requiredness.OPTIONAL)
    private Boolean isWhite;

    /**
     * 是否 set 化
     */
    @FieldDoc(name = "isSet", description = "是否set化，默认false；传true，setName也要传", rule = "布尔值", requiredness = Requiredness.OPTIONAL, defaultValue = "false")
    private Boolean isSet;

    /**
     * set名称
     */
    @FieldDoc(name = "setName", description = "set链路名称，默认空串", rule = "isSet为true时，setName不能为空", requiredness = Requiredness.OPTIONAL, defaultValue = "")
    private String setName;

    /**
     * set类型
     */
    @FieldDoc(name = "setType", description = "set 类型（R/G/C），默认空串", rule = "限定R、G、C及其组合", requiredness = Requiredness.OPTIONAL, defaultValue = "")
    private String setType;

    /**
     * 更新日期
     */
    @FieldDoc(name = "updateDate", description = "上报日期", rule = "日期形式：2000-01-01", requiredness = Requiredness.OPTIONAL, example = "1949-10-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;


}
