package com.sankuai.avatar.workflow.server.vo.flow.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sankuai.avatar.common.utils.KeepAsJsonDeserializer;
import lombok.Data;

/**
 * @author Jie.li.sh
 * @create 2023-02-28
 **/
@Data
public class FlowCreateRequestVO {
    /**
     * 输入
     */
    @JsonDeserialize(using = KeepAsJsonDeserializer.class)
    String input;
}
