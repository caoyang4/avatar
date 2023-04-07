package com.sankuai.avatar.workflow.server.vo.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 流程首页vo对象
 * @author caoyang
 * @create 2023-02-21 14:13
 */
@Data
public class FlowHomeVO {

    private Integer id;

    private String appkey;

    private String applyId;

    private String applyName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyTime;

    private String applyUser;

    private String applyUserImage;

    private String approveStatus;

    private List<String> approveUsers;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private String env;

    private List<String> keyword;

    private String status;

    private String reason;
}
