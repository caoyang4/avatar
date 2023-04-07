package com.sankuai.avatar.workflow.core.execute.atom.impl;

import com.sankuai.avatar.resource.jumper.JumperResource;
import com.sankuai.avatar.workflow.core.execute.atom.AbstractAtom;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import com.sankuai.avatar.workflow.core.execute.atom.atomInput.JumperUserUnlockAtomInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 跳板机账号解锁
 *
 * @author zhaozhifan02
 */
@Slf4j
@Component("JumperUserUnlock")
@Scope("prototype")
public class JumperUserUnlock extends AbstractAtom {

    private final JumperResource jumperResource;

    @Autowired
    public JumperUserUnlock(JumperResource jumperResource) {
        this.jumperResource = jumperResource;
    }

    @Override
    protected AtomStatus doProcess() {
        JumperUserUnlockAtomInput inputData = this.getInput(JumperUserUnlockAtomInput.class);
        if (inputData == null) {
            // 入参数为空，跳过 atom 执行
            log.error("doProcess JumperUserUnlock getInput is null");
            return AtomStatus.SUCCESS;
        }
        String loginName = inputData.getLoginName();
        jumperResource.userUnlock(loginName);
        return AtomStatus.SUCCESS;
    }
}
