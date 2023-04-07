package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.resource.plus.PlusResource;
import com.sankuai.avatar.resource.plus.bo.PlusReleaseBO;
import com.sankuai.avatar.web.dto.PlusReleaseDTO;
import com.sankuai.avatar.web.service.PlusService;
import com.sankuai.avatar.web.transfer.PlusResourceTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2022/10/19 15:30
 * @version 1.0
 */
@Slf4j
@Service
public class PlusServiceImpl implements PlusService {

    @Autowired
    private PlusResource plusResource;

    @Override
    public List<PlusReleaseDTO> getBindPlusByAppkey(String appkey) {
        List<PlusReleaseBO> plusReleaseList = plusResource.getBindPlusByAppkey(appkey);
        return PlusResourceTransfer.INSTANCE.toDTOList(plusReleaseList);
    }

    @Override
    public List<PlusReleaseDTO> getAppliedPlusByAppkey(String appkey) {
        List<PlusReleaseBO> plusReleaseList = plusResource.getAppliedPlusByAppkey(appkey);
        return PlusResourceTransfer.INSTANCE.toDTOList(plusReleaseList);
    }
}
