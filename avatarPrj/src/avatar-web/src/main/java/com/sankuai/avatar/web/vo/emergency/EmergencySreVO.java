package com.sankuai.avatar.web.vo.emergency;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author caoyang
 * @create 2023-02-01 17:29
 */
@Data
public class EmergencySreVO {

    private Integer id;

    /**
     * 来源ID
     */
    private Integer sourceId;

    /**
     *  服务
     */
    @NotBlank(message = "appkey不能为空")
    private String appkey;

    /**
     *  SRE
     */
    private String opAdmin;

    /**
     *  临时新增SRE
     */
    private String attachAdmin;

    /**
     *  次数
     */
    private Integer time;

    /**
     *  状态
     */
    private String state;

    /**
     *  创建人
     */
    private String createUser;

    /**
     *  创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     *  更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
