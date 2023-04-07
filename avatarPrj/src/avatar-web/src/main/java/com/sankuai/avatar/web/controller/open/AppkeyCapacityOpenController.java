package com.sankuai.avatar.web.controller.open;

import com.meituan.servicecatalog.api.annotations.*;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacityDTO;
import com.sankuai.avatar.web.dto.whitelist.ServiceWhitelistDTO;
import com.sankuai.avatar.web.request.AppkeyCapacityPageRequest;
import com.sankuai.avatar.web.service.AppkeyCapacityService;
import com.sankuai.avatar.web.service.WhitelistService;
import com.sankuai.avatar.web.transfer.capacity.AppkeyCapacityVOTransfer;
import com.sankuai.avatar.web.vo.capacity.AppKeyCapacityDetailVO;
import com.sankuai.avatar.web.vo.capacity.AppkeyCapacityVO;
import com.sankuai.avatar.web.vo.capacity.AppkeySetCapacityVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 容灾等级
 *
 * @author chenxinli
 */
@InterfaceDoc(
        displayName = "业务容灾信息",
        type = "restful",
        description = "Avatar业务服务容灾信息接口",
        scenarios = "接口用于查看服务各链路的容灾等级信息"
)
@Slf4j
@RestController
@RequestMapping("/open/api/v2/avatar/capacity")
public class AppkeyCapacityOpenController {

    private final AppkeyCapacityService appkeyCapacityService;
    private final WhitelistService whitelistService;

    @Autowired
    public AppkeyCapacityOpenController(AppkeyCapacityService appkeyCapacityService,
                                        WhitelistService whitelistService) {
        this.appkeyCapacityService = appkeyCapacityService;
        this.whitelistService = whitelistService;
    }

    @MethodDoc(displayName = "业务容灾分页查询接口",
               description = "接口用于分页查询业务 appkey 的容灾等级信息",
               parameters = {
                       @ParamDoc(
                               name = "pageRequest",
                               description = "业务容灾分页查询支持的查询参数",
                               paramType = ParamType.REQUEST_PARAM,
                               requiredness = Requiredness.OPTIONAL
                       )
               },
               requestMethods = {HttpMethod.GET},
               restExampleUrl="http://avatar.vip.sankuai.com/open/api/v2/avatar/capacity?appkey=xxx",
               responseParams = {
                    @ParamDoc(name = "code", description = "返回码"),
                    @ParamDoc(name = "message", description = "返回描述"),
                    @ParamDoc(name = "data", description = "业务容灾分页查询数据", type = PageResponse.class),
              },
              restExampleResponseData = "{\n" +
                      "  \"code\": 0,\n" +
                      "  \"message\": \"操作成功\",\n" +
                      "  \"data\": {\n" +
                      "    \"totalCount\": 10,\n" +
                      "    \"page\": 1,\n" +
                      "    \"totalPage\": 1,\n" +
                      "    \"pageSize\": 10,\n" +
                      "    \"items\": [\n" +
                      "      {\n" +
                      "        \"appkey\": \"com.sankuai.deliveryturing.nextbox.dispatch\",\n" +
                      "        \"cellName\": \"banma-north\",\n" +
                      "        \"capacityLevel\": 5,\n" +
                      "        \"standardLevel\": 4,\n" +
                      "        \"isCapacityStandard\": true,\n" +
                      "        \"standardReason\": \"资源利用率\",\n" +
                      "        \"standardTips\": \"\",\n" +
                      "        \"isWhite\": true,\n" +
                      "        \"whiteReason\": \"test\",\n" +
                      "        \"set\": true,\n" +
                      "        \"updateTime\": \"2022-09-09 11:42:09\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"appkey\": \"com.sankuai.deliveryturing.nextbox.dispatch\",\n" +
                      "        \"cellName\": \"\",\n" +
                      "        \"capacityLevel\": 5,\n" +
                      "        \"standardLevel\": 4,\n" +
                      "        \"isCapacityStandard\": true,\n" +
                      "        \"standardReason\": \"该服务已接入弹性伸缩，且满足3级容灾，判定容灾等级为5\",\n" +
                      "        \"standardTips\": \"\",\n" +
                      "        \"isWhite\": true,\n" +
                      "        \"whiteReason\": \"test\",\n" +
                      "        \"set\": false,\n" +
                      "        \"updateTime\": \"2022-09-09 11:42:09\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"appkey\": \"com.sankuai.deliveryturing.nextbox.dispatch\",\n" +
                      "        \"cellName\": \"banma-huabei\",\n" +
                      "        \"capacityLevel\": 5,\n" +
                      "        \"standardLevel\": 4,\n" +
                      "        \"isCapacityStandard\": true,\n" +
                      "        \"standardReason\": \"资源利用率\",\n" +
                      "        \"standardTips\": \"\",\n" +
                      "        \"isWhite\": true,\n" +
                      "        \"whiteReason\": \"test\",\n" +
                      "        \"set\": true,\n" +
                      "        \"updateTime\": \"2022-09-09 11:42:09\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"appkey\": \"com.sankuai.deliveryturing.nextbox.dispatch\",\n" +
                      "        \"cellName\": \"banma-huadong\",\n" +
                      "        \"capacityLevel\": 5,\n" +
                      "        \"standardLevel\": 4,\n" +
                      "        \"isCapacityStandard\": true,\n" +
                      "        \"standardReason\": \"资源利用率\",\n" +
                      "        \"standardTips\": \"\",\n" +
                      "        \"isWhite\": true,\n" +
                      "        \"whiteReason\": \"test\",\n" +
                      "        \"set\": true,\n" +
                      "        \"updateTime\": \"2022-09-09 11:42:09\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"appkey\": \"com.sankuai.deliveryturing.nextbox.dispatch\",\n" +
                      "        \"cellName\": \"banma-exp\",\n" +
                      "        \"capacityLevel\": 0,\n" +
                      "        \"standardLevel\": 4,\n" +
                      "        \"isCapacityStandard\": true,\n" +
                      "        \"standardReason\": \"【该服务链路已加入容灾等级白名单，默认达标】，真实容灾等级信息：单机器, 建议扩容至少一台机器\",\n" +
                      "        \"standardTips\": \"\",\n" +
                      "        \"isWhite\": true,\n" +
                      "        \"whiteReason\": \"test\",\n" +
                      "        \"set\": true,\n" +
                      "        \"updateTime\": \"2022-09-09 11:42:09\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"appkey\": \"com.sankuai.deliveryturing.nextbox.dispatch\",\n" +
                      "        \"cellName\": \"banma-west\",\n" +
                      "        \"capacityLevel\": 5,\n" +
                      "        \"standardLevel\": 4,\n" +
                      "        \"isCapacityStandard\": true,\n" +
                      "        \"standardReason\": \"资源利用率\",\n" +
                      "        \"standardTips\": \"\",\n" +
                      "        \"isWhite\": true,\n" +
                      "        \"whiteReason\": \"test\",\n" +
                      "        \"set\": true,\n" +
                      "        \"updateTime\": \"2022-09-09 11:42:09\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"appkey\": \"com.sankuai.deliveryturing.nextbox.dispatch\",\n" +
                      "        \"cellName\": \"banma-south\",\n" +
                      "        \"capacityLevel\": 5,\n" +
                      "        \"standardLevel\": 4,\n" +
                      "        \"isCapacityStandard\": true,\n" +
                      "        \"standardReason\": \"资源利用率\",\n" +
                      "        \"standardTips\": \"\",\n" +
                      "        \"isWhite\": true,\n" +
                      "        \"whiteReason\": \"test\",\n" +
                      "        \"set\": true,\n" +
                      "        \"updateTime\": \"2022-09-09 11:42:09\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"appkey\": \"com.sankuai.deliveryturing.nextbox.dispatch\",\n" +
                      "        \"cellName\": \"banma-east\",\n" +
                      "        \"capacityLevel\": 5,\n" +
                      "        \"standardLevel\": 4,\n" +
                      "        \"isCapacityStandard\": true,\n" +
                      "        \"standardReason\": \"资源利用率\",\n" +
                      "        \"standardTips\": \"\",\n" +
                      "        \"isWhite\": true,\n" +
                      "        \"whiteReason\": \"test\",\n" +
                      "        \"set\": true,\n" +
                      "        \"updateTime\": \"2022-09-09 11:42:09\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"appkey\": \"com.sankuai.deliveryturing.nextbox.dispatch\",\n" +
                      "        \"cellName\": \"banma-north-gray\",\n" +
                      "        \"capacityLevel\": 5,\n" +
                      "        \"standardLevel\": 4,\n" +
                      "        \"isCapacityStandard\": true,\n" +
                      "        \"standardReason\": \"SET间部署均衡\",\n" +
                      "        \"standardTips\": \"\",\n" +
                      "        \"isWhite\": false,\n" +
                      "        \"whiteReason\": \"\",\n" +
                      "        \"set\": true,\n" +
                      "        \"updateTime\": \"2023-03-03 15:56:05\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"appkey\": \"com.sankuai.deliveryturing.nextbox.dispatch\",\n" +
                      "        \"cellName\": \"banma-east-gray\",\n" +
                      "        \"capacityLevel\": 5,\n" +
                      "        \"standardLevel\": 4,\n" +
                      "        \"isCapacityStandard\": true,\n" +
                      "        \"standardReason\": \"SET间部署均衡\",\n" +
                      "        \"standardTips\": \"\",\n" +
                      "        \"isWhite\": false,\n" +
                      "        \"whiteReason\": \"\",\n" +
                      "        \"set\": true,\n" +
                      "        \"updateTime\": \"2023-03-03 15:56:05\"\n" +
                      "      }\n" +
                      "    ]\n" +
                      "  }\n" +
                      "}"
    )
    @GetMapping("")
    public PageResponse<AppkeyCapacityVO> getPageAppkeyCapacity(AppkeyCapacityPageRequest pageRequest) {
        PageResponse<AppkeyCapacityVO> pageResponse = new PageResponse<>();
        PageResponse<AppkeyCapacityDTO> capacityDTOPageResponse = appkeyCapacityService.queryPage(pageRequest);
        pageResponse.setPage(pageRequest.getPage());
        pageResponse.setPageSize(pageRequest.getPageSize());
        pageResponse.setTotalCount(capacityDTOPageResponse.getTotalCount());
        pageResponse.setTotalPage(capacityDTOPageResponse.getTotalPage());
        if (Boolean.TRUE.equals(pageRequest.getIsFullField())) {
            pageResponse.setItems(AppkeyCapacityVOTransfer.INSTANCE.toVOList(capacityDTOPageResponse.getItems()));
        } else {
            pageResponse.setItems(AppkeyCapacityVOTransfer.INSTANCE.toPartVOList(capacityDTOPageResponse.getItems()));
        }
        return pageResponse;
    }

    @MethodDoc(displayName = "查询指定appkey业务容灾接口",
               description = "接口用于查询指定 appkey 的容灾等级信息",
               parameters = {
                  @ParamDoc(name = "appkey", description = "服务名称", paramType = ParamType.REQUEST_PARAM)
               },
               requestMethods = {HttpMethod.GET},
               restExampleUrl="http://avatar.vip.sankuai.com/open/api/v2/avatar/capacity/xxx",
               responseParams = {
                    @ParamDoc(name = "code", description = "返回码"),
                    @ParamDoc(name = "message", description = "返回描述"),
                    @ParamDoc(name = "data", description = "业务服务容灾信息", type = List.class),
              },
              restExampleResponseData = "{\n" +
                      "  \"code\": 0,\n" +
                      "  \"message\": \"操作成功\",\n" +
                      "  \"data\": [\n" +
                      "    {\n" +
                      "      \"appkey\": \"com.sankuai.avatar.web\",\n" +
                      "      \"cellName\": \"\",\n" +
                      "      \"capacityLevel\": 5,\n" +
                      "      \"standardLevel\": 4,\n" +
                      "      \"isCapacityStandard\": true,\n" +
                      "      \"standardReason\": \"该服务已接入弹性伸缩，且满足3级容灾，判定容灾等级为5\",\n" +
                      "      \"standardTips\": \"\",\n" +
                      "      \"utilization\": {\n" +
                      "        \"value\": 0.3542799285714286,\n" +
                      "        \"lastWeekValue\": 0.3761492371559143,\n" +
                      "        \"lastWeekValueWithoutSet\": 0.3761492371559143,\n" +
                      "        \"yearPeekValue\": 0.5559848333333333\n" +
                      "      },\n" +
                      "      \"utilizationStandard\": \"STANDARD\",\n" +
                      "      \"whiteList\": [],\n" +
                      "      \"whitelists\": [],\n" +
                      "      \"middleWareList\": [\n" +
                      "        {\n" +
                      "          \"middleWare\": \"CRANE\",\n" +
                      "          \"used\": true\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"middleWare\": \"MGW\",\n" +
                      "          \"used\": false\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"middleWare\": \"OCEANUS_HTTP\",\n" +
                      "          \"used\": true\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"middleWare\": \"MQ\",\n" +
                      "          \"used\": true\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"middleWare\": \"OCTO_HTTP\",\n" +
                      "          \"used\": true\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"middleWare\": \"OCTO_TRIFT\",\n" +
                      "          \"used\": false\n" +
                      "        }\n" +
                      "      ],\n" +
                      "      \"hostList\": [\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-gh-avatar-web01\",\n" +
                      "          \"ip\": \"10.20.198.110\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"gh\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-gh-avatar-web02\",\n" +
                      "          \"ip\": \"10.30.95.190\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"gh\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-hh-avatar-web01\",\n" +
                      "          \"ip\": \"10.34.102.215\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"hh\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-hh-avatar-web02\",\n" +
                      "          \"ip\": \"10.34.165.43\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"hh\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-tx-avatar-web03\",\n" +
                      "          \"ip\": \"10.59.128.93\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"tx_bj0\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-mt-avatar-web02\",\n" +
                      "          \"ip\": \"10.198.188.138\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"mt\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-mt-avatar-web04\",\n" +
                      "          \"ip\": \"10.198.32.121\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"mt\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        }\n" +
                      "      ],\n" +
                      "      \"hosts\": [\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-gh-avatar-web01\",\n" +
                      "          \"ip\": \"10.20.198.110\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"gh\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-gh-avatar-web02\",\n" +
                      "          \"ip\": \"10.30.95.190\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"gh\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-hh-avatar-web01\",\n" +
                      "          \"ip\": \"10.34.102.215\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"hh\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-hh-avatar-web02\",\n" +
                      "          \"ip\": \"10.34.165.43\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"hh\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-tx-avatar-web03\",\n" +
                      "          \"ip\": \"10.59.128.93\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"tx_bj0\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-mt-avatar-web02\",\n" +
                      "          \"ip\": \"10.198.188.138\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"mt\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-mt-avatar-web04\",\n" +
                      "          \"ip\": \"10.198.32.121\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"mt\",\n" +
                      "          \"cpuCount\": 4,\n" +
                      "          \"memSize\": 8,\n" +
                      "          \"region\": \"BJ\"\n" +
                      "        }\n" +
                      "      ],\n" +
                      "      \"octoHttpProviderList\": [\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-mt-avatar-web04\",\n" +
                      "          \"ip\": \"10.198.32.121\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"mt\",\n" +
                      "          \"protocol\": \"http\",\n" +
                      "          \"status\": 2\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-gh-avatar-web02\",\n" +
                      "          \"ip\": \"10.30.95.190\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"gh\",\n" +
                      "          \"protocol\": \"http\",\n" +
                      "          \"status\": 2\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-hh-avatar-web01\",\n" +
                      "          \"ip\": \"10.34.102.215\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"hh\",\n" +
                      "          \"protocol\": \"http\",\n" +
                      "          \"status\": 2\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-hh-avatar-web02\",\n" +
                      "          \"ip\": \"10.34.165.43\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"hh\",\n" +
                      "          \"protocol\": \"http\",\n" +
                      "          \"status\": 2\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-tx-avatar-web03\",\n" +
                      "          \"ip\": \"10.59.128.93\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"tx_bj0\",\n" +
                      "          \"protocol\": \"http\",\n" +
                      "          \"status\": 2\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-gh-avatar-web01\",\n" +
                      "          \"ip\": \"10.20.198.110\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"gh\",\n" +
                      "          \"protocol\": \"http\",\n" +
                      "          \"status\": 2\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"hostName\": \"set-mt-avatar-web02\",\n" +
                      "          \"ip\": \"10.198.188.138\",\n" +
                      "          \"cell\": \"\",\n" +
                      "          \"idc\": \"mt\",\n" +
                      "          \"protocol\": \"http\",\n" +
                      "          \"status\": 2\n" +
                      "        }\n" +
                      "      ],\n" +
                      "      \"octoThriftProviderList\": [],\n" +
                      "      \"accessComponentList\": [\n" +
                      "        {\n" +
                      "          \"name\": \"elastic\",\n" +
                      "          \"access\": true,\n" +
                      "          \"cname\": \"弹性伸缩\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"name\": \"plus\",\n" +
                      "          \"access\": true,\n" +
                      "          \"cname\": \"是否plus发布过\"\n" +
                      "        },\n" +
                      "        {\n" +
                      "          \"name\": \"nest\",\n" +
                      "          \"access\": false,\n" +
                      "          \"cname\": \"是否为nest服务\"\n" +
                      "        }\n" +
                      "      ],\n" +
                      "      \"isPaas\": false,\n" +
                      "      \"canSingleHostRestart\": true,\n" +
                      "      \"stateful\": false,\n" +
                      "      \"orgPath\": \"100046,150042,1573,150044,1021866\",\n" +
                      "      \"orgDisplayName\": \"基础研发平台/基础技术部/服务运维部/运维工具开发组/变更管理开发组\",\n" +
                      "      \"updateBy\": \"avatar\",\n" +
                      "      \"isWhite\": false,\n" +
                      "      \"whiteReason\": \"\",\n" +
                      "      \"set\": false,\n" +
                      "      \"updateTime\": \"2023-03-03 15:55:43\"\n" +
                      "    }\n" +
                      "  ]\n" +
                      "}"
    )
    @GetMapping("/{appkey}")
    public List<AppkeyCapacityVO> getAppkeyCapacity(@PathVariable("appkey") String appKey) {
        List<AppkeyCapacityDTO> capacityDTOList = appkeyCapacityService.getAppkeyCapacityByAppkey(appKey);
        return AppkeyCapacityVOTransfer.INSTANCE.toVOList(capacityDTOList);
    }

    @MethodDoc(displayName = "查询指定appkey业务容灾详情接口",
            description = "接口用于查询指定 appkey 的容灾等级详情信息，包括 appkey 容灾等级信息、属性信息等",
            parameters = {
                    @ParamDoc(name = "appkey", description = "服务名称", paramType = ParamType.REQUEST_PARAM)
            },
            requestMethods = {HttpMethod.GET},
            restExampleUrl="http://avatar.vip.sankuai.com/open/api/v2/avatar/capacity/xxx/detail",
            responseParams = {
                    @ParamDoc(name = "code", description = "返回码"),
                    @ParamDoc(name = "message", description = "返回描述"),
                    @ParamDoc(name = "data", description = "业务容灾详细信息", type = AppKeyCapacityDetailVO.class)
            },
            restExampleResponseData = "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"message\": \"操作成功\",\n" +
                    "  \"data\": {\n" +
                    "    \"capacityLevels\": [\n" +
                    "      {\n" +
                    "        \"cellName\": \"\",\n" +
                    "        \"capacityLevel\": 5,\n" +
                    "        \"standardLevel\": 4,\n" +
                    "        \"isCapacityStandard\": true,\n" +
                    "        \"standardReason\": \"该服务已接入弹性伸缩，且满足3级容灾，判定容灾等级为5\",\n" +
                    "        \"standardTips\": \"\",\n" +
                    "        \"isWhite\": false,\n" +
                    "        \"whiteReason\": \"\",\n" +
                    "        \"whiteStartTime\": null,\n" +
                    "        \"expireTime\": null,\n" +
                    "        \"updateTime\": \"2023-03-03 15:55:43\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"capacityWhiteList\": [\n" +
                    "      {\n" +
                    "        \"app\": \"capacity\",\n" +
                    "        \"isWhite\": false,\n" +
                    "        \"canApply\": true,\n" +
                    "        \"white\": null,\n" +
                    "        \"apply\": {\n" +
                    "          \"app\": \"capacity\",\n" +
                    "          \"cname\": \"容灾等级\",\n" +
                    "          \"desc\": \"容灾等级白名单，添加后将不再关注该服务容灾等级信息\"\n" +
                    "        }\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"app\": \"auto-migration\",\n" +
                    "        \"isWhite\": false,\n" +
                    "        \"canApply\": true,\n" +
                    "        \"white\": null,\n" +
                    "        \"apply\": {\n" +
                    "          \"app\": \"auto-migration\",\n" +
                    "          \"cname\": \"自动迁移\",\n" +
                    "          \"desc\": \"添加后认为服务可以自动迁移，会影响容灾等级1->2的判断\"\n" +
                    "        }\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"hasSet\": false,\n" +
                    "    \"defaultCapacityLevel\": 5,\n" +
                    "    \"standardCapacityLevel\": 4,\n" +
                    "    \"isStandard\": true,\n" +
                    "    \"standardReason\": \"业务容灾治理可参考：<a href='https://km.sankuai.com/page/114022614' target='_blank'>应用服务容灾等级定义与达标要求</a>，每天4:00、9:00、13:00、17:00、21:00 发起容灾计算，持续时间大概一个小时\",\n" +
                    "    \"standardTips\": \"\",\n" +
                    "    \"canExpireApply\": false\n" +
                    "  }\n" +
                    "}"
    )
    @GetMapping("/{appkey}/detail")
    public AppKeyCapacityDetailVO getAppKeyCapacityDetail(@PathVariable("appkey") String appkey){
        List<AppkeyCapacityDTO> dtoList = appkeyCapacityService.getAppkeyCapacityByAppkey(appkey);
        if (CollectionUtils.isEmpty(dtoList)) {return null;}
        AppKeyCapacityDetailVO detailVO = new AppKeyCapacityDetailVO();
        List<AppkeySetCapacityVO> setCapacityVOList = AppkeyCapacityVOTransfer.INSTANCE.toAppkeySetCapacityVOList(dtoList);
        detailVO.setCapacityLevels(setCapacityVOList);
        for (AppkeySetCapacityVO capacityVO : setCapacityVOList) {
            if (Objects.isNull(capacityVO.getExpireTime())) {continue;}
            List<ServiceWhitelistDTO> whitelist = whitelistService.getServiceWhitelistByAppkey(appkey, WhiteType.CAPACITY);
            if (CollectionUtils.isNotEmpty(whitelist)) {
                capacityVO.setWhiteStartTime(whitelist.get(0).getAddTime());
            }
        }
        detailVO.setHasSet(dtoList.size() > 1);
        // 计算整体容灾：
        // 无加白或全部加白所有整体取小；有加白剔除加白，然后所有未加白的整体取小
        setCapacityVOList.sort((v1, v2) -> {
            if (Objects.equals(-1, v1.getCapacityLevel()) || Objects.equals(-1, v2.getCapacityLevel())) {
                return Objects.equals(-1, v1.getCapacityLevel()) ? 1 : -1;
            } else if (!v1.getIsWhite().equals(v2.getIsWhite())) {
                return Boolean.TRUE.equals(v1.getIsWhite()) ? 1 : -1;
            } else if (!v1.getIsCapacityStandard().equals(v2.getIsCapacityStandard())) {
                return Boolean.TRUE.equals(v1.getIsCapacityStandard()) ? 1 : -1;
            } else {
                return v1.getCapacityLevel() - v2.getCapacityLevel();
            }
        });
        // 判断加白是否可续期：生效时间长度小于3天可续期
        AppkeySetCapacityVO vo = setCapacityVOList.stream().filter(setCapacity ->
                Boolean.TRUE.equals(setCapacity.getIsWhite())
                        && Objects.nonNull(setCapacity.getExpireTime())
                        && (int) ((setCapacity.getExpireTime().getTime() - System.currentTimeMillis())/(1000*3600*24)) < 3
        ).findFirst().orElse(null);
        detailVO.setCanExpireApply(Objects.nonNull(vo));
        AppkeySetCapacityVO minSetCapacity = setCapacityVOList.get(0);
        detailVO.setDefaultCapacityLevel(minSetCapacity.getCapacityLevel());
        detailVO.setStandardCapacityLevel(minSetCapacity.getStandardLevel());
        detailVO.setIsStandard(minSetCapacity.getIsCapacityStandard());
        String tips = "业务容灾治理可参考：<a href='https://km.sankuai.com/page/114022614' target='_blank'>应用服务容灾等级定义与达标要求</a>，每天4:00、9:00、13:00、17:00、21:00 发起容灾计算，持续时间大概一个小时";
        detailVO.setStandardReason(tips);
        detailVO.setStandardTips(minSetCapacity.getStandardTips());
        detailVO.setCapacityWhiteList(AppkeyCapacityVOTransfer.INSTANCE.toAppkeyCapaictyWhiteVOList(dtoList));
        return detailVO;
    }
}

