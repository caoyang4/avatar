package com.sankuai.avatar.client.ops.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * OPS服务树订阅者（关注人）
 *
 * @author qinwei05
 * @date 2022/12/18
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class OpsSubscribersResponse extends OpsResponse {

    /**
     * subscribers
     */
    private List<String> subscribers;
}
