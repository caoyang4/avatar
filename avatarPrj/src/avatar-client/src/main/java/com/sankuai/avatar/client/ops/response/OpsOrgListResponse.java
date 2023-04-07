package com.sankuai.avatar.client.ops.response;

import com.sankuai.avatar.client.ops.model.OpsOrg;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zhangxiaoning07
 * @create 2022/10/28
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class OpsOrgListResponse extends OpsResponse{
    private List<OpsOrg> orgs;
}
