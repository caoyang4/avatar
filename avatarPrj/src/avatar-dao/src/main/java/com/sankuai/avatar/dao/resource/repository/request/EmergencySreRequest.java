package com.sankuai.avatar.dao.resource.repository.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author caoyang
 * @create 2023-01-17 15:33
 */
@Builder
@Data
public class EmergencySreRequest {

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
