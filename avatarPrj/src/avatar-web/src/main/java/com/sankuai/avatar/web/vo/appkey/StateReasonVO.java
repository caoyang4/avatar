package com.sankuai.avatar.web.vo.appkey;

import lombok.Data;

import java.util.List;

/**
 * 服务状态原因
 * @author qinwei05
 * @date 2023/1/17 16:39
 */
@Data
public class StateReasonVO {

    /**
     * 有状态原因
     */
    private List<String> stateful;

    /**
     * 无状态原因
     */
    private List<String> noStateful;
}
