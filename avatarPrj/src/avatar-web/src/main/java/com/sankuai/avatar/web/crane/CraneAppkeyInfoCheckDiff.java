package com.sankuai.avatar.web.crane;

import com.cip.crane.client.spring.annotation.Crane;
import com.cip.crane.client.spring.annotation.CraneConfiguration;
import com.github.dadiyang.equator.Equator;
import com.github.dadiyang.equator.FieldInfo;
import com.github.dadiyang.equator.GetterBaseEquator;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEvent;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventData;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.collect.appkey.scheduer.AppkeyCollectEventScheduler;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.resource.dxMessage.DxMessageResource;
import com.sankuai.avatar.web.dto.appkey.AppkeyDTO;
import com.sankuai.avatar.web.service.AppkeyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 定时任务:检验Appkey信息采集的准确性
 *
 * @author qinwei05
 * @date 2022/12/28
 */
@CraneConfiguration
@Component
@Slf4j
public class CraneAppkeyInfoCheckDiff {

    private final AppkeyCollectEventScheduler appkeyCollectEventScheduler;

    private final AppkeyService appkeyService;

    private final DxMessageResource dxMessageResource;

    @Autowired
    public CraneAppkeyInfoCheckDiff(AppkeyCollectEventScheduler appkeyCollectEventScheduler,
                                    AppkeyService appkeyService,
                                    DxMessageResource dxMessageResource) {
        this.appkeyCollectEventScheduler = appkeyCollectEventScheduler;
        this.appkeyService = appkeyService;
        this.dxMessageResource = dxMessageResource;
    }

    @Crane("com.sankuai.avatar.web.appkeyInfoCheckDiff")
    public void appkeyInfoCheckDiff() {
        List<AppkeyDTO> appkeyDTOList = appkeyService.getByAppkeyRandom(20);
        List<String> diffInfos = new ArrayList<>();

        for (AppkeyDTO appkeyDTO : appkeyDTOList){
            List<String> diffInfo = new ArrayList<>();
            diffInfo.add(appkeyDTO.getAppkey());
            AppkeyCollectEvent appkeyCollectEvent = AppkeyCollectEvent.builder()
                    .collectEventData(AppkeyCollectEventData.of(appkeyDTO.getAppkey()))
                    .build();
            if (Objects.equals(appkeyDTO.getType(), "BACKEND")){
                appkeyCollectEvent.setCollectEventName(CollectEventName.APPKEY_REFRESH);
            } else {
                appkeyCollectEvent.setCollectEventName(CollectEventName.SC_NOT_BACKEND_APPKEY_UPDATE);
            }
            Appkey appkeyModel = appkeyCollectEventScheduler.collect(appkeyCollectEvent);
            // 标记下线的服务比对
            if (!Objects.equals(appkeyModel.getIsOffline(), appkeyDTO.getIsOffline())){
                diffInfo.add(String.format("服务Offline状态不一致[%s]: 采集值为 %s, 存储值为 %s", appkeyModel.getAppkey(),
                        appkeyModel.getIsOffline(), appkeyDTO.getIsOffline()));
                continue;
            }
            String diffInfoString = getDiffInfo(appkeyModel, appkeyDTO);
            if (StringUtils.isNotBlank(diffInfoString)){
                diffInfo.add(diffInfoString);
            }
            if (CollectionUtils.isNotEmpty(diffInfo)){
                diffInfos.add(String.format("服务名: %s, 比对信息 %s", appkeyDTO.getAppkey(), String.join(";", diffInfo)));
            }
        }
        if (!diffInfos.isEmpty()){
            List<String> misList = Collections.singletonList("qinwei05");
            sendDxMessage(misList, String.join(";", diffInfos), diffInfos.size());
        }
    }

    private String getDiffInfo(Appkey appkeyModel, AppkeyDTO appkeyDTO){
        Equator equator = new GetterBaseEquator();
        StringBuilder str = new StringBuilder();
        List<FieldInfo> diff = equator.getDiffFields(appkeyModel, appkeyDTO);
        if (diff.isEmpty()){
            return "";
        }
        for (FieldInfo fieldInfo: diff){
            if ((fieldInfo.getFirstVal() instanceof Boolean || fieldInfo.getSecondVal() instanceof Boolean)
                    && !Objects.equals(fieldInfo.getFirstVal(), fieldInfo.getSecondVal())){
                str.append(String.format("字段名[%s]: 采集值为 %s, 存储值为 %s", fieldInfo.getFieldName(),
                        fieldInfo.getFirstVal(), fieldInfo.getSecondVal()));
                continue;
            }
            if ((isObjectEmpty(fieldInfo.getFirstVal()) && isObjectEmpty(fieldInfo.getSecondVal()))
                    || Arrays.asList("updateTime", "offlineTime").contains(fieldInfo.getFieldName())){
                continue;
            }
            str.append(String.format("字段名[%s]: 采集值为 %s, 存储值为 %s", fieldInfo.getFieldName(),
                    fieldInfo.getFirstVal(), fieldInfo.getSecondVal()));
        }
        return str.toString();
    }

    private Boolean isObjectEmpty(Object obj) {
        String str = obj == null ? "" : obj.toString();
        return StringUtils.isBlank(str);
    }

    /**
     * 发送大象消息
     *  @param mis      用户 mis
     * @param diffText 文案
     * @param size
     */
    private void sendDxMessage(List<String> mis, String diffText, int size) {
        String text = "【服务数据校对不一致通知】\n"
                + "【比对结果】%s \n"
                + "【总览】不一致占比 %s/20 ";
        String sendText = String.format(text, diffText, size);
        dxMessageResource.pushDxMessage(mis, sendText);
    }
}
