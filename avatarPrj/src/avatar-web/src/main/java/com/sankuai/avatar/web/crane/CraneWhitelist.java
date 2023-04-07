package com.sankuai.avatar.web.crane;

import com.cip.crane.client.spring.annotation.Crane;
import com.cip.crane.client.spring.annotation.CraneConfiguration;
import com.dianping.cat.Cat;
import com.sankuai.avatar.capacity.util.RocketUtil;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.dxMessage.DxMessageResource;
import com.sankuai.avatar.resource.whitelist.OwtSetWhitelistResource;
import com.sankuai.avatar.resource.whitelist.ServiceWhitelistResource;
import com.sankuai.avatar.resource.whitelist.bo.OwtSetWhitelistBO;
import com.sankuai.avatar.resource.whitelist.bo.ServiceWhitelistBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.resource.whitelist.request.OwtSetWhitelistRequestBO;
import com.sankuai.avatar.resource.whitelist.request.ServiceWhitelistRequestBO;
import com.sankuai.avatar.sdk.entity.servicecatalog.AppKey;
import com.sankuai.avatar.sdk.manager.ServiceCatalogAppKey;
import com.sankuai.rocket.host.instance.openapi.model.PageResultHostDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenxinli
 */
@CraneConfiguration
@Component
@Slf4j
public class CraneWhitelist {

    private final ServiceCatalogAppKey serviceCatalogAppKey;

    private final DxMessageResource dxMessageResource;

    private final ServiceWhitelistResource serviceWhitelistResource;

    private final OwtSetWhitelistResource owtSetWhitelistResource;

    @Autowired
    public CraneWhitelist(ServiceCatalogAppKey serviceCatalogAppKey,
                          DxMessageResource dxMessageResource,
                          ServiceWhitelistResource serviceWhitelistResource,
                          OwtSetWhitelistResource owtSetWhitelistResource) {
        this.serviceCatalogAppKey = serviceCatalogAppKey;
        this.dxMessageResource = dxMessageResource;
        this.serviceWhitelistResource = serviceWhitelistResource;
        this.owtSetWhitelistResource = owtSetWhitelistResource;
    }

    public static final int BATCH_SIZE = 200;

    @Crane("com.sankuai.avatar.web.clearExpireWhitelist")
    public void clearExpireWhitelist() {
        List<ServiceWhitelistBO> expirelist1 = serviceWhitelistResource.getExpireWhitelist();
        log.info("prepare to delete expire whitelist, total: {}", expirelist1.size());
        for (ServiceWhitelistBO bo : expirelist1) {
            serviceWhitelistResource.deleteByCondition(ServiceWhitelistRequestBO.builder().id(bo.getId()).build());
            log.info("delete whitelist appkey: {}, whiteType: {}", bo.getAppkey(), bo.getApp().getCname());
        }
        List<OwtSetWhitelistBO> expirelist2 = owtSetWhitelistResource.getExpireWhitelist();
        log.info("prepare to delete expire owt whitelist, total: {}", expirelist2.size());
        for (OwtSetWhitelistBO bo : expirelist2) {
            owtSetWhitelistResource.deleteByCondition(OwtSetWhitelistRequestBO.builder().id(bo.getId()).build());
            log.info("clear expire owt whitelist, owt: {}, set: {}", bo.getOwt(), bo.getSetName());
        }
    }

    private List<ServiceWhitelistBO> getAllWhitelist(){
        List<ServiceWhitelistBO> list = new ArrayList<>();
        int page = 1;
        ServiceWhitelistRequestBO requestBO = ServiceWhitelistRequestBO.builder().build();
        requestBO.setPage(page++);
        requestBO.setPageSize(BATCH_SIZE);
        PageResponse<ServiceWhitelistBO> pageResponse = serviceWhitelistResource.queryPage(requestBO);
        while (Objects.nonNull(pageResponse) && CollectionUtils.isNotEmpty(pageResponse.getItems())){
            list.addAll(pageResponse.getItems());
            requestBO.setPage(page++);
            pageResponse = serviceWhitelistResource.queryPage(requestBO);
        }
        return list;
    }

    @Crane("com.sankuai.avatar.web.updateWhitelistOrgAndApplication")
    public void updateWhitelistOrgAndApplication() {
        List<ServiceWhitelistBO> boList = getAllWhitelist();
        List<String> invalid = new ArrayList<>();
        for (ServiceWhitelistBO bo : boList) {
            String appkey = bo.getAppkey();
            if (StringUtils.isEmpty(appkey)) {
                serviceWhitelistResource.deleteByCondition(ServiceWhitelistRequestBO.builder().id(bo.getId()).build());
                continue;
            }
            if (invalid.contains(appkey)) {continue;}
            try {
                AppKey appKey = serviceCatalogAppKey.getAppKey(appkey);
                bo.setApplication(appKey.getApplicationName().toLowerCase());
                bo.setOrgIds(appKey.getOrgIds());
                serviceWhitelistResource.save(bo);
            } catch (Exception e) {
                if (Objects.equals("服务不存在", e.getMessage())){
                    invalid.add(appkey);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(invalid)) {
            serviceWhitelistResource.deleteByCondition(ServiceWhitelistRequestBO.builder().appkeys(invalid).build());
            log.info("appkeys: {} 不存在，删除关联的白名单", invalid);
        }

    }

    @Crane("com.sankuai.avatar.web.sendExpireWhitelistNotice")
    public void sendExpireWhitelistNotice() {

        List<ServiceWhitelistBO> boList = getAllWhitelist();
        int count = 0;
        for (ServiceWhitelistBO bo : boList) {
            String appkey = bo.getAppkey();
            // Staging解除单机限制与服务高峰期白名单，不通知
            if (WhiteType.ALTERATION.equals(bo.getApp()) || WhiteType.ST_SINGLE_RELIEVE.equals(bo.getApp())) {
                continue;
            }
            // 空服务不发送通知
            PageResultHostDTO hostDTO = RocketUtil.httpGetHostDataByAppKey(appkey);
            if (Objects.isNull(hostDTO) || CollectionUtils.isEmpty(hostDTO.getData())) {
                continue;
            }

            Date endTime = bo.getEndTime();
            if (Objects.isNull(endTime) || endTime.getTime() <= System.currentTimeMillis()) {
                continue;
            }
            boolean expireIn3Days = (endTime.getTime() - System.currentTimeMillis()) <= 1000 * 3600 * 24 * 3;
            Date addTime = bo.getAddTime();
            try {
                final String comma = ",";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                if (expireIn3Days) {
                    count++;
                    AppKey appKey = serviceCatalogAppKey.getAppKey(appkey);
                    String timeRange = sdf.format(addTime) + " ~ " + sdf.format(endTime);
                    List<String> misList = Arrays.asList(appKey.getRdAdmin().split(comma));
                    pushTextMessage(misList, appkey, appkey + ": " + bo.getApp().getCname(), appKey.getOrgIds(), timeRange);
                    log.info("{} 服务负责人:{} 白名单临近3天过期消息发送成功", appKey, misList);
                }
            } catch (Exception e){
                log.info("{}，白名单临近过期处理失败", appkey);
                Cat.logError(e);
            }
        }
        log.info("本次总共有{}个服务白名单临近3天过期", count);
    }

    public void pushTextMessage(List<String> misList, String appkey, String whiteList, String orgId, String timeRange) {
    String text =
        "【Avatar服务白名单临近到期通知】\n"
            + "【内容】请注意：您所负责的 %s 白名单已不足三天，若想延长白名单时间，请前往Avatar处理。\n"
            + "【时间】%s \n"
            + "【部门链接】[前往查看|https://avatar.mws.sankuai.com/#/owt/operation?ids=%s]\n"
            + "【服务详情】[前往查看|https://avatar.mws.sankuai.com/#/service/detail/info?appkey=%s&env=prod]\n"
            + "【操作说明】[白名单说明|https://km.sankuai.com/page/1274227259]";
        String sendText = String.format(text, whiteList, timeRange, orgId.replace(",", "%2C"), appkey);
        try {
            dxMessageResource.pushDxMessage(misList, sendText);
        } catch (SdkBusinessErrorException ignored) {
        }
    }
}
