package com.sankuai.avatar.web.controller.open;

import com.meituan.servicecatalog.api.annotations.*;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.emergency.constant.OperationType;
import com.sankuai.avatar.web.dto.emergency.EmergencyResourceDTO;
import com.sankuai.avatar.web.request.EmergencyResourcePageRequest;
import com.sankuai.avatar.web.service.EmergencyService;
import com.sankuai.avatar.web.transfer.emergency.EmergencyHostVOTransfer;
import com.sankuai.avatar.web.vo.emergency.EmergencyOfflineVO;
import com.sankuai.avatar.web.vo.emergency.EmergencyOnlineVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caoyang
 * @create 2023-03-02 10:09
 */
@InterfaceDoc(
        displayName = "紧急资源信息",
        type = "restful",
        description = "紧急资源新增及下线信息查看",
        scenarios = "接口用于查询紧急资源的上下线信息"
)
@RestController
@RequestMapping("/open/api/v2/avatar/emergency_resource")
public class EmergencyResourceOpenController {

    private final EmergencyService emergencyService;

    @Autowired
    public EmergencyResourceOpenController(EmergencyService emergencyService) {
        this.emergencyService = emergencyService;
    }

    @MethodDoc(displayName = "紧急资源上线信息分页查询接口",
            description = "接口用于分页查询紧急资源的上线信息",
            parameters = {
                    @ParamDoc(
                            name = "pageRequest",
                            description = "紧急资源分页查询支持的查询参数",
                            paramType = ParamType.REQUEST_PARAM,
                            requiredness = Requiredness.OPTIONAL
                    )
            },
            requestMethods = {HttpMethod.GET},
            restExampleUrl="http://avatar.vip.sankuai.com/open/api/v2/avatar/emergency_resource/online?appkey=xxx",
            responseParams = {
                    @ParamDoc(name = "code", description = "返回码"),
                    @ParamDoc(name = "message", description = "返回描述"),
                    @ParamDoc(name = "data", description = "紧急资源上线信息分页查询数据", type = PageResponse.class),
            },
            restExampleResponseData = "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"message\": \"操作成功\",\n" +
                    "  \"data\": {\n" +
                    "    \"totalCount\": 40,\n" +
                    "    \"page\": 1,\n" +
                    "    \"totalPage\": 4,\n" +
                    "    \"pageSize\": 10,\n" +
                    "    \"items\": [\n" +
                    "      {\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"com.sankuai.mtcv.serving.imagedrawassis\",\n" +
                    "        \"template\": \"service_expand\",\n" +
                    "        \"set\": \"\",\n" +
                    "        \"channel\": \"\",\n" +
                    "        \"channelCn\": \"\",\n" +
                    "        \"cluster\": \"emergency\",\n" +
                    "        \"clusterCn\": \"\",\n" +
                    "        \"city\": \"\",\n" +
                    "        \"region\": \"beijing\",\n" +
                    "        \"count\": 20,\n" +
                    "        \"cpu\": 8,\n" +
                    "        \"memory\": 8,\n" +
                    "        \"disk\": 150,\n" +
                    "        \"diskType\": \"\",\n" +
                    "        \"diskTypeCn\": \"\",\n" +
                    "        \"os\": \"\",\n" +
                    "        \"configExtraInfo\": \"\",\n" +
                    "        \"flowUuid\": \"84930bff-e203-4ce9-84f0-f05ac031c65e\",\n" +
                    "        \"flowId\": 2544246,\n" +
                    "        \"idcs\": [],\n" +
                    "        \"onlineDate\": \"2023-03-02 21:43:23\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"shop-server\",\n" +
                    "        \"template\": \"service_expand\",\n" +
                    "        \"set\": \"\",\n" +
                    "        \"channel\": \"\",\n" +
                    "        \"channelCn\": \"\",\n" +
                    "        \"cluster\": \"emergency\",\n" +
                    "        \"clusterCn\": \"\",\n" +
                    "        \"city\": \"\",\n" +
                    "        \"region\": \"shanghai\",\n" +
                    "        \"count\": 20,\n" +
                    "        \"cpu\": 8,\n" +
                    "        \"memory\": 8,\n" +
                    "        \"disk\": 100,\n" +
                    "        \"diskType\": \"\",\n" +
                    "        \"diskTypeCn\": \"\",\n" +
                    "        \"os\": \"\",\n" +
                    "        \"configExtraInfo\": \"\",\n" +
                    "        \"flowUuid\": \"755e1488-1547-4b13-b8b0-d72ff3ca0315\",\n" +
                    "        \"flowId\": 2538321,\n" +
                    "        \"idcs\": [\n" +
                    "          {\n" +
                    "            \"count\": 7,\n" +
                    "            \"idcName\": \"嘉定\",\n" +
                    "            \"idc\": \"jd\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"count\": 7,\n" +
                    "            \"idcName\": \"月浦\",\n" +
                    "            \"idc\": \"yp\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"count\": 6,\n" +
                    "            \"idcName\": \"浦江\",\n" +
                    "            \"idc\": \"pj\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"onlineDate\": \"2023-03-01 14:14:36\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"com.sankuai.qcs.service.algodrivermarket\",\n" +
                    "        \"template\": \"service_expand\",\n" +
                    "        \"set\": \"\",\n" +
                    "        \"channel\": \"\",\n" +
                    "        \"channelCn\": \"\",\n" +
                    "        \"cluster\": \"emergency\",\n" +
                    "        \"clusterCn\": \"\",\n" +
                    "        \"city\": \"\",\n" +
                    "        \"region\": \"beijing\",\n" +
                    "        \"count\": 1,\n" +
                    "        \"cpu\": 8,\n" +
                    "        \"memory\": 16,\n" +
                    "        \"disk\": 150,\n" +
                    "        \"diskType\": \"\",\n" +
                    "        \"diskTypeCn\": \"\",\n" +
                    "        \"os\": \"\",\n" +
                    "        \"configExtraInfo\": \"\",\n" +
                    "        \"flowUuid\": \"82dac655-b621-466a-84c4-18fe797e5d31\",\n" +
                    "        \"flowId\": 2523203,\n" +
                    "        \"idcs\": [\n" +
                    "          {\n" +
                    "            \"count\": 1,\n" +
                    "            \"idcName\": \"铭泰\",\n" +
                    "            \"idc\": \"mt\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"onlineDate\": \"2023-02-23 11:51:56\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"com.sankuai.hotel.biz.operation\",\n" +
                    "        \"template\": \"service_expand\",\n" +
                    "        \"set\": \"\",\n" +
                    "        \"channel\": \"\",\n" +
                    "        \"channelCn\": \"\",\n" +
                    "        \"cluster\": \"emergency\",\n" +
                    "        \"clusterCn\": \"\",\n" +
                    "        \"city\": \"\",\n" +
                    "        \"region\": \"beijing\",\n" +
                    "        \"count\": 42,\n" +
                    "        \"cpu\": 4,\n" +
                    "        \"memory\": 8,\n" +
                    "        \"disk\": 220,\n" +
                    "        \"diskType\": \"\",\n" +
                    "        \"diskTypeCn\": \"\",\n" +
                    "        \"os\": \"\",\n" +
                    "        \"configExtraInfo\": \"\",\n" +
                    "        \"flowUuid\": \"f5001ae1-a902-4dda-b962-83d8a26d866c\",\n" +
                    "        \"flowId\": 2516447,\n" +
                    "        \"idcs\": [\n" +
                    "          {\n" +
                    "            \"count\": 10,\n" +
                    "            \"idcName\": \"兆丰\",\n" +
                    "            \"idc\": \"zf\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"count\": 10,\n" +
                    "            \"idcName\": \"铭泰\",\n" +
                    "            \"idc\": \"mt\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"count\": 10,\n" +
                    "            \"idcName\": \"汇海\",\n" +
                    "            \"idc\": \"hh\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"count\": 12,\n" +
                    "            \"idcName\": \"贤人\",\n" +
                    "            \"idc\": \"xr\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"onlineDate\": \"2023-02-21 17:20:27\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"com.sankuai.rms.data\",\n" +
                    "        \"template\": \"service_expand\",\n" +
                    "        \"set\": \"\",\n" +
                    "        \"channel\": \"\",\n" +
                    "        \"channelCn\": \"\",\n" +
                    "        \"cluster\": \"emergency\",\n" +
                    "        \"clusterCn\": \"\",\n" +
                    "        \"city\": \"\",\n" +
                    "        \"region\": \"beijing\",\n" +
                    "        \"count\": 20,\n" +
                    "        \"cpu\": 4,\n" +
                    "        \"memory\": 8,\n" +
                    "        \"disk\": 150,\n" +
                    "        \"diskType\": \"\",\n" +
                    "        \"diskTypeCn\": \"\",\n" +
                    "        \"os\": \"\",\n" +
                    "        \"configExtraInfo\": \"\",\n" +
                    "        \"flowUuid\": \"2696f960-f73f-4652-ab97-cbffc3a1fe9a\",\n" +
                    "        \"flowId\": 2512703,\n" +
                    "        \"idcs\": [],\n" +
                    "        \"onlineDate\": \"2023-02-20 16:40:37\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"env\": \"staging\",\n" +
                    "        \"appkey\": \"com.sankuai.sas.risk\",\n" +
                    "        \"template\": \"service_expand\",\n" +
                    "        \"set\": \"\",\n" +
                    "        \"channel\": \"hulk\",\n" +
                    "        \"channelCn\": \"\",\n" +
                    "        \"cluster\": \"emergency\",\n" +
                    "        \"clusterCn\": \"\",\n" +
                    "        \"city\": \"北京\",\n" +
                    "        \"region\": \"beijing\",\n" +
                    "        \"count\": 1,\n" +
                    "        \"cpu\": 16,\n" +
                    "        \"memory\": 32,\n" +
                    "        \"disk\": 800,\n" +
                    "        \"diskType\": \"system\",\n" +
                    "        \"diskTypeCn\": \"\",\n" +
                    "        \"os\": \"CentOS 6\",\n" +
                    "        \"configExtraInfo\": \"\",\n" +
                    "        \"flowUuid\": \"eeaa6de2-92a1-45b6-a912-36c5a609e454\",\n" +
                    "        \"flowId\": 2503410,\n" +
                    "        \"idcs\": [\n" +
                    "          {\n" +
                    "            \"count\": 1,\n" +
                    "            \"idcName\": \"云谷\",\n" +
                    "            \"idc\": \"yg\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"onlineDate\": \"2023-02-16 13:19:56\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"env\": \"staging\",\n" +
                    "        \"appkey\": \"com.sankuai.sas.risk\",\n" +
                    "        \"template\": \"service_expand\",\n" +
                    "        \"set\": \"\",\n" +
                    "        \"channel\": \"hulk\",\n" +
                    "        \"channelCn\": \"\",\n" +
                    "        \"cluster\": \"emergency\",\n" +
                    "        \"clusterCn\": \"\",\n" +
                    "        \"city\": \"北京\",\n" +
                    "        \"region\": \"beijing\",\n" +
                    "        \"count\": 6,\n" +
                    "        \"cpu\": 16,\n" +
                    "        \"memory\": 32,\n" +
                    "        \"disk\": 800,\n" +
                    "        \"diskType\": \"system\",\n" +
                    "        \"diskTypeCn\": \"\",\n" +
                    "        \"os\": \"CentOS 6\",\n" +
                    "        \"configExtraInfo\": \"\",\n" +
                    "        \"flowUuid\": \"c7543a24-7b2d-4012-9cab-476e9b2d8551\",\n" +
                    "        \"flowId\": 2503409,\n" +
                    "        \"idcs\": [\n" +
                    "          {\n" +
                    "            \"count\": 6,\n" +
                    "            \"idcName\": \"云谷\",\n" +
                    "            \"idc\": \"yg\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"onlineDate\": \"2023-02-16 13:06:44\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"com.sankuai.inf.image.info\",\n" +
                    "        \"template\": \"service_expand\",\n" +
                    "        \"set\": \"\",\n" +
                    "        \"channel\": \"\",\n" +
                    "        \"channelCn\": \"\",\n" +
                    "        \"cluster\": \"emergency\",\n" +
                    "        \"clusterCn\": \"\",\n" +
                    "        \"city\": \"\",\n" +
                    "        \"region\": \"beijing\",\n" +
                    "        \"count\": 30,\n" +
                    "        \"cpu\": 8,\n" +
                    "        \"memory\": 16,\n" +
                    "        \"disk\": 150,\n" +
                    "        \"diskType\": \"\",\n" +
                    "        \"diskTypeCn\": \"\",\n" +
                    "        \"os\": \"\",\n" +
                    "        \"configExtraInfo\": \"\",\n" +
                    "        \"flowUuid\": \"e8810307-39b1-4dc0-851b-3f25274c4604\",\n" +
                    "        \"flowId\": 2502320,\n" +
                    "        \"idcs\": [\n" +
                    "          {\n" +
                    "            \"count\": 7,\n" +
                    "            \"idcName\": \"铭泰\",\n" +
                    "            \"idc\": \"mt\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"count\": 6,\n" +
                    "            \"idcName\": \"光环\",\n" +
                    "            \"idc\": \"gh\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"count\": 4,\n" +
                    "            \"idcName\": \"汇海\",\n" +
                    "            \"idc\": \"hh\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"count\": 13,\n" +
                    "            \"idcName\": \"腾讯云\",\n" +
                    "            \"idc\": \"tx_bj3\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"onlineDate\": \"2023-02-15 20:59:32\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"com.sankuai.guess.idx.proxy\",\n" +
                    "        \"template\": \"service_expand\",\n" +
                    "        \"set\": \"\",\n" +
                    "        \"channel\": \"hulk\",\n" +
                    "        \"channelCn\": \"\",\n" +
                    "        \"cluster\": \"emergency\",\n" +
                    "        \"clusterCn\": \"\",\n" +
                    "        \"city\": \"北京\",\n" +
                    "        \"region\": \"beijing\",\n" +
                    "        \"count\": 2,\n" +
                    "        \"cpu\": 8,\n" +
                    "        \"memory\": 16,\n" +
                    "        \"disk\": 300,\n" +
                    "        \"diskType\": \"system\",\n" +
                    "        \"diskTypeCn\": \"\",\n" +
                    "        \"os\": \"CentOS 7\",\n" +
                    "        \"configExtraInfo\": \"\",\n" +
                    "        \"flowUuid\": \"da868027-4dce-48a4-96c0-98d2e5d6f736\",\n" +
                    "        \"flowId\": 2497956,\n" +
                    "        \"idcs\": [\n" +
                    "          {\n" +
                    "            \"count\": 2,\n" +
                    "            \"idcName\": \"铭泰\",\n" +
                    "            \"idc\": \"mt\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"onlineDate\": \"2023-02-14 18:51:47\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"com.sankuai.sinai.data.query\",\n" +
                    "        \"template\": \"service_expand\",\n" +
                    "        \"set\": \"\",\n" +
                    "        \"channel\": \"\",\n" +
                    "        \"channelCn\": \"\",\n" +
                    "        \"cluster\": \"emergency\",\n" +
                    "        \"clusterCn\": \"\",\n" +
                    "        \"city\": \"\",\n" +
                    "        \"region\": \"beijing\",\n" +
                    "        \"count\": 40,\n" +
                    "        \"cpu\": 8,\n" +
                    "        \"memory\": 8,\n" +
                    "        \"disk\": 100,\n" +
                    "        \"diskType\": \"\",\n" +
                    "        \"diskTypeCn\": \"\",\n" +
                    "        \"os\": \"\",\n" +
                    "        \"configExtraInfo\": \"\",\n" +
                    "        \"flowUuid\": \"56ce6f4f-1b7c-499e-b0ce-186dc6c8b050\",\n" +
                    "        \"flowId\": 2497736,\n" +
                    "        \"idcs\": [\n" +
                    "          {\n" +
                    "            \"count\": 10,\n" +
                    "            \"idcName\": \"兆丰\",\n" +
                    "            \"idc\": \"zf\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"count\": 10,\n" +
                    "            \"idcName\": \"铭泰\",\n" +
                    "            \"idc\": \"mt\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"count\": 10,\n" +
                    "            \"idcName\": \"贤人\",\n" +
                    "            \"idc\": \"xr\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"count\": 10,\n" +
                    "            \"idcName\": \"光环\",\n" +
                    "            \"idc\": \"gh\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"onlineDate\": \"2023-02-14 17:51:36\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}"
    )
    @GetMapping("/online")
    PageResponse<EmergencyOnlineVO> getPageEmergencyOnline(EmergencyResourcePageRequest pageRequest){
        PageResponse<EmergencyOnlineVO> pageResponse = new PageResponse<>();
        pageRequest.setOperationType(OperationType.ECS_ONLINE);
        PageResponse<EmergencyResourceDTO> dtoPageResponse = emergencyService.queryPage(pageRequest);
        pageResponse.setPage(dtoPageResponse.getPage());
        pageResponse.setPageSize(dtoPageResponse.getPageSize());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setItems(EmergencyHostVOTransfer.INSTANCE.toEmergencyOnlineVOList(dtoPageResponse.getItems()));
        return pageResponse;
    }

    @MethodDoc(displayName = "紧急资源下线信息分页查询接口",
            description = "接口用于分页查询紧急资源的下线信息",
            parameters = {
                    @ParamDoc(
                            name = "pageRequest",
                            description = "紧急资源分页查询支持的查询参数",
                            paramType = ParamType.REQUEST_PARAM,
                            requiredness = Requiredness.OPTIONAL
                    )
            },
            requestMethods = {HttpMethod.GET},
            restExampleUrl="http://avatar.vip.sankuai.com/open/api/v2/avatar/emergency_resource/offline?appkey=xxx",
            responseParams = {
                    @ParamDoc(name = "code", description = "返回码"),
                    @ParamDoc(name = "message", description = "返回描述"),
                    @ParamDoc(name = "data", description = "紧急资源下线信息分页查询数据", type = PageResponse.class),
            },
            restExampleResponseData = "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"message\": \"操作成功\",\n" +
                    "  \"data\": {\n" +
                    "    \"totalCount\": 15520,\n" +
                    "    \"page\": 1,\n" +
                    "    \"totalPage\": 1552,\n" +
                    "    \"pageSize\": 10,\n" +
                    "    \"items\": [\n" +
                    "      {\n" +
                    "        \"cell\": \"-\",\n" +
                    "        \"ip\": \"10.7.172.164\",\n" +
                    "        \"name\": \"set-yf-wmdtraffic-recommend-rank-staging02\",\n" +
                    "        \"env\": \"staging\",\n" +
                    "        \"appkey\": \"com.sankuai.shangou.platform.rank\",\n" +
                    "        \"template\": \"reduced_service\",\n" +
                    "        \"displaySrv\": \"\",\n" +
                    "        \"flowUuid\": \"03f941c2-fe72-4621-8379-64badba4d217\",\n" +
                    "        \"flowId\": 2564512,\n" +
                    "        \"cpu\": 8,\n" +
                    "        \"memory\": 8,\n" +
                    "        \"disk\": 250,\n" +
                    "        \"kind\": \"hulk\",\n" +
                    "        \"os\": \"CentOS 7.6.1810\",\n" +
                    "        \"idc\": \"yf\",\n" +
                    "        \"offlineDate\": \"2023-03-10 10:48:24\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"cell\": \"\",\n" +
                    "        \"ip\": \"10.87.201.58\",\n" +
                    "        \"name\": \"set-yp-wmdrecsys-recommendtopic32\",\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"com.sankuai.wmdrecsys.recommendtopic\",\n" +
                    "        \"template\": \"reduced_service\",\n" +
                    "        \"displaySrv\": \"\",\n" +
                    "        \"flowUuid\": \"b3991b53-0205-464a-bf2b-27befeb20891\",\n" +
                    "        \"flowId\": 2564507,\n" +
                    "        \"cpu\": 16,\n" +
                    "        \"memory\": 16,\n" +
                    "        \"disk\": 300,\n" +
                    "        \"kind\": \"hulk\",\n" +
                    "        \"os\": \"CentOS 7.6.1810\",\n" +
                    "        \"idc\": \"yp\",\n" +
                    "        \"offlineDate\": \"2023-03-10 10:45:23\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"cell\": \"banma-east\",\n" +
                    "        \"ip\": \"10.79.34.98\",\n" +
                    "        \"name\": \"set-jd-deliveryturing-feature-dlbox-staging01\",\n" +
                    "        \"env\": \"staging\",\n" +
                    "        \"appkey\": \"com.sankuai.deliveryturing.feature.dlbox\",\n" +
                    "        \"template\": \"reduced_service\",\n" +
                    "        \"displaySrv\": \"\",\n" +
                    "        \"flowUuid\": \"9201ee81-f6a7-4585-9b83-63ed38196a01\",\n" +
                    "        \"flowId\": 2564489,\n" +
                    "        \"cpu\": 16,\n" +
                    "        \"memory\": 32,\n" +
                    "        \"disk\": 300,\n" +
                    "        \"kind\": \"hulk\",\n" +
                    "        \"os\": \"CentOS 7.6.1810\",\n" +
                    "        \"idc\": \"jd\",\n" +
                    "        \"offlineDate\": \"2023-03-10 10:40:57\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"cell\": \"\",\n" +
                    "        \"ip\": \"10.208.32.92\",\n" +
                    "        \"name\": \"set-hh-rerank-search-multimerchants69\",\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"com.sankuai.rerank.search.multimerchants\",\n" +
                    "        \"template\": \"reduced_service\",\n" +
                    "        \"displaySrv\": \"\",\n" +
                    "        \"flowUuid\": \"a04a79a3-9738-4eeb-aadb-8fb6e9552761\",\n" +
                    "        \"flowId\": 2564473,\n" +
                    "        \"cpu\": 8,\n" +
                    "        \"memory\": 16,\n" +
                    "        \"disk\": 100,\n" +
                    "        \"kind\": \"hulk\",\n" +
                    "        \"os\": \"CentOS 7.6.1810\",\n" +
                    "        \"idc\": \"hh\",\n" +
                    "        \"offlineDate\": \"2023-03-10 10:38:38\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"cell\": \"\",\n" +
                    "        \"ip\": \"10.208.44.94\",\n" +
                    "        \"name\": \"set-hh-rerank-search-multimerchants05\",\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"com.sankuai.rerank.search.multimerchants\",\n" +
                    "        \"template\": \"reduced_service\",\n" +
                    "        \"displaySrv\": \"\",\n" +
                    "        \"flowUuid\": \"e5b6c995-1883-4849-82f3-895de4d8b00e\",\n" +
                    "        \"flowId\": 2564438,\n" +
                    "        \"cpu\": 32,\n" +
                    "        \"memory\": 64,\n" +
                    "        \"disk\": 500,\n" +
                    "        \"kind\": \"hulk\",\n" +
                    "        \"os\": \"CentOS 7.6.1810\",\n" +
                    "        \"idc\": \"hh\",\n" +
                    "        \"offlineDate\": \"2023-03-10 10:37:35\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"cell\": \"\",\n" +
                    "        \"ip\": \"10.79.79.198\",\n" +
                    "        \"name\": \"set-jd-dprec-ug-rec-push-service11\",\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"rec-push-service\",\n" +
                    "        \"template\": \"reduced_service\",\n" +
                    "        \"displaySrv\": \"\",\n" +
                    "        \"flowUuid\": \"4f4259be-c1c3-4667-8e33-c5754de6ddb8\",\n" +
                    "        \"flowId\": 2564448,\n" +
                    "        \"cpu\": 32,\n" +
                    "        \"memory\": 16,\n" +
                    "        \"disk\": 150,\n" +
                    "        \"kind\": \"hulk\",\n" +
                    "        \"os\": \"CentOS 6.8\",\n" +
                    "        \"idc\": \"jd\",\n" +
                    "        \"offlineDate\": \"2023-03-10 10:35:03\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"cell\": \"\",\n" +
                    "        \"ip\": \"10.87.198.42\",\n" +
                    "        \"name\": \"set-yp-dprec-ug-cip-home-popups-service10\",\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"cip-home-popups-service\",\n" +
                    "        \"template\": \"reduced_service\",\n" +
                    "        \"displaySrv\": \"\",\n" +
                    "        \"flowUuid\": \"86f680f9-b81f-4c60-846b-2a572104c45b\",\n" +
                    "        \"flowId\": 2564422,\n" +
                    "        \"cpu\": 8,\n" +
                    "        \"memory\": 16,\n" +
                    "        \"disk\": 150,\n" +
                    "        \"kind\": \"hulk\",\n" +
                    "        \"os\": \"CentOS 6.8\",\n" +
                    "        \"idc\": \"yp\",\n" +
                    "        \"offlineDate\": \"2023-03-10 10:30:21\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"cell\": \"\",\n" +
                    "        \"ip\": \"10.7.234.149\",\n" +
                    "        \"name\": \"set-yf-hulk-banner-clustera03\",\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"com.sankuai.hulk.banner.clustera\",\n" +
                    "        \"template\": \"reduced_service\",\n" +
                    "        \"displaySrv\": \"\",\n" +
                    "        \"flowUuid\": \"2537b363-5f52-42eb-901b-de672f7c9f1d\",\n" +
                    "        \"flowId\": 2564415,\n" +
                    "        \"cpu\": 4,\n" +
                    "        \"memory\": 8,\n" +
                    "        \"disk\": 150,\n" +
                    "        \"kind\": \"hulk\",\n" +
                    "        \"os\": \"CentOS 6.8\",\n" +
                    "        \"idc\": \"yf\",\n" +
                    "        \"offlineDate\": \"2023-03-10 10:29:23\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"cell\": \"\",\n" +
                    "        \"ip\": \"10.34.102.152\",\n" +
                    "        \"name\": \"set-hh-mtsi-sec-maxflow08\",\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"com.sankuai.mtsi.sec.maxflow\",\n" +
                    "        \"template\": \"reduced_service\",\n" +
                    "        \"displaySrv\": \"\",\n" +
                    "        \"flowUuid\": \"62450523-bf7a-48da-9d90-1c487b25834a\",\n" +
                    "        \"flowId\": 2564413,\n" +
                    "        \"cpu\": 8,\n" +
                    "        \"memory\": 16,\n" +
                    "        \"disk\": 120,\n" +
                    "        \"kind\": \"hulk\",\n" +
                    "        \"os\": \"CentOS 7.6.1810\",\n" +
                    "        \"idc\": \"hh\",\n" +
                    "        \"offlineDate\": \"2023-03-10 10:29:13\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"cell\": \"\",\n" +
                    "        \"ip\": \"10.87.198.219\",\n" +
                    "        \"name\": \"set-yp-rc-zeus-latency26\",\n" +
                    "        \"env\": \"prod\",\n" +
                    "        \"appkey\": \"com.sankuai.rc.zeus.latency\",\n" +
                    "        \"template\": \"reduced_service\",\n" +
                    "        \"displaySrv\": \"\",\n" +
                    "        \"flowUuid\": \"6c4f0b9e-83d6-4676-8b38-59d144df124c\",\n" +
                    "        \"flowId\": 2564404,\n" +
                    "        \"cpu\": 8,\n" +
                    "        \"memory\": 16,\n" +
                    "        \"disk\": 300,\n" +
                    "        \"kind\": \"hulk\",\n" +
                    "        \"os\": \"CentOS 6.8\",\n" +
                    "        \"idc\": \"yp\",\n" +
                    "        \"offlineDate\": \"2023-03-10 10:27:21\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}"
    )
    @GetMapping("/offline")
    PageResponse<EmergencyOfflineVO> getPageEmergencyOffline(EmergencyResourcePageRequest pageRequest){
        PageResponse<EmergencyOfflineVO> pageResponse = new PageResponse<>();
        pageRequest.setOperationType(OperationType.ECS_OFFLINE);
        PageResponse<EmergencyResourceDTO> dtoPageResponse = emergencyService.queryPage(pageRequest);
        pageResponse.setPage(dtoPageResponse.getPage());
        pageResponse.setPageSize(dtoPageResponse.getPageSize());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setItems(EmergencyHostVOTransfer.INSTANCE.toEmergencyOfflineVOList(dtoPageResponse.getItems()));
        return pageResponse;
    }
}
