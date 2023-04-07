package com.sankuai.avatar.workflow.core.execute.atom.atomInput;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 跳板机账户解锁 Atom 入参
 *
 * @author zhaozhifan02
 */
@Data
public class JumperUserUnlockAtomInput {
    /**
     * 用户名
     */
    @JsonProperty("login_name")
    private String loginName;

    /**
     * 操作类型
     */
    private String type;
}
