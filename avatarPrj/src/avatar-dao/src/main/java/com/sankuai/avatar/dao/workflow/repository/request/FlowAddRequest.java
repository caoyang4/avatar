package com.sankuai.avatar.dao.workflow.repository.request;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowTemplateEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Date;

/**
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlowAddRequest {

    private Integer id;

    private String uuid;

    private String status;

    private String createUser;

    private Object publicData = Collections.emptyMap();

    private Object logs = Collections.emptyMap();

    private Object config = Collections.emptyMap();

    private String input;

    private String appkey = "";

    private String srv = "";

    private String env;

    private String type = "dbQueue";

    private String approveUsers = "";

//    private String approveStatus = "AUTO_ACCESS";

    private Integer index = 0;

    private String objectName;

    private String objectType;

    private Date startTime = new Date();

    private FlowTemplateEntity flowTemplateEntity;
}
