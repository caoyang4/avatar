package com.sankuai.avatar.workflow.server.vo.flow;

import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Data
public class PreCheckItemVO {
    /**
     * 预检信息
     */
    private String msg;

    /**
     * 类型 error、warning
     */
    private String type;

    public static PreCheckItemVO of(String msg, String type) {
        PreCheckItemVO vo = new PreCheckItemVO();
        vo.setMsg(msg);
        vo.setType(type);
        return vo;
    }
}
