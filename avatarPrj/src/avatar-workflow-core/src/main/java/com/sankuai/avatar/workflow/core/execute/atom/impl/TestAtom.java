package com.sankuai.avatar.workflow.core.execute.atom.impl;

import com.sankuai.avatar.workflow.core.execute.atom.AbstractAtom;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 测试atom
 *
 * @author xk
 */
@Component
@Scope("prototype")
public class TestAtom extends AbstractAtom {

    @Override
    protected AtomStatus doProcess() {
        return AtomStatus.SUCCESS;
    }

}
