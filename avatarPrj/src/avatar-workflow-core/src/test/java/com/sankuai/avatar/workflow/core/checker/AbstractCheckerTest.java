package com.sankuai.avatar.workflow.core.checker;

import com.sankuai.avatar.workflow.core.annotation.CheckerMeta;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class AbstractCheckerTest {

    @Mock
    CheckerMeta checkerMeta;

    private final AbstractChecker abstractChecker = new AbstractChecker() {
        @Override
        public CheckResult check(FlowContext flowContext) {
            return null;
        }
    };

    @Test
    public void getTimeout() {
        Integer timeout = abstractChecker.getTimeout();
        Assert.assertNull(timeout);
    }

    @Test
    public void getOrder() {
        Integer order = abstractChecker.getOrder();
        Assert.assertNull(order);
    }
}