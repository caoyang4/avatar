package com.sankuai.avatar.client;

import com.sankuai.avatar.client.http.core.HttpClient;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 测试基类
 *
 * @author qinwei05
 * @date 2022/10/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public abstract class TestBase {

    protected MockWebServer server = new MockWebServer();

    protected String mockUrl = "http://" + server.getHostName() + ":" + server.getPort();

    protected HttpClient baseHttpClient;

    public final String testAppkey = "com.sankuai.avatar.cscscscs";

    public final String testSrvKey = "meituan.avatar.ceshi.avatar-cscscscs";

    public final String hostName = "set-yp-avatar-cscscscs-test02";

    protected final Logger logger = Logger.getLogger("TEST");

    protected void logger(String message) {
        logger.log(Level.INFO, message);
    }
}
