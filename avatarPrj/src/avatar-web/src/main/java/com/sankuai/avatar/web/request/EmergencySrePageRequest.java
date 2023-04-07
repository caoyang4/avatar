package com.sankuai.avatar.web.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caoyang
 * @create 2023-02-01 17:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmergencySrePageRequest extends PageRequest {

    private Integer id;

    /**
     * 来源ID
     */
    private Integer sourceId;

    /**
     *  服务
     */
    private String appkey;

    /**
     *  状态
     */
    private String state;

}
