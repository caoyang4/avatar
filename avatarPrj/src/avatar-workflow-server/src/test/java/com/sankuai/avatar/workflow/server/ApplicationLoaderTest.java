package com.sankuai.avatar.workflow.server;

import com.sankuai.avatar.workflow.server.dal.entity.CasUser;
import com.sankuai.avatar.workflow.server.util.MockMvcTest;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhaozhifan02
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class ApplicationLoaderTest {

    @Autowired
    protected MockMvcTest mockMvcTest;

    @Before
    public void setUp() {
        CasUser casUser = new CasUser();
        casUser.setLoginName("zhaozhifan02");
    }
}
