package com.sankuai.avatar.resource.activity.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.WindowPeriodResource;
import com.sankuai.avatar.resource.activity.bo.WindowPeriodResourceBO;
import com.sankuai.avatar.resource.activity.request.WindowPeriodRequestBO;
import com.sankuai.avatar.resource.activity.transfer.WindowPeriodTransfer;
import com.sankuai.avatar.dao.resource.repository.WindowPeriodRepository;
import com.sankuai.avatar.dao.resource.repository.model.ResourceWindowPeriodDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-03-15 16:48
 */
@Component
public class WindowPeriodResourceImpl implements WindowPeriodResource {

    private final WindowPeriodRepository repository;

    @Autowired
    public WindowPeriodResourceImpl(WindowPeriodRepository repository) {
        this.repository = repository;
    }

    @Override
    public PageResponse<WindowPeriodResourceBO> queryPage(WindowPeriodRequestBO requestBO) {
        int page = requestBO.getPage();
        int pageSize = requestBO.getPageSize();
        PageResponse<WindowPeriodResourceBO> pageResponse = new PageResponse<>();
        Page<ResourceWindowPeriodDO> doPage = PageHelper.startPage(page, pageSize).doSelectPage(
                () -> repository.query(WindowPeriodTransfer.INSTANCE.toRequest(requestBO))
        );
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPage(doPage.getPages());
        pageResponse.setTotalCount(doPage.getTotal());
        pageResponse.setItems(WindowPeriodTransfer.INSTANCE.toBOList(doPage));
        return pageResponse;
    }

    @Override
    public Integer getMaxWindowId() {
        return repository.getMaxWindowId();
    }

    @Override
    public List<WindowPeriodResourceBO> query(WindowPeriodRequestBO requestBO) {
        List<ResourceWindowPeriodDO> doList = repository.query(WindowPeriodTransfer.INSTANCE.toRequest(requestBO));
        return WindowPeriodTransfer.INSTANCE.toBOList(doList);
    }

    @Override
    public Boolean save(WindowPeriodResourceBO resourceBO) {
        ResourceWindowPeriodDO periodDO = WindowPeriodTransfer.INSTANCE.toDO(resourceBO);
        if (Objects.isNull(resourceBO.getId())) {
            return repository.insert(periodDO);
        }
        return repository.update(periodDO);
    }

    @Override
    public Boolean deleteByPk(int pk) {
        return repository.delete(pk);
    }

}
