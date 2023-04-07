package com.sankuai.avatar.web.vo.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sankuai.avatar.resource.activity.constant.ResourceStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author caoyang
 * @create 2023-03-09 10:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResourceChangeVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 状态
     */
    private ResourceStatusType status;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

}
