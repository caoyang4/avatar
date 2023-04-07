package com.sankuai.avatar.collect.appkey.event;

import com.sankuai.avatar.collect.appkey.collector.source.AppkeySource;
import com.sankuai.avatar.collect.appkey.collector.source.Source;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2022-11-18
 **/
@Data
public class AppkeyCollectEventResult implements CollectEventResult {

    private List<AppkeySource> appkeySourceList;

    private AppkeyCollectEventResult(List<AppkeySource> appkeySourceList) {
        this.appkeySourceList = appkeySourceList;
    }

    public static AppkeyCollectEventResult ofSource(AppkeySource appkeySource) {
        List<AppkeySource> appkeySourceList = Collections.singletonList(appkeySource);
        return new AppkeyCollectEventResult(appkeySourceList);
    }

    @Override
    public List<? extends Source> getSourceList() {
        return this.appkeySourceList;
    }
}
