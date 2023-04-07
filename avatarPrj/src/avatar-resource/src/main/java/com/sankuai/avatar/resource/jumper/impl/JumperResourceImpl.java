package com.sankuai.avatar.resource.jumper.impl;

import com.sankuai.avatar.client.jumper.JumperHttpClient;
import com.sankuai.avatar.resource.jumper.JumperResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Service
public class JumperResourceImpl implements JumperResource {

    private final JumperHttpClient jumperHttpClient;

    public JumperResourceImpl(JumperHttpClient jumperHttpClient) {
        this.jumperHttpClient = jumperHttpClient;
    }

    @Override
    public void userUnlock(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return;
        }
        jumperHttpClient.userUnlock(userName);
    }

    @Override
    public void passwordReset(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return;
        }
        jumperHttpClient.passwordReset(userName);
    }

}
