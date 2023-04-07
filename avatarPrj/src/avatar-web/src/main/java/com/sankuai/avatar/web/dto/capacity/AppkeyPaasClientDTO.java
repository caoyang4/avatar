package com.sankuai.avatar.web.dto.capacity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * appkey 依赖的 paas 客户端信息 dto
 * @author caoyang
 * @create 2022-10-13 13:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppkeyPaasClientDTO {

    /**
     * paas 名称
     */
    private String paasName;
    /**
     * 业务 appkey
     */
    private String appkey;
    /**
     * 客户端语言
     */
    private String language;
    /**
     * 客户端groupId
     */
    private String groupId;
    /**
     *
     */
    private String artifactId;
    /**
     * 当前所使用的客户端版本
     */
    private String version;
    /**
     * 客户端达标版本
     */
    private String standardVersion;

    /**
     * 是否达标
     */
    private Boolean isCapacityStandard;

    /**
     * 上报人
     */
    private String updateBy;

    public String getVersionDesc(){
        return String.format("%s-%s/%s以上", language, version, standardVersion);
    }
}
