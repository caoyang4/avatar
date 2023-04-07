package com.sankuai.avatar.web.vo.emergency;

import com.sankuai.avatar.resource.emergency.constant.OperationType;
import com.sankuai.avatar.web.dto.emergency.OfflineHostDTO;
import com.sankuai.avatar.web.dto.emergency.OnlineHostDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author caoyang
 * @create 2022-12-30 17:31
 */
@Data
public class EmergencyResourceVO {

    /**
     * 流程id
     */
    @NotNull
    private Integer flowId;

    /**
     * 流程uuid
     */
    @NotBlank(message = "flowUuid不能为空")
    private String flowUuid;

    /**
     * appkey
     */
    @NotBlank(message = "appkey不能为空")
    private String appkey;

    /**
     * 环境
     */
    @NotBlank(message = "env不能为空")
    private String env;

    /**
     * 流程模板名称
     */
    @NotBlank(message = "模版名称不能为空")
    private String template;

    /**
     * 机器数量
     */
    private Integer count;

    /**
     * 新增机器的配置数据
     */
    private OnlineHostDTO hostConfig;

    /**
     * 下线机器信息
     */
    private OfflineHostDTO offlineHost;

    /**
     * 紧急资源新增、归还
     */
    @NotNull
    private OperationType operationType;

    /**
     * 申请人
     */
    private String createUser;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
