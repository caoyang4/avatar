package com.sankuai.avatar.workflow.core.execute.atom.atomInput;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 跳板机密码重置 atom 入参
 * @author caoyang
 * @create 2023-03-14 16:50
 */
@Data
public class JumperPasswordResetAtomInput {
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
