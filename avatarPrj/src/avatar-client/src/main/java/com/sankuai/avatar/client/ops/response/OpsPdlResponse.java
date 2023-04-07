package com.sankuai.avatar.client.ops.response;

import com.sankuai.avatar.client.ops.model.OpsOwt;
import com.sankuai.avatar.client.ops.model.OpsPdl;
import lombok.Data;

/**
 * @author zhangxiaoning07
 * @create 2022/10/31
 **/
@Data
public class OpsPdlResponse extends OpsResponse{
    private OpsPdl pdl;
    private OpsOwt owt;
}
