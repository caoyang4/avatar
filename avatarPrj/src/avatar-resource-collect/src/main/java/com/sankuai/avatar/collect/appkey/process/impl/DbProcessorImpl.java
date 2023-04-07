package com.sankuai.avatar.collect.appkey.process.impl;

import com.github.dadiyang.equator.Equator;
import com.github.dadiyang.equator.FieldInfo;
import com.github.dadiyang.equator.GetterBaseEquator;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.collect.appkey.process.Processor;
import com.sankuai.avatar.collect.appkey.process.request.AppkeyDbQueryRequest;
import com.sankuai.avatar.collect.appkey.process.transfer.AppkeyTransfer;
import com.sankuai.avatar.dao.resource.repository.AppkeyRepository;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author qinwei05
 * @date 2022/11/3 17:28
 */
@Service
@Slf4j
public class DbProcessorImpl implements Processor {

    private final AppkeyRepository appkeyRepository;

    @Autowired
    public DbProcessorImpl(AppkeyRepository appkeyRepository){
        this.appkeyRepository = appkeyRepository;
    }

    @Override
    public Boolean batchProcess(List<Appkey> appkeys) {
        log.info("batch write appkeys to db size: {}", appkeys.size());
        appkeys.forEach(this::process);
        List<AppkeyDO> appkeyDOS = AppkeyTransfer.INSTANCE.batchToDO(appkeys);
        return appkeyRepository.insertBatch(appkeyDOS) > 0;
    }

    @Override
    public Boolean process(Appkey appkey) {
        AppkeyDO appkeyDO = AppkeyTransfer.INSTANCE.toDO(appkey);
        AppkeyDbQueryRequest appkeyDbQueryRequest = new AppkeyDbQueryRequest(appkey.getAppkey());
        List<AppkeyDO> appkeyDOS = appkeyRepository.query(AppkeyTransfer.INSTANCE.toDbRequest(appkeyDbQueryRequest));
        // 数据源未发生变化时,不进行存储更新
        // if (Boolean.FALSE.equals(checkShouldUpdate(appkey, appkeyDOS))){
        //     return false;
        // }
        if (CollectionUtils.isEmpty(appkeyDOS)){
            // 不会新增已经下线的Appkey
            if (Boolean.TRUE.equals(appkey.getIsOffline())) {
                return false;
            }
            appkeyRepository.insert(appkeyDO);
        } else {
            AppkeyDO existAppkeyDO = appkeyDOS.get(0);
            appkeyDO.setId(existAppkeyDO.getId());
            // 标记服务下线时,若已经存在下线时间,则放弃更新
            if (Boolean.TRUE.equals(appkeyDO.getIsOffline()) && existAppkeyDO.getOfflineTime() != null) {
                appkeyDO.setOfflineTime(null);
            }
            appkeyRepository.update(appkeyDO);
        }
        log.info("write appkey: [{}] to db success", appkey.getAppkey());
        return true;
    }

    /**
     * 前置拦截检查：是否需要更新数据源
     * 若数据源并未发生变化，则忽略更新存储端
     *
     * @param appkeyResourceData 采集事件结果
     * @return {@link Boolean}
     */
    public Boolean checkShouldUpdate(Appkey appkeyResourceData, List<AppkeyDO> appkeyDoList){
        if (CollectionUtils.isEmpty(appkeyDoList)){
            return Boolean.TRUE;
        }
        Equator equator = new GetterBaseEquator();
        AppkeyDO existAppkeyDO = appkeyDoList.get(0);
        // 默认只比对两个类字段的交集(即两个类都有的字段才比对) 避免DB/ES重复写入
        List<FieldInfo> diffFieldInfos = equator.getDiffFields(appkeyResourceData, existAppkeyDO);
        if (diffFieldInfos.isEmpty()){
            return Boolean.FALSE;
        }
        List<FieldInfo> realDiffFieldInfos = new ArrayList<>();
        for (FieldInfo fieldInfo: diffFieldInfos){
            if (Boolean.TRUE.equals(checkDiff(fieldInfo.getFirstVal(), fieldInfo.getSecondVal()))){
                realDiffFieldInfos.add(fieldInfo);
            }
        }
        return realDiffFieldInfos.isEmpty() ? Boolean.FALSE : Boolean.TRUE;
    }

    private Boolean isObjectEmpty(Object obj) {
        String str = obj == null ? "" : obj.toString();
        return StringUtils.isBlank(str);
    }

    private Boolean checkDiff(Object source, Object target) {
        if ((source instanceof Boolean || target instanceof Boolean)
                && !Objects.equals(source, target)){
            return Boolean.TRUE;
        }
        if (isObjectEmpty(source) && isObjectEmpty(target)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
