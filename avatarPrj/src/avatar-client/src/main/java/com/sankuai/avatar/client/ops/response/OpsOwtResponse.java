package com.sankuai.avatar.client.ops.response;

import com.sankuai.avatar.client.ops.model.OpsOwt;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangxiaoning07
 * @create 2022/10/28
 **/
@Data
@EqualsAndHashCode(callSuper=true)
public class OpsOwtResponse extends OpsResponse {
    /**
     * AppkeyTreeOwt对象
     */
    private OpsOwt owt;
}
