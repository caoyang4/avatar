package com.sankuai.avatar.resource.whitelist.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.resource.repository.OwtSetWhitelistRepository;
import com.sankuai.avatar.dao.resource.repository.model.OwtSetWhitelistDO;
import com.sankuai.avatar.dao.resource.repository.request.OwtSetWhitelistRequest;
import com.sankuai.avatar.resource.whitelist.OwtSetWhitelistResource;
import com.sankuai.avatar.resource.whitelist.bo.OwtSetWhitelistBO;
import com.sankuai.avatar.resource.whitelist.request.OwtSetWhitelistRequestBO;
import com.sankuai.avatar.resource.whitelist.transfer.OwtSetWhitelistRequestTransfer;
import com.sankuai.avatar.resource.whitelist.transfer.OwtSetWhitelistTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * OwtSetWhitelistResource(owt-set白名单)实现类
 * @author caoyang
 * @create 2022-10-27 20:41
 */
@Slf4j
@Component
public class OwtSetWhitelistResourceImpl implements OwtSetWhitelistResource {

    private final OwtSetWhitelistRepository repository;

    @Autowired
    public OwtSetWhitelistResourceImpl(OwtSetWhitelistRepository repository){
        this.repository = repository;
    }

    @Override
    public PageResponse<OwtSetWhitelistBO> queryPage(OwtSetWhitelistRequestBO requestBO) {
        PageResponse<OwtSetWhitelistBO> pageResponse = new PageResponse<>();
        int page = requestBO.getPage();
        int pageSize = requestBO.getPageSize();
        OwtSetWhitelistRequest request = OwtSetWhitelistRequestTransfer.INSTANCE.toOwtSetWhitelistRequest(requestBO);
        request.setSetName(requestBO.getSetName());
        Page<OwtSetWhitelistDO> pageDO = PageHelper.startPage(page, pageSize).doSelectPage(
                () -> repository.query(request)
        );
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPage(pageDO.getPages());
        pageResponse.setTotalCount(pageDO.getTotal());
        pageResponse.setItems( OwtSetWhitelistTransfer.INSTANCE.toBOList(pageDO));
        return pageResponse;
    }

    @Override
    public List<OwtSetWhitelistBO> query(OwtSetWhitelistRequestBO requestBO) {
        OwtSetWhitelistRequest request = OwtSetWhitelistRequestTransfer.INSTANCE.toOwtSetWhitelistRequest(requestBO);
        request.setSetName(requestBO.getSetName());
        return OwtSetWhitelistTransfer.INSTANCE.toBOList(repository.query(request));
    }

    @Override
    public boolean save(OwtSetWhitelistBO owtSetWhitelistBO) {
        if (Objects.isNull(owtSetWhitelistBO)) {
            return false;
        }
        OwtSetWhitelistDO owtSetWhitelistDO = OwtSetWhitelistTransfer.INSTANCE.toDO(owtSetWhitelistBO);
        List<OwtSetWhitelistBO> owtSetWhitelistBOList = query(OwtSetWhitelistRequestBO.builder()
                .owt(owtSetWhitelistBO.getOwt())
                .app(owtSetWhitelistBO.getApp())
                .setName(owtSetWhitelistBO.getSetName())
                .build());
        if (CollectionUtils.isEmpty(owtSetWhitelistBOList)) {
            return repository.insert(owtSetWhitelistDO);
        } else {
            int id = owtSetWhitelistBOList.get(0).getId();
            owtSetWhitelistDO.setId(id);
            return repository.update(owtSetWhitelistDO);
        }
    }

    @Override
    public boolean deleteByCondition(OwtSetWhitelistRequestBO requestBO) {
        if (ObjectUtils.checkObjAllFieldsIsNull(requestBO)) {
            return false;
        }
        boolean success = true;
        List<OwtSetWhitelistBO> owtSetWhitelistBOList = query(requestBO);
        for (OwtSetWhitelistBO bo : owtSetWhitelistBOList) {
            if (!repository.delete(bo.getId())) {
                success = false;
            }
        }
        return success;
    }

    @Override
    public boolean isWhiteOfOwtSet(String owt, String setName) {
        List<OwtSetWhitelistDO> doList = repository.query(OwtSetWhitelistRequest.builder()
                        .owt(owt).setName(setName)
                        .build());
        return CollectionUtils.isNotEmpty(doList);
    }

    @Override
    public List<OwtSetWhitelistBO> getExpireWhitelist() {
        List<OwtSetWhitelistDO> doList = repository.query(OwtSetWhitelistRequest.builder().endTimeLtn(new Date()).build());
        return OwtSetWhitelistTransfer.INSTANCE.toBOList(doList);
    }
}
