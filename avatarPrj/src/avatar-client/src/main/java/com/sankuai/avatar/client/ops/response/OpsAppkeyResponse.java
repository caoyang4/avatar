package com.sankuai.avatar.client.ops.response;

import com.sankuai.avatar.client.ops.model.OpsAppkey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author qinwei
 **/
@Data
@EqualsAndHashCode(callSuper=true)
public class OpsAppkeyResponse extends OpsResponse {
    /**
     * 服务数据
     */
    private OpsAppkey appkey;
}
