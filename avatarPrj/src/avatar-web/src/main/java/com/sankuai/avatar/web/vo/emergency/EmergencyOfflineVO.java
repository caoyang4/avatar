package com.sankuai.avatar.web.vo.emergency;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author caoyang
 * @create 2022-12-30 16:43
 */
@Data
public class EmergencyOfflineVO {

    private String cell;

    private String ip;

    private String name;

    /**
     * 环境
     */
    private String env;

    /**
     * appkey
     */
    private String appkey;

    /**
     * 流程模板
     */
    private String template;

    /**
     * 服务名称
     */
    private String displaySrv;

    private String flowUuid;

    private Integer flowId;

    private Integer cpu;

    private Integer memory;

    private Integer disk;

    private String kind;

    private String os;

    private String idc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date offlineDate;

}
