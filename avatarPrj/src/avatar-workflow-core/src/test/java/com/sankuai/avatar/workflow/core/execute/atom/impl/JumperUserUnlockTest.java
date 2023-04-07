package com.sankuai.avatar.workflow.core.execute.atom.impl;

import com.google.common.collect.ImmutableMap;
import com.sankuai.avatar.resource.jumper.JumperResource;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

/**
 * @author caoyang
 * @create 2023-03-16 15:33
 */
@RunWith(MockitoJUnitRunner.class)
public class JumperUserUnlockTest {

    private JumperUserUnlock atom;

    @Mock
    private JumperResource jumperResource;

    @Before
    public void setUp(){
        atom = new JumperUserUnlock(jumperResource);
    }

    @Test
    public void doProcess() {
        Map<String, String> input = ImmutableMap.of("login_name", "x", "type", "password_reset");
        ReflectionTestUtils.setField(atom, "publicData", input);
        doNothing().when(jumperResource).userUnlock(anyString());
        AtomStatus atomStatus = atom.doProcess();
        Assert.assertEquals(AtomStatus.SUCCESS, atomStatus);
        verify(jumperResource).userUnlock(anyString());
    }

    @Test
    public void doProcessEmpty() {
        ReflectionTestUtils.setField(atom,"publicData", null);
        AtomStatus atomStatus = atom.doProcess();
        Assert.assertEquals(AtomStatus.SUCCESS, atomStatus);
        verify(jumperResource, times(0)).userUnlock(anyString());
    }
}