package com.sankuai.avatar.client.dx.impl;

import com.dianping.cat.Cat;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.meituan.mdp.boot.starter.thrift.annotation.MdpThriftClient;
import com.sankuai.avatar.client.dx.DxClient;
import com.sankuai.avatar.client.dx.model.DxUser;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.xm.pubapi.thrift.PushMessageServiceI;
import com.sankuai.xm.pubapi.thrift.PusherInfo;
import com.sankuai.xm.udb.thrift.AppTenantEnum;
import com.sankuai.xm.udb.thrift.UdbOpenThriftBeans;
import com.sankuai.xm.udb.thrift.UdbOpenThriftClient;
import com.sankuai.xm.uinfo.thrift.model.UinfoEntity;
import com.sankuai.xm.uinfo.thrift.service.UinfoOpenThriftBeans;
import com.sankuai.xm.uinfo.thrift.service.UinfoOpenThriftClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * DxClient 实现类
 * @author caoyang
 * @create 2022-10-25 16:10
 */
@Component
@Slf4j
public class DxClientImpl implements DxClient {

    @MdpConfig("TENANT:sankuai.info")
    private static String tenant;

    @MdpConfig("DX_AVATAR_KEY:0p920521371416p9")
    private static String APP_KEY;

    @MdpConfig("DX_AVATAR_PUBID:137438988150")
    private static Long PUB_ID;

    @MdpConfig("DX_AVATAR_TOKEN:80c6a724228d36aa0f744098d467dcf2")
    private static String APP_TOKEN;

    // 审核应用号
    private static final String AUDIT_CLIENT_ID = "1w21381155907s00";
    private static final Long AUDIT_PUB_ID = 137444301369L;
    private static final String AUDIT_APP_TOKEN = "9a1b9ac78bf7868b9547106f6a9e9cd5";

    @MdpThriftClient(remoteAppKey = "com.sankuai.xm.pubapi", remoteServerPort = 8820, timeout = 1000)
    private PushMessageServiceI.Iface pushMessageServiceI;

    private final UdbOpenThriftClient udbOpenThriftClient = UdbOpenThriftBeans.get(UdbOpenThriftClient.class);
    private final UinfoOpenThriftClientService uinfoOpenThriftClientService = UinfoOpenThriftBeans.get(UinfoOpenThriftClientService.class);


    @Override
    public DxUser getDxUserByMis(String mis) throws SdkBusinessErrorException {
        DxUser dxUser = null;
        try {
            dxUser = getDxUserByTenant(mis, tenant);
        } catch (SdkBusinessErrorException ignore) {
        }
        if (Objects.isNull(dxUser) || StringUtils.isEmpty(Objects.requireNonNull(dxUser).getName())) {
            dxUser = getDxUserByTenant(mis, AppTenantEnum.APPID_1_MAOYAN.getTenant());
        }
        return dxUser;
    }

    @Override
    public Boolean pushDxMessage(List<String> receivers, String message) throws SdkBusinessErrorException{
        // 配置信息
        PusherInfo pushInfo = new PusherInfo();
        pushInfo.setAppkey(APP_KEY);
        pushInfo.setToken(APP_TOKEN);
        pushInfo.setFromUid(PUB_ID);
        try {
            String response = pushMessageServiceI.pushTextMessage(System.currentTimeMillis(), message, receivers, pushInfo);
            String code = JsonUtil.readValUsingJsonPath(response, "$.rescode");
            return "0".equals(code);
        } catch (TException e) {
            Cat.logError(e);
            throw new SdkBusinessErrorException("Avatar公众号发送消息至异常：" + e.getMessage());
        }
    }

    @Override
    public Boolean pushDxAuditMessage(List<String> receivers, String message) throws SdkBusinessErrorException {
        // 配置信息
        PusherInfo pushInfo = new PusherInfo();
        pushInfo.setFromUid(AUDIT_PUB_ID);
        pushInfo.setAppkey(AUDIT_CLIENT_ID);
        pushInfo.setToken(AUDIT_APP_TOKEN);
        try {
            String response = pushMessageServiceI.pushTextMessage(System.currentTimeMillis(),
                    message, receivers, pushInfo);
            String code = JsonUtil.readValUsingJsonPath(response, "$.rescode");
            return "0".equals(code);
        } catch (TException e) {
            Cat.logError(e);
            throw new SdkBusinessErrorException("Avatar审核公众号发送消息异常：" + e.getMessage());
        }
    }


    /**
     * 根据 mis 和 租户id 获取 dx 信息
     *
     * @param mis    mis
     * @param tenant 租户
     * @return DxUser
     */
    private DxUser getDxUserByTenant(String mis, String tenant) throws SdkBusinessErrorException {
        try {
            DxUser dxUser = new DxUser();
            dxUser.setMis(mis);
            Long uid = udbOpenThriftClient.getUid(mis, tenant);
            if (Objects.isNull(uid)) {
                return dxUser;
            }
            Set<Long> uidSet = new HashSet<>();
            uidSet.add(uid);
            Map<Long, UinfoEntity> uidUinfoEntityMap = uinfoOpenThriftClientService.getUinfoEntities(uidSet);
            if (MapUtils.isEmpty(uidUinfoEntityMap)) {
                return dxUser;
            }
            dxUser.setAvatarUrl(uidUinfoEntityMap.get(uid).getAvatar_url());
            dxUser.setName(uidUinfoEntityMap.get(uid).getName());
            return dxUser;
        } catch (Exception e) {
            Cat.logError(e);
            throw new SdkBusinessErrorException("获取用户dx信息异常："+e.getMessage());
        }
    }
}
