package com.sankuai.avatar.workflow.core.execute.atom.impl;

import com.sankuai.avatar.resource.jumper.JumperResource;
import com.sankuai.avatar.workflow.core.execute.atom.AbstractAtom;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import com.sankuai.avatar.workflow.core.execute.atom.atomInput.JumperPasswordResetAtomInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 跳板机密码重置执行原子
 * @author caoyang
 * @create 2023-03-14 16:52
 */
@Slf4j
@Component("JumperPasswordReset")
@Scope("prototype")
public class JumperPasswordReset extends AbstractAtom {

    private final JumperResource jumperResource;

    @Autowired
    public JumperPasswordReset(JumperResource jumperResource) {
        this.jumperResource = jumperResource;
    }

    @Override
    protected AtomStatus doProcess() {
        JumperPasswordResetAtomInput inputData = this.getInput(JumperPasswordResetAtomInput.class);
        if (inputData == null) {
            // 入参数为空，跳过 atom 执行
            log.error("doProcess JumperPasswordReset getInput is null");
            return AtomStatus.SUCCESS;
        }
        String loginName = inputData.getLoginName();
        jumperResource.passwordReset(loginName);
        return AtomStatus.SUCCESS;
    }

}
