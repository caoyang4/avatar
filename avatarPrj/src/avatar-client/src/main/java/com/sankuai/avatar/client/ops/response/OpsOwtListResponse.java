package com.sankuai.avatar.client.ops.response;

import com.sankuai.avatar.client.ops.model.OpsOwt;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zhangxiaoning07
 * @create 2022/10/28
 **/
@Data
@EqualsAndHashCode(callSuper=true)
public class OpsOwtListResponse extends OpsResponse{
    /**
     * AppkeyTreeOwt列表
     */
    private List<OpsOwt> owts;
}
