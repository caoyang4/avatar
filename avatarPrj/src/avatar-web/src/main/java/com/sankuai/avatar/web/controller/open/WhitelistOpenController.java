package com.sankuai.avatar.web.controller.open;

import com.meituan.servicecatalog.api.annotations.*;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.sdk.entity.servicecatalog.AppKey;
import com.sankuai.avatar.sdk.manager.ServiceCatalogAppKey;
import com.sankuai.avatar.web.dto.whitelist.OwtSetWhitelistDTO;
import com.sankuai.avatar.web.dto.whitelist.ServiceWhitelistDTO;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.request.OwtSetWhitelistPageRequest;
import com.sankuai.avatar.web.request.WhitelistPageRequest;
import com.sankuai.avatar.web.service.OwtSetWhitelistService;
import com.sankuai.avatar.web.service.WhitelistService;
import com.sankuai.avatar.web.transfer.whitelist.OwtSetWhitelistVOTransfer;
import com.sankuai.avatar.web.transfer.whitelist.ServiceWhitelistVOTransfer;
import com.sankuai.avatar.web.vo.whitelist.OwtSetWhitelistVO;
import com.sankuai.avatar.web.vo.whitelist.ServiceWhitelistVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-08-26 13:54
 */
@InterfaceDoc(
        displayName = "白名单信息",
        type = "restful",
        description = "白名单信息接口",
        scenarios = "接口用于查看/上报白名单"
)
@Validated
@RestController
@RequestMapping("/open/api/v2/avatar/whitelist")
public class WhitelistOpenController {

    private final ServiceCatalogAppKey serviceCatalogAppKey;
    private final WhitelistService whitelistService;
    private final OwtSetWhitelistService owtSetWhitelistService;

    @Autowired
    public WhitelistOpenController(ServiceCatalogAppKey serviceCatalogAppKey,
                                   WhitelistService whitelistService,
                                   OwtSetWhitelistService owtSetWhitelistService) {
        this.serviceCatalogAppKey = serviceCatalogAppKey;
        this.whitelistService = whitelistService;
        this.owtSetWhitelistService = owtSetWhitelistService;
    }

    private static final int BATCH_LIMIT_SIZE = 100;

    @MethodDoc(displayName = "服务白名单信息分页查询接口",
            description = "接口用于分页查询服务白名单信息",
            parameters = {
                    @ParamDoc(
                            name = "pageRequest",
                            description = "服务白名单分页查询支持的参数",
                            paramType = ParamType.REQUEST_PARAM,
                            requiredness = Requiredness.OPTIONAL
                    )
            },
            requestMethods = {HttpMethod.GET},
            restExampleUrl="http://avatar.vip.sankuai.com/open/api/v2/avatar/whitelist?app=capacity",
            responseParams = {
                    @ParamDoc(name = "code", description = "返回码"),
                    @ParamDoc(name = "message", description = "返回描述"),
                    @ParamDoc(name = "data", description = "服务白名单信息分页查询数据", type = PageResponse.class),
            },
            example = "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"message\": \"操作成功\",\n" +
                    "  \"data\": {\n" +
                    "    \"totalCount\": 1,\n" +
                    "    \"page\": 1,\n" +
                    "    \"totalPage\": 1,\n" +
                    "    \"pageSize\": 10,\n" +
                    "    \"items\": [\n" +
                    "      {\n" +
                    "        \"id\": 6,\n" +
                    "        \"app\": \"capacity\",\n" +
                    "        \"cname\": \"容灾等级\",\n" +
                    "        \"reason\": \"需特殊处理的集群\",\n" +
                    "        \"appkey\": \"com.sankuai.dba.dba.n128_dba_offset_128\",\n" +
                    "        \"application\": \"rdsnode\",\n" +
                    "        \"orgIds\": \"100046,877,10009845\",\n" +
                    "        \"setName\": \"\",\n" +
                    "        \"inputUser\": \"likai44\",\n" +
                    "        \"addTime\": \"2021-01-01 00:00:00\",\n" +
                    "        \"endTime\": \"2200-12-31 00:00:00\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}"
    )
    @GetMapping("")
    public PageResponse<ServiceWhitelistVO> getPageServiceWhitelist(@Valid WhitelistPageRequest pageRequest){
        PageResponse<ServiceWhitelistDTO> dtoPageResponse = whitelistService.queryPage(pageRequest);
        PageResponse<ServiceWhitelistVO> pageResponse = new PageResponse<>();
        pageResponse.setPage(dtoPageResponse.getPage());
        pageResponse.setPageSize(dtoPageResponse.getPageSize());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setItems(ServiceWhitelistVOTransfer.INSTANCE.toVOList(dtoPageResponse.getItems()));
        return pageResponse;
    }

    @MethodDoc(displayName = "owt-set白名单信息分页查询接口",
            description = "接口用于分页查询owt-set白名单信息",
            parameters = {
                    @ParamDoc(
                            name = "pageRequest",
                            description = "owt-set白名单分页查询支持的参数",
                            paramType = ParamType.REQUEST_PARAM,
                            requiredness = Requiredness.OPTIONAL
                    )
            },
            requestMethods = {HttpMethod.GET},
            restExampleUrl="http://avatar.vip.sankuai.com/open/api/v2/avatar/whitelist/owt?owt=meituan.sre",
            responseParams = {
                    @ParamDoc(name = "code", description = "返回码"),
                    @ParamDoc(name = "message", description = "返回描述"),
                    @ParamDoc(name = "data", description = "服务白名单信息分页查询数据", type = PageResponse.class),
            },
            restExampleResponseData = "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"message\": \"操作成功\",\n" +
                    "  \"data\": {\n" +
                    "    \"totalCount\": 562,\n" +
                    "    \"page\": 1,\n" +
                    "    \"totalPage\": 57,\n" +
                    "    \"pageSize\": 10,\n" +
                    "    \"items\": [\n" +
                    "      {\n" +
                    "        \"id\": 1,\n" +
                    "        \"app\": \"capacity\",\n" +
                    "        \"owt\": \"meituan.fe\",\n" +
                    "        \"setName\": \"gray-release-fe-dd\",\n" +
                    "        \"reason\": \"灰度链路，仅用于测试，无需考量容灾\",\n" +
                    "        \"applyBy\": \"chexinyue\",\n" +
                    "        \"startTime\": \"2022-09-06 04:34:42\",\n" +
                    "        \"endTime\": \"2122-08-13 04:34:42\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 2,\n" +
                    "        \"app\": \"capacity\",\n" +
                    "        \"owt\": \"meituan.zc\",\n" +
                    "        \"setName\": \"gray-release-zc-open\",\n" +
                    "        \"reason\": \"灰度链路\",\n" +
                    "        \"applyBy\": \"wangtingting47\",\n" +
                    "        \"startTime\": \"2022-09-06 04:34:43\",\n" +
                    "        \"endTime\": \"2122-08-13 04:34:43\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 3,\n" +
                    "        \"app\": \"capacity\",\n" +
                    "        \"owt\": \"meituan.sjst\",\n" +
                    "        \"setName\": \"sjst-rms-rtdw-m\",\n" +
                    "        \"reason\": \"灰度链路\",\n" +
                    "        \"applyBy\": \"wangtingting47\",\n" +
                    "        \"startTime\": \"2022-09-06 04:34:44\",\n" +
                    "        \"endTime\": \"2122-08-13 04:34:44\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 4,\n" +
                    "        \"app\": \"capacity\",\n" +
                    "        \"owt\": \"meituan.sjst\",\n" +
                    "        \"setName\": \"sjst-rms-rtdw-xscm\",\n" +
                    "        \"reason\": \"灰度链路\",\n" +
                    "        \"applyBy\": \"wangtingting47\",\n" +
                    "        \"startTime\": \"2022-09-06 04:34:45\",\n" +
                    "        \"endTime\": \"2122-08-13 04:34:45\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 5,\n" +
                    "        \"app\": \"capacity\",\n" +
                    "        \"owt\": \"dianping.dprec\",\n" +
                    "        \"setName\": \"gray-release-dprec-farseer-core\",\n" +
                    "        \"reason\": \"点评搜调测灰度链路\",\n" +
                    "        \"applyBy\": \"zhengfei14\",\n" +
                    "        \"startTime\": \"2022-09-06 04:34:46\",\n" +
                    "        \"endTime\": \"2122-08-13 04:34:46\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 6,\n" +
                    "        \"app\": \"capacity\",\n" +
                    "        \"owt\": \"dianping.dprec\",\n" +
                    "        \"setName\": \"gray-release-dprec-farseer-material\",\n" +
                    "        \"reason\": \"点评搜调测灰度链路\",\n" +
                    "        \"applyBy\": \"zhengfei14\",\n" +
                    "        \"startTime\": \"2022-09-06 04:34:47\",\n" +
                    "        \"endTime\": \"2122-08-13 04:34:47\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 7,\n" +
                    "        \"app\": \"capacity\",\n" +
                    "        \"owt\": \"dianping.dprec\",\n" +
                    "        \"setName\": \"gray-release-dprec-cicd-line\",\n" +
                    "        \"reason\": \"点评搜调测灰度链路\",\n" +
                    "        \"applyBy\": \"zhengfei14\",\n" +
                    "        \"startTime\": \"2022-09-06 04:34:48\",\n" +
                    "        \"endTime\": \"2122-08-13 04:34:48\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 8,\n" +
                    "        \"app\": \"capacity\",\n" +
                    "        \"owt\": \"dianping.dprec\",\n" +
                    "        \"setName\": \"gray-release-dprec-rec-dbs\",\n" +
                    "        \"reason\": \"点评搜调测灰度链路\",\n" +
                    "        \"applyBy\": \"zhengfei14\",\n" +
                    "        \"startTime\": \"2022-09-06 04:34:49\",\n" +
                    "        \"endTime\": \"2122-08-13 04:34:49\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 9,\n" +
                    "        \"app\": \"capacity\",\n" +
                    "        \"owt\": \"dianping.base\",\n" +
                    "        \"setName\": \"gray-release-base-coin-freeshare\",\n" +
                    "        \"reason\": \"点评搜调测灰度链路\",\n" +
                    "        \"applyBy\": \"zhengfei14\",\n" +
                    "        \"startTime\": \"2022-09-06 04:34:50\",\n" +
                    "        \"endTime\": \"2122-08-13 04:34:50\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 10,\n" +
                    "        \"app\": \"capacity\",\n" +
                    "        \"owt\": \"meituan.sl\",\n" +
                    "        \"setName\": \"gray-release-sl-shoppingmall\",\n" +
                    "        \"reason\": \"点评搜调测灰度链路\",\n" +
                    "        \"applyBy\": \"zhengfei14\",\n" +
                    "        \"startTime\": \"2022-09-06 04:34:51\",\n" +
                    "        \"endTime\": \"2122-08-13 04:34:51\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}"
    )
    @GetMapping("/owt")
    public PageResponse<OwtSetWhitelistVO> getPageOwtSetWhitelist(OwtSetWhitelistPageRequest pageRequest){
        PageResponse<OwtSetWhitelistVO> pageResponse = new PageResponse<>();
        PageResponse<OwtSetWhitelistDTO> dtoPageResponse = owtSetWhitelistService.queryPage(pageRequest);
        pageResponse.setPage(dtoPageResponse.getPage());
        pageResponse.setPageSize(dtoPageResponse.getPageSize());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setItems(OwtSetWhitelistVOTransfer.INSTANCE.toVOList(dtoPageResponse.getItems()));
        return pageResponse;
    }

    @MethodDoc(displayName = "owt-set白名单上报接口",
            description = "接口用于上报owt-set白名单信息",
            parameters = {
                    @ParamDoc(name = "owtSetWhitelistVOList", rule = "数据类型为list，最少1条，最多100条", description = "白名单上报信息", paramType = ParamType.REQUEST_BODY, requiredness = Requiredness.REQUIRED)
            },
            requestMethods = {HttpMethod.POST},
            restExampleUrl="http://avatar.vip.sankuai.com/open/api/v2/avatar/whitelist/capacity/owt",
            responseParams = {
                    @ParamDoc(name = "code", description = "返回码"),
                    @ParamDoc(name = "message", description = "返回描述"),
                    @ParamDoc(name = "data", description = "返回信息，代表上报成功的数量"),
            },
            restExamplePostData = "[\n" +
                    "  {\n" +
                    "    \"app\":\"capacity\",\n" +
                    "    \"owt\": \"testowt66\",\n" +
                    "    \"setName\": \"test-set\",\n" +
                    "    \"applyBy\":\"young\",\n" +
                    "    \"reason\":\"无可奉告\",\n" +
                    "    \"startTime\": \"2022-08-22 14:56:48\",\n" +
                    "    \"endTime\": \"2296-05-26 23:59:59\"\n" +
                    "  }\n" +
                    "]",
            restExampleResponseData = "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"message\": \"操作成功\",\n" +
                    "  \"data\": 1\n" +
                    "}"
    )
    @PostMapping("/capacity/owt")
    public Integer reportOwtCapacityList(@RequestBody @Valid @Size(min = 1, max = BATCH_LIMIT_SIZE,
                                                    message = "批量上传每次至少1条，至多"+BATCH_LIMIT_SIZE+"条，请知悉！")
                                                     List<OwtSetWhitelistVO> owtSetWhitelistVOList){

        List<OwtSetWhitelistDTO> dtoList = OwtSetWhitelistVOTransfer.INSTANCE.toDTOList(owtSetWhitelistVOList);
        for (OwtSetWhitelistDTO owtSetWhitelistDTO : dtoList) {
            if (owtSetWhitelistDTO.getStartTime().after(owtSetWhitelistDTO.getEndTime())) {
                throw new SupportErrorException("白名单生效到期时间不能晚于起始时间");
            }
            if (owtSetWhitelistDTO.getEndTime().before(new Date())) {
                throw new SupportErrorException("白名单生效到期时间不能晚于当前时间");
            }
            owtSetWhitelistService.saveOwtSetWhitelist(owtSetWhitelistDTO);
        }
        return owtSetWhitelistVOList.size();
    }

    @MethodDoc(displayName = "服务白名单上报接口",
            description = "接口用于上报服务白名单信息",
            parameters = {
                    @ParamDoc(name = "serviceWhitelistVOList", rule = "数据类型为list，最少1条，最多100条", description = "白名单上报信息", paramType = ParamType.REQUEST_BODY, requiredness = Requiredness.REQUIRED)
            },
            requestMethods = {HttpMethod.POST},
            restExampleUrl="http://avatar.vip.sankuai.com/open/api/v2/avatar/whitelist/capacity/appkey",
            responseParams = {
                    @ParamDoc(name = "code", description = "返回码"),
                    @ParamDoc(name = "message", description = "返回描述"),
                    @ParamDoc(name = "data", description = "返回信息，代表上报成功的数量"),
            },
            restExamplePostData = "[\n" +
                    "  {\n" +
                    "    \"appkey\":\"avatar-platform-web\",\n" +
                    "    \"input_user\":\"young\",\n" +
                    "    \"app\":\"capacity\",\n" +
                    "    \"add_time\":\"2022-08-01 00:00:00\",\n" +
                    "    \"end_time\":\"2022-09-05 23:59:59\",\n" +
                    "    \"reason\":\"test\",\n" +
                    "    \"set_name\":\"set1,set2\"\n" +
                    "  }\n" +
                    "]",
            restExampleResponseData = "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"message\": \"操作成功\",\n" +
                    "  \"data\": 1\n" +
                    "}"
    )
    @PostMapping("/capacity/appkey")
    public Integer reportAppkeyCapacityList(@RequestBody @Valid @Size(min = 1, max = BATCH_LIMIT_SIZE,
                                            message = "批量上传每次至少1条，至多"+BATCH_LIMIT_SIZE+"条，请知悉！")
                                                        List<ServiceWhitelistVO> serviceWhitelistVOList) throws Exception {
        for (ServiceWhitelistVO serviceWhitelistVO : serviceWhitelistVOList) {
            if (StringUtils.isEmpty(serviceWhitelistVO.getApplication()) || StringUtils.isEmpty(serviceWhitelistVO.getOrgIds())) {
                // TODO serviceCatalogAppKey待替换
                AppKey scAppKey = serviceCatalogAppKey.getAppKey(serviceWhitelistVO.getAppkey());
                serviceWhitelistVO.setApplication(scAppKey.getApplicationName().toLowerCase());
                serviceWhitelistVO.setOrgIds(scAppKey.getOrgIds());
            }
            if (serviceWhitelistVO.getAddTime().after(serviceWhitelistVO.getEndTime())) {
                throw new SupportErrorException("白名单生效到期时间不能晚于起始时间");
            }
            if (serviceWhitelistVO.getEndTime().before(new Date())) {
                throw new SupportErrorException("白名单生效到期时间不能晚于当前时间");
            }
        }
        List<ServiceWhitelistDTO> dtoList = ServiceWhitelistVOTransfer.INSTANCE.toDTOList(serviceWhitelistVOList);
        for (ServiceWhitelistDTO serviceWhitelistDTO : dtoList) {
            whitelistService.saveServiceWhitelist(serviceWhitelistDTO);
        }
        return serviceWhitelistVOList.size();
    }

}
