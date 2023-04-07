package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.emergency.constant.OperationType;
import com.sankuai.avatar.web.dto.emergency.EmergencyHostDTO;
import com.sankuai.avatar.web.dto.emergency.HostIdcDTO;
import com.sankuai.avatar.web.dto.emergency.OfflineHostDTO;
import com.sankuai.avatar.web.dto.emergency.OnlineHostDTO;
import com.sankuai.avatar.web.vo.emergency.EmergencyOfflineVO;
import com.sankuai.avatar.web.vo.emergency.EmergencyOnlineVO;
import com.sankuai.avatar.web.vo.emergency.EmergencyResourceVO;
import com.sankuai.avatar.web.vo.user.UserPermissionVO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

/**
 * @author caoyang
 * @create 2023-01-04 16:00
 */
public class EmergencyResourceControllerTest extends TestBase {

    private final String baseUrl = "/api/v2/avatar/emergency_resource";

    @Test
    public void getPageEmergencyOnline() throws Exception{
        String url = baseUrl + "/online?startTime=2022-01-19 12:00:00&endTime=2022-08-16 23:30:17";
        PageResponse<EmergencyOnlineVO> pageResponse = getMock(url, PageResponse.class);
        assert pageResponse != null;
        Assert.assertEquals(10, pageResponse.getPageSize());
        Assert.assertTrue(pageResponse.getItems().size() > 0);
    }

    @Test
    public void getPageEmergencyOffline() throws Exception{
        String url = baseUrl + "/offline?startTime=2022-08-17 10:00:00&endTime=2022-08-26 18:30:17";
        PageResponse<EmergencyOfflineVO> pageResponse = getMock(url, PageResponse.class);
        assert pageResponse != null;
        Assert.assertEquals(10, pageResponse.getPageSize());
        Assert.assertTrue(pageResponse.getItems().size() > 0);
    }

    @Test
    public void deleteEmergencyResource() throws Exception{
        String url = baseUrl + "/1";
        boolean delete = deleteMock(url, new Object(), Boolean.class);
        Assert.assertTrue(delete);
    }

    @Test
    public void saveOnlineEmergencyResource() throws Exception{
        EmergencyResourceVO resourceVO = new EmergencyResourceVO();
        resourceVO.setCount(1);
        resourceVO.setAppkey("com.sankuai.avatar.develop");
        resourceVO.setEnv("prod");
        resourceVO.setFlowId(15504);
        resourceVO.setFlowUuid("a3f5f3c1-8852-442a-909c-8a3ec69fb7af");
        resourceVO.setTemplate("service_expand");
        resourceVO.setCreateUser("zhangsan");
        resourceVO.setOperationType(OperationType.ECS_ONLINE);
        OnlineHostDTO hostDTO = new OnlineHostDTO();
        hostDTO.setCluster("emergency");
        hostDTO.setChannelCn("");
        hostDTO.setDisk(150);
        hostDTO.setCity("beijing");
        hostDTO.setNicType("common_nic");
        hostDTO.setSet("");
        hostDTO.setEnv("prod");
        hostDTO.setChannel("hulk");
        hostDTO.setChannelCn("");
        hostDTO.setDeploy(false);
        hostDTO.setMemory(4);
        hostDTO.setNic("nic_10g");
        hostDTO.setNicType("");
        hostDTO.setParallel(1);
        hostDTO.setRegion("beijing");
        hostDTO.setCpu(4);
        hostDTO.setDisk(4);
        hostDTO.setDiskType("system");
        hostDTO.setDiskTypeCn("");
        HostIdcDTO hostIdcDTO = new HostIdcDTO();
        hostIdcDTO.setCount(1);
        hostIdcDTO.setIdcName("兆丰");
        hostIdcDTO.setIdc("zf");
        hostDTO.setIdcs(Collections.singletonList(hostIdcDTO));
        hostDTO.setOs("CentOS 6");
        resourceVO.setHostConfig(hostDTO);

        Boolean save = postMock(baseUrl, resourceVO, Boolean.class);
        Assert.assertTrue(save);
    }

    @Test
    public void saveOfflineEmergencyResource() throws Exception{
        EmergencyResourceVO resourceVO = new EmergencyResourceVO();
        resourceVO.setCount(1);
        resourceVO.setAppkey("com.sankuai.avatar.develop");
        resourceVO.setEnv("prod");
        resourceVO.setFlowId(15753);
        resourceVO.setFlowUuid("3954c9ea-16b8-4bd5-8e7f-954a9aaf0fee");
        resourceVO.setTemplate("reduced_service");
        resourceVO.setCreateUser("lisi");
        resourceVO.setOperationType(OperationType.ECS_ONLINE);
        OfflineHostDTO hostDTO = new OfflineHostDTO();
        hostDTO.setEnv("prod");
        hostDTO.setAppkey("com.sankuai.avatar.develop");
        hostDTO.setDisplaySrv("meituan.avatar.ceshi.avatar-develop");
        hostDTO.setReason("紧急资源归还");
        EmergencyHostDTO emergencyHostDTO = new EmergencyHostDTO();
        emergencyHostDTO.setCell("-");
        emergencyHostDTO.setIpLan("10.45.120.44");
        emergencyHostDTO.setName("set-xr-avatar-develop01");
        hostDTO.setHosts(Collections.singletonList(emergencyHostDTO));
        resourceVO.setOfflineHost(hostDTO);

        Boolean save = postMock(baseUrl, resourceVO, Boolean.class);
        Assert.assertTrue(save);
    }

    @Test
    public void isUnitSre() throws Exception {
        String url = baseUrl + "/permission?mis=songchenze";
        UserPermissionVO map = getMock(url, UserPermissionVO.class);
        Assert.assertNotNull(map);
    }

}