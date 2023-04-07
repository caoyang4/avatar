package com.sankuai.avatar.client.ops.response;

import com.sankuai.avatar.client.ops.model.OpsSrv;
import lombok.Data;

import java.util.List;

/**
 * @author zhangxiaoning07
 * @create 2022/11/1
 **/
@Data
public class OpsSrvListResponse extends OpsResponse{
    private List<OpsSrv> srvs;
}
