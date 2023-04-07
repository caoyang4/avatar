package com.sankuai.avatar.client.ops.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zhangxiaoning07
 * @create 2022/10/28
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class OpsUserBgListResponse extends OpsResponse {
    private List<String> bgs;
}
