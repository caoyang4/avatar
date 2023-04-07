package com.sankuai.avatar.web.controller.open;

import com.meituan.mafka.client.producer.ProducerResult;
import com.meituan.mafka.client.producer.ProducerStatus;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.meituan.servicecatalog.api.annotations.*;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasCapacityDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasClientDTO;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.mq.capacity.producer.PaasCapacityReportProducer;
import com.sankuai.avatar.web.request.AppkeyPaasCapacityPageRequest;
import com.sankuai.avatar.web.service.AppkeyPaasCapacityService;
import com.sankuai.avatar.web.transfer.capacity.AppkeyPaasCapacityDTOTransfer;
import com.sankuai.avatar.web.util.GsonUtils;
import com.sankuai.avatar.web.vo.capacity.AppkeyPaasCapacityReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author caoyang
 * @create 2022-09-22 16:41
 */
@InterfaceDoc(
        displayName = "paas容灾信息",
        type = "restful",
        description = "Avatar paas服务容灾信息上报及查询",
        scenarios = "paas服务容灾信息接口可让paas上报或查询相关信息"
)
@Slf4j
@Validated
@RestController
@RequestMapping("/open/api/v2/avatar/capacity/paas")
public class AppkeyPaasCapacityOpenController {

    /**
     * 批量上报最大长度
     */
    private static final int BATCH_LIMIT_SIZE = 100;
    /**
     * 负责人上报最大数量
     */
    private static final int OWNER_LIMIT_SIZE = 10;
    /**
     * 单页查询最大数量
     */
    private static final int MAX_PAGE_SIZE = 500;

    private static final Logger logger = LoggerFactory.getLogger("logger_com.sankuai.avatar.web");

    private final AppkeyPaasCapacityService appkeyPaasCapacityService;
    private final PaasCapacityReportProducer paasCapacityProducer;

    @Autowired
    public AppkeyPaasCapacityOpenController(AppkeyPaasCapacityService appkeyPaasCapacityService,
                                            PaasCapacityReportProducer paasCapacityProducer){
        this.appkeyPaasCapacityService = appkeyPaasCapacityService;
        this.paasCapacityProducer = paasCapacityProducer;
    }

    @MdpConfig("PAAS_LIST:['RDS','Mafka','Squirrel','Cellar','Eagle','Oceanus']")
    private String paasNames;

    @MethodDoc(displayName = "paas容灾上报接口",
            description = "接口用于上报paas容灾等级信息",
            parameters = {
                    @ParamDoc(name = "paasCapacityList", rule = "数据类型为list，最少1条，最多100条", description = "上报信息", paramType = ParamType.REQUEST_BODY, requiredness = Requiredness.REQUIRED)
            },
            requestMethods = {HttpMethod.POST},
            restExampleUrl="http://avatar.vip.sankuai.com/open/api/v2/avatar/capacity/paas",
            responseParams = {
                    @ParamDoc(name = "code", description = "返回码"),
                    @ParamDoc(name = "message", description = "返回描述"),
                    @ParamDoc(name = "data", description = "返回信息, 0 代表上报成功，-1 代表上报失败"),
            },
            restExamplePostData = "[\n" +
                    "  {\n" +
                    "    \"capacityLevel\": 2,\n" +
                    "    \"clientAppkey\": [\n" +
                    "      \"com.meituan.movie.mmdb.comment4\",\n" +
                    "      \"com.meituan.movie.mmdb.comment5\"\n" +
                    "    ],\n" +
                    "    \"clientConfig\": [\n" +
                    "      {\n" +
                    "        \"value\": \"默认分配\",\n" +
                    "        \"key\": \"producer.cluster.dispatch.type\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"value\": \"true\",\n" +
                    "        \"key\": \"has.traffic.cluster\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"clientSdkVersion\": [\n" +
                    "      {\n" +
                    "        \"clientAppkey\": \"com.meituan.movie.mmdb.comment3\",\n" +
                    "        \"items\": [\n" +
                    "          {\n" +
                    "            \"groupId\": \"com.meituan.mafka\",\n" +
                    "            \"artifactId\": \"mafka-clientx\",\n" +
                    "            \"language\": \"java\",\n" +
                    "            \"version\": \"3.3.4\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"clientAppkey\": \"com.meituan.movie.mmdb.comment2\",\n" +
                    "        \"items\": [\n" +
                    "          {\n" +
                    "            \"groupId\": \"com.meituan.mafka\",\n" +
                    "            \"artifactId\": \"mafka-clientx\",\n" +
                    "            \"language\": \"java\",\n" +
                    "            \"version\": \"3.3.4\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"isCapacityStandard\": false,\n" +
                    "    \"isConfigStandard\": false,\n" +
                    "    \"isCore\": true,\n" +
                    "    \"isWhite\": false,\n" +
                    "    \"owner\": \"yangying14,lutao02,tangchao03,lichao23,zhenghao08,wangxiao15,liyongqiang06,sunzhiwei02\",\n" +
                    "    \"paasAppkey\": \"com.sankuai.inf.mafka.broker\",\n" +
                    "    \"paasName\": \"Mafka\",\n" +
                    "    \"standardConfig\": [\n" +
                    "      {\n" +
                    "        \"key\": \"producer.cluster.dispatch.type\",\n" +
                    "        \"value\": \"同地域集群优先\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"key\": \"producer.cluster.dispatch.type\",\n" +
                    "        \"value\": \"全部集群\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"standardLevel\": 4,\n" +
                    "    \"standardReason\": \"主题单集群!!!\",\n" +
                    "    \"standardTips\": \"建议同地域双集群多分区多副本\",\n" +
                    "    \"standardVersion\": [\n" +
                    "      {\n" +
                    "        \"groupId\": \"com.meituan.mafka\",\n" +
                    "        \"artifactId\": \"mafka-clientx\",\n" +
                    "        \"language\": \"java\",\n" +
                    "        \"version\": \"3.5.7\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"type\": \"TOPIC\",\n" +
                    "    \"typeComment\": \"auto import from zk when updating\",\n" +
                    "    \"typeName\": \"maoyan-mmdb-comment\",\n" +
                    "    \"cilentRole\": \"producer\",\n" +
                    "    \"updateBy\": \"zhangpanwei02\",\n" +
                    "    \"whiteReason\": \"\",\n" +
                    "    \"isSet\":true,\n" +
                    "    \"setName\":\"waimai-south\",\n" +
                    "    \"setType\":\"C\"\n" +
                    "  }\n" +
                    "]",
            restExampleResponseData = "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"message\": \"操作成功\",\n" +
                    "  \"data\": 0\n" +
                    "}"
    )
    @PostMapping("")
    public Integer reportPaasCapacityList(@RequestBody @Valid @Size(min = 1, max = BATCH_LIMIT_SIZE,
                                                                    message = "批量上报每次至少1条，至多"+BATCH_LIMIT_SIZE+"条，请知悉！")
                                          List<AppkeyPaasCapacityReportVO> paasCapacityList){
        // 允许上报的paasName
        List<String> paasList = Arrays.asList(GsonUtils.deserialization(paasNames, String[].class));

        AtomicBoolean isSuccess = new AtomicBoolean(true);
        paasCapacityList.parallelStream().forEach(param -> {
            if (CollectionUtils.isEmpty(paasList) || !paasList.contains(param.getPaasName())) {
                throw new SupportErrorException(String.format("暂不支持 %s 上报容灾等级，如有疑问，请联系avatar同学", param.getPaasName()));
            }
            checkValidPaasReport(param);
            if (StringUtils.isNotEmpty(param.getOwner())) {
                String[] owners = param.getOwner().split(",");
                if (owners.length > OWNER_LIMIT_SIZE) {
                    param.setOwner(StringUtils.join(Arrays.copyOf(owners, OWNER_LIMIT_SIZE),","));
                }
            }
            param.setDefaultValue();
            // 上报 mafka
            ProducerResult result = paasCapacityProducer.paasCapacityReport(JsonUtil.bean2Json(param));
            if (ProducerStatus.SEND_FAILURE.equals(result.getProducerStatus())) {
                isSuccess.set(false);
            }
            // 上报日志中心
            logger.info("paas:{}, typeName:{}, capacity:{}", param.getPaasName(), param.getTypeName(), JsonUtil.bean2Json(param));

        });
        return isSuccess.get() ? 0 : -1;
    }

    /**
     * 联动性paas上报数据校验
     *
     * @param reportVO reportVO
     */
    private void checkValidPaasReport(AppkeyPaasCapacityReportVO reportVO){
        for (AppkeyPaasCapacityReportVO.ClientSdkVersion clientSdkVersion : reportVO.getClientSdkVersion()) {
            if (StringUtils.isEmpty(clientSdkVersion.getClientAppkey()) || CollectionUtils.isEmpty(clientSdkVersion.getItems())){
                throw new SupportErrorException("请完整上报不达标客户端信息");
            }
        }
        for (AppkeyPaasCapacityReportVO.ClientConfig clientConfig : reportVO.getClientConfig()) {
            if (StringUtils.isEmpty(clientConfig.getKey()) || StringUtils.isEmpty(clientConfig.getValue())) {
                throw new SupportErrorException("请完整上报配置信息");
            }
        }
        for (AppkeyPaasCapacityReportVO.ClientConfig config : reportVO.getStandardConfig()) {
            if (StringUtils.isEmpty(config.getKey()) || StringUtils.isEmpty(config.getValue())) {
                throw new SupportErrorException("请完整上报配置信息");
            }
        }
    }

    @MethodDoc(displayName = "paas容灾信息分页查询接口",
            description = "接口用于查询paas容灾等级信息",
            parameters = {
                    @ParamDoc(name = "request",  description = "查询支持的过滤条件", paramType = ParamType.REQUEST_PARAM)
            },
            requestMethods = {HttpMethod.GET},
            restExampleUrl="http://avatar.vip.sankuai.com/open/api/v2/avatar/capacity/paas?paasName=Mafka",
            responseParams = {
                    @ParamDoc(name = "code", description = "返回码"),
                    @ParamDoc(name = "message", description = "返回描述"),
                    @ParamDoc(name = "data", description = "paas容灾数据", type = PageResponse.class),
            },
            restExampleResponseData = "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"message\": \"操作成功\",\n" +
                    "  \"data\": {\n" +
                    "    \"totalCount\": 1508308,\n" +
                    "    \"page\": 1,\n" +
                    "    \"totalPage\": 150831,\n" +
                    "    \"pageSize\": 10,\n" +
                    "    \"items\": [\n" +
                    "      {\n" +
                    "        \"id\": 1923315,\n" +
                    "        \"paasName\": \"Cellar\",\n" +
                    "        \"type\": \"APPKEY\",\n" +
                    "        \"typeName\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"clientRole\": \"\",\n" +
                    "        \"typeComment\": \"\",\n" +
                    "        \"paasAppkey\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"isCore\": false,\n" +
                    "        \"capacityLevel\": 2,\n" +
                    "        \"standardLevel\": 4,\n" +
                    "        \"isCapacityStandard\": false,\n" +
                    "        \"standardReason\": \"\",\n" +
                    "        \"standardTips\": \"未达到容灾标准\",\n" +
                    "        \"standardVersion\": [],\n" +
                    "        \"clientSdkVersion\": [],\n" +
                    "        \"clientAppkey\": [\n" +
                    "          \"com.sankuai.movie.sc.scserver\"\n" +
                    "        ],\n" +
                    "        \"clientConfig\": [],\n" +
                    "        \"isClientStandard\": true,\n" +
                    "        \"standardConfig\": [],\n" +
                    "        \"isConfigStandard\": true,\n" +
                    "        \"isWhite\": false,\n" +
                    "        \"whiteReason\": \"\",\n" +
                    "        \"isSet\": false,\n" +
                    "        \"setName\": \"\",\n" +
                    "        \"setType\": \"\",\n" +
                    "        \"updateBy\": \"com.sankuai.inf.kv.controlcenter\",\n" +
                    "        \"owner\": \"kangkai05,gengdiannan,liyebing,duanlikao,huwei05,liuhang24,muhongxu,meidanlong,sunyunpeng,sunzhiwei02\",\n" +
                    "        \"updateDate\": \"2022-10-27\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 1923322,\n" +
                    "        \"paasName\": \"Cellar\",\n" +
                    "        \"type\": \"APPKEY\",\n" +
                    "        \"typeName\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"clientRole\": \"producer\",\n" +
                    "        \"typeComment\": \"\",\n" +
                    "        \"paasAppkey\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"isCore\": false,\n" +
                    "        \"capacityLevel\": 2,\n" +
                    "        \"standardLevel\": 4,\n" +
                    "        \"isCapacityStandard\": false,\n" +
                    "        \"standardReason\": \"\",\n" +
                    "        \"standardTips\": \"未达到容灾标准\",\n" +
                    "        \"standardVersion\": [],\n" +
                    "        \"clientSdkVersion\": [],\n" +
                    "        \"clientAppkey\": [\n" +
                    "          \"com.sankuai.inf.hulk.config\"\n" +
                    "        ],\n" +
                    "        \"clientConfig\": [],\n" +
                    "        \"isClientStandard\": true,\n" +
                    "        \"standardConfig\": [],\n" +
                    "        \"isConfigStandard\": true,\n" +
                    "        \"isWhite\": false,\n" +
                    "        \"whiteReason\": \"\",\n" +
                    "        \"isSet\": false,\n" +
                    "        \"setName\": \"\",\n" +
                    "        \"setType\": \"\",\n" +
                    "        \"updateBy\": \"com.sankuai.inf.kv.controlcenter\",\n" +
                    "        \"owner\": \"kangkai05,gengdiannan,liyebing,duanlikao,huwei05,liuhang24,muhongxu,meidanlong,sunyunpeng,sunzhiwei02\",\n" +
                    "        \"updateDate\": \"2022-10-27\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 1923329,\n" +
                    "        \"paasName\": \"Cellar\",\n" +
                    "        \"type\": \"APPKEY\",\n" +
                    "        \"typeName\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"clientRole\": \"consumer\",\n" +
                    "        \"typeComment\": \"\",\n" +
                    "        \"paasAppkey\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"isCore\": false,\n" +
                    "        \"capacityLevel\": 2,\n" +
                    "        \"standardLevel\": 4,\n" +
                    "        \"isCapacityStandard\": false,\n" +
                    "        \"standardReason\": \"\",\n" +
                    "        \"standardTips\": \"未达到容灾标准\",\n" +
                    "        \"standardVersion\": [],\n" +
                    "        \"clientSdkVersion\": [],\n" +
                    "        \"clientAppkey\": [\n" +
                    "          \"com.sankuai.hulk.billing.account\"\n" +
                    "        ],\n" +
                    "        \"clientConfig\": [],\n" +
                    "        \"isClientStandard\": true,\n" +
                    "        \"standardConfig\": [],\n" +
                    "        \"isConfigStandard\": true,\n" +
                    "        \"isWhite\": false,\n" +
                    "        \"whiteReason\": \"\",\n" +
                    "        \"isSet\": false,\n" +
                    "        \"setName\": \"\",\n" +
                    "        \"setType\": \"\",\n" +
                    "        \"updateBy\": \"com.sankuai.inf.kv.controlcenter\",\n" +
                    "        \"owner\": \"kangkai05,gengdiannan,liyebing,duanlikao,huwei05,liuhang24,muhongxu,meidanlong,sunyunpeng,sunzhiwei02\",\n" +
                    "        \"updateDate\": \"2022-10-27\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 1923336,\n" +
                    "        \"paasName\": \"Cellar\",\n" +
                    "        \"type\": \"APPKEY\",\n" +
                    "        \"typeName\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"clientRole\": \"consumer\",\n" +
                    "        \"typeComment\": \"\",\n" +
                    "        \"paasAppkey\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"isCore\": false,\n" +
                    "        \"capacityLevel\": 2,\n" +
                    "        \"standardLevel\": 4,\n" +
                    "        \"isCapacityStandard\": false,\n" +
                    "        \"standardReason\": \"\",\n" +
                    "        \"standardTips\": \"未达到容灾标准\",\n" +
                    "        \"standardVersion\": [],\n" +
                    "        \"clientSdkVersion\": [],\n" +
                    "        \"clientAppkey\": [\n" +
                    "          \"com.sankuai.movie.promotion.partnervoucher\"\n" +
                    "        ],\n" +
                    "        \"clientConfig\": [],\n" +
                    "        \"isClientStandard\": true,\n" +
                    "        \"standardConfig\": [],\n" +
                    "        \"isConfigStandard\": true,\n" +
                    "        \"isWhite\": false,\n" +
                    "        \"whiteReason\": \"\",\n" +
                    "        \"isSet\": false,\n" +
                    "        \"setName\": \"\",\n" +
                    "        \"setType\": \"\",\n" +
                    "        \"updateBy\": \"com.sankuai.inf.kv.controlcenter\",\n" +
                    "        \"owner\": \"kangkai05,gengdiannan,liyebing,duanlikao,huwei05,liuhang24,muhongxu,meidanlong,sunyunpeng,sunzhiwei02\",\n" +
                    "        \"updateDate\": \"2022-10-27\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 1923343,\n" +
                    "        \"paasName\": \"Cellar\",\n" +
                    "        \"type\": \"APPKEY\",\n" +
                    "        \"typeName\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"clientRole\": \"consumer\",\n" +
                    "        \"typeComment\": \"\",\n" +
                    "        \"paasAppkey\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"isCore\": false,\n" +
                    "        \"capacityLevel\": 2,\n" +
                    "        \"standardLevel\": 4,\n" +
                    "        \"isCapacityStandard\": false,\n" +
                    "        \"standardReason\": \"\",\n" +
                    "        \"standardTips\": \"未达到容灾标准\",\n" +
                    "        \"standardVersion\": [],\n" +
                    "        \"clientSdkVersion\": [],\n" +
                    "        \"clientAppkey\": [\n" +
                    "          \"com.sankuai.hulk.heimdal.manager\"\n" +
                    "        ],\n" +
                    "        \"clientConfig\": [],\n" +
                    "        \"isClientStandard\": true,\n" +
                    "        \"standardConfig\": [],\n" +
                    "        \"isConfigStandard\": true,\n" +
                    "        \"isWhite\": false,\n" +
                    "        \"whiteReason\": \"\",\n" +
                    "        \"isSet\": false,\n" +
                    "        \"setName\": \"\",\n" +
                    "        \"setType\": \"\",\n" +
                    "        \"updateBy\": \"com.sankuai.inf.kv.controlcenter\",\n" +
                    "        \"owner\": \"kangkai05,gengdiannan,liyebing,duanlikao,huwei05,liuhang24,muhongxu,meidanlong,sunyunpeng,sunzhiwei02\",\n" +
                    "        \"updateDate\": \"2022-10-27\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 1923350,\n" +
                    "        \"paasName\": \"Cellar\",\n" +
                    "        \"type\": \"APPKEY\",\n" +
                    "        \"typeName\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"clientRole\": \"consumer\",\n" +
                    "        \"typeComment\": \"\",\n" +
                    "        \"paasAppkey\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"isCore\": false,\n" +
                    "        \"capacityLevel\": 2,\n" +
                    "        \"standardLevel\": 4,\n" +
                    "        \"isCapacityStandard\": false,\n" +
                    "        \"standardReason\": \"\",\n" +
                    "        \"standardTips\": \"未达到容灾标准\",\n" +
                    "        \"standardVersion\": [],\n" +
                    "        \"clientSdkVersion\": [],\n" +
                    "        \"clientAppkey\": [\n" +
                    "          \"com.meituan.movie.marketing\"\n" +
                    "        ],\n" +
                    "        \"clientConfig\": [],\n" +
                    "        \"isClientStandard\": true,\n" +
                    "        \"standardConfig\": [],\n" +
                    "        \"isConfigStandard\": true,\n" +
                    "        \"isWhite\": false,\n" +
                    "        \"whiteReason\": \"\",\n" +
                    "        \"isSet\": false,\n" +
                    "        \"setName\": \"\",\n" +
                    "        \"setType\": \"\",\n" +
                    "        \"updateBy\": \"com.sankuai.inf.kv.controlcenter\",\n" +
                    "        \"owner\": \"kangkai05,gengdiannan,liyebing,duanlikao,huwei05,liuhang24,muhongxu,meidanlong,sunyunpeng,sunzhiwei02\",\n" +
                    "        \"updateDate\": \"2022-10-27\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 1923357,\n" +
                    "        \"paasName\": \"Cellar\",\n" +
                    "        \"type\": \"APPKEY\",\n" +
                    "        \"typeName\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"clientRole\": \"consumer\",\n" +
                    "        \"typeComment\": \"\",\n" +
                    "        \"paasAppkey\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"isCore\": false,\n" +
                    "        \"capacityLevel\": 2,\n" +
                    "        \"standardLevel\": 4,\n" +
                    "        \"isCapacityStandard\": false,\n" +
                    "        \"standardReason\": \"\",\n" +
                    "        \"standardTips\": \"未达到容灾标准\",\n" +
                    "        \"standardVersion\": [],\n" +
                    "        \"clientSdkVersion\": [],\n" +
                    "        \"clientAppkey\": [\n" +
                    "          \"com.sankuai.banner.metrics.clustera\"\n" +
                    "        ],\n" +
                    "        \"clientConfig\": [],\n" +
                    "        \"isClientStandard\": true,\n" +
                    "        \"standardConfig\": [],\n" +
                    "        \"isConfigStandard\": true,\n" +
                    "        \"isWhite\": false,\n" +
                    "        \"whiteReason\": \"\",\n" +
                    "        \"isSet\": false,\n" +
                    "        \"setName\": \"\",\n" +
                    "        \"setType\": \"\",\n" +
                    "        \"updateBy\": \"com.sankuai.inf.kv.controlcenter\",\n" +
                    "        \"owner\": \"kangkai05,gengdiannan,liyebing,duanlikao,huwei05,liuhang24,muhongxu,meidanlong,sunyunpeng,sunzhiwei02\",\n" +
                    "        \"updateDate\": \"2022-10-27\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 1923364,\n" +
                    "        \"paasName\": \"Cellar\",\n" +
                    "        \"type\": \"APPKEY\",\n" +
                    "        \"typeName\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"clientRole\": \"consumer\",\n" +
                    "        \"typeComment\": \"\",\n" +
                    "        \"paasAppkey\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"isCore\": false,\n" +
                    "        \"capacityLevel\": 2,\n" +
                    "        \"standardLevel\": 4,\n" +
                    "        \"isCapacityStandard\": false,\n" +
                    "        \"standardReason\": \"\",\n" +
                    "        \"standardTips\": \"未达到容灾标准\",\n" +
                    "        \"standardVersion\": [],\n" +
                    "        \"clientSdkVersion\": [],\n" +
                    "        \"clientAppkey\": [\n" +
                    "          \"com.meituan.movie.pay\"\n" +
                    "        ],\n" +
                    "        \"clientConfig\": [],\n" +
                    "        \"isClientStandard\": true,\n" +
                    "        \"standardConfig\": [],\n" +
                    "        \"isConfigStandard\": true,\n" +
                    "        \"isWhite\": false,\n" +
                    "        \"whiteReason\": \"\",\n" +
                    "        \"isSet\": false,\n" +
                    "        \"setName\": \"\",\n" +
                    "        \"setType\": \"\",\n" +
                    "        \"updateBy\": \"com.sankuai.inf.kv.controlcenter\",\n" +
                    "        \"owner\": \"kangkai05,gengdiannan,liyebing,duanlikao,huwei05,liuhang24,muhongxu,meidanlong,sunyunpeng,sunzhiwei02\",\n" +
                    "        \"updateDate\": \"2022-10-27\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 1923371,\n" +
                    "        \"paasName\": \"Cellar\",\n" +
                    "        \"type\": \"APPKEY\",\n" +
                    "        \"typeName\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"clientRole\": \"consumer\",\n" +
                    "        \"typeComment\": \"\",\n" +
                    "        \"paasAppkey\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"isCore\": false,\n" +
                    "        \"capacityLevel\": 2,\n" +
                    "        \"standardLevel\": 4,\n" +
                    "        \"isCapacityStandard\": false,\n" +
                    "        \"standardReason\": \"\",\n" +
                    "        \"standardTips\": \"未达到容灾标准\",\n" +
                    "        \"standardVersion\": [],\n" +
                    "        \"clientSdkVersion\": [],\n" +
                    "        \"clientAppkey\": [\n" +
                    "          \"com.sankuai.hulk.banner.dams\"\n" +
                    "        ],\n" +
                    "        \"clientConfig\": [],\n" +
                    "        \"isClientStandard\": true,\n" +
                    "        \"standardConfig\": [],\n" +
                    "        \"isConfigStandard\": true,\n" +
                    "        \"isWhite\": false,\n" +
                    "        \"whiteReason\": \"\",\n" +
                    "        \"isSet\": false,\n" +
                    "        \"setName\": \"\",\n" +
                    "        \"setType\": \"\",\n" +
                    "        \"updateBy\": \"com.sankuai.inf.kv.controlcenter\",\n" +
                    "        \"owner\": \"kangkai05,gengdiannan,liyebing,duanlikao,huwei05,liuhang24,muhongxu,meidanlong,sunyunpeng,sunzhiwei02\",\n" +
                    "        \"updateDate\": \"2022-10-27\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 1923378,\n" +
                    "        \"paasName\": \"Cellar\",\n" +
                    "        \"type\": \"APPKEY\",\n" +
                    "        \"typeName\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"clientRole\": \"consumer\",\n" +
                    "        \"typeComment\": \"\",\n" +
                    "        \"paasAppkey\": \"com.sankuai.tair.inf.maoyantest\",\n" +
                    "        \"isCore\": false,\n" +
                    "        \"capacityLevel\": 2,\n" +
                    "        \"standardLevel\": 4,\n" +
                    "        \"isCapacityStandard\": false,\n" +
                    "        \"standardReason\": \"\",\n" +
                    "        \"standardTips\": \"未达到容灾标准\",\n" +
                    "        \"standardVersion\": [],\n" +
                    "        \"clientSdkVersion\": [],\n" +
                    "        \"clientAppkey\": [\n" +
                    "          \"com.sankuai.hulk.banner.clustera\"\n" +
                    "        ],\n" +
                    "        \"clientConfig\": [],\n" +
                    "        \"isClientStandard\": true,\n" +
                    "        \"standardConfig\": [],\n" +
                    "        \"isConfigStandard\": true,\n" +
                    "        \"isWhite\": false,\n" +
                    "        \"whiteReason\": \"\",\n" +
                    "        \"isSet\": false,\n" +
                    "        \"setName\": \"\",\n" +
                    "        \"setType\": \"\",\n" +
                    "        \"updateBy\": \"com.sankuai.inf.kv.controlcenter\",\n" +
                    "        \"owner\": \"kangkai05,gengdiannan,liyebing,duanlikao,huwei05,liuhang24,muhongxu,meidanlong,sunyunpeng,sunzhiwei02\",\n" +
                    "        \"updateDate\": \"2022-10-27\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}"
    )
    @GetMapping("")
    public PageResponse<AppkeyPaasCapacityReportVO> getPaasCapacity(@Valid AppkeyPaasCapacityPageRequest request){

        PageResponse<AppkeyPaasCapacityReportVO> pageResponse = new PageResponse<>();
        PageResponse<AppkeyPaasCapacityDTO> dtoPageResponse = appkeyPaasCapacityService.queryPage(request);
        pageResponse.setPage(request.getPage());
        pageResponse.setPageSize(request.getPageSize());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        List<AppkeyPaasCapacityReportVO> reportVOList = AppkeyPaasCapacityDTOTransfer.INSTANCE.toReportVOList(dtoPageResponse.getItems());
        for (AppkeyPaasCapacityReportVO reportVO : reportVOList) {
            reportVO.setDefaultValue();
            reportVO.setIsClientStandard(true);
            for (String appkey : reportVO.getClientAppkey()) {
                if (StringUtils.isEmpty(appkey)) { continue; }
                List<AppkeyPaasClientDTO> appkeyPaasClientDTOList = appkeyPaasCapacityService.queryPaasClientByAppkey(reportVO.getPaasName(), appkey, request.getUpdateDate());
                if (CollectionUtils.isNotEmpty(appkeyPaasClientDTOList)) {
                    reportVO.setIsClientStandard(false);
                    reportVO.setClientSdkVersionByClientDTO(appkeyPaasClientDTOList.get(0));
                }
            }
        }
        pageResponse.setItems(reportVOList);
        return pageResponse;
    }
}
