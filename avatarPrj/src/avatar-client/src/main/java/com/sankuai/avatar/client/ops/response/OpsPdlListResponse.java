package com.sankuai.avatar.client.ops.response;

import com.sankuai.avatar.client.ops.model.OpsPdl;
import lombok.Data;

import java.util.List;

/**
 * @author zhangxiaoning07
 * @create 2022/10/31
 **/
@Data
public class OpsPdlListResponse extends OpsResponse {
    private List<OpsPdl> pdls;
}
