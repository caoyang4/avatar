package com.sankuai.avatar.web.vo.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author caoyang
 * @create 2023-01-17 10:51
 */
@Data
public class ApplicationRoleAdminVO {

    private Integer id;

    /**
     *  应用 id
     */
    @NotNull(message = "应用id不能为空")
    private Integer applicationId;

    /**
     *  应用名称
     */
    @NotBlank(message = "应用名称不能为空")
    private String applicationName;

    /**
     * 应用中文名称
     */
    private String applicationCnName;

    /**
     *  测试负责人
     */
    private String epAdmin;

    /**
     *  运维负责人
     */
    private String opAdmin;

    /**
     * 创建人
     */
    private String createUser;

    /**
     *  更新人
     */
    private String updateUser;

    /**
     *  org 路径
     */
    private String orgPath;

    /**
     *  org 路径名称
     */
    private String orgNamePath;

    /**
     *  更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     *  创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
