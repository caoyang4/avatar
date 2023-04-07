package com.sankuai.avatar.resource.activity.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.ActivityResource;
import com.sankuai.avatar.resource.activity.bo.ActivityResourceBO;
import com.sankuai.avatar.resource.activity.request.ActivityResourceRequestBO;
import com.sankuai.avatar.resource.activity.transfer.ActivityResourceTransfer;
import com.sankuai.avatar.dao.resource.repository.ActivityResourceRepository;
import com.sankuai.avatar.dao.resource.repository.model.ActivityResourceDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-03-08 15:05
 */
@Component
public class ActivityResourceImpl implements ActivityResource {

    private final ActivityResourceRepository repository;

    @Autowired
    public ActivityResourceImpl(ActivityResourceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ActivityResourceBO> query(ActivityResourceRequestBO requestBO) {
        List<ActivityResourceDO> doList = repository.query(ActivityResourceTransfer.INSTANCE.toRequest(requestBO));
        return ActivityResourceTransfer.INSTANCE.toBOList(doList);
    }

    @Override
    public PageResponse<ActivityResourceBO> queryPage(ActivityResourceRequestBO requestBO) {
        PageResponse<ActivityResourceBO> pageResponse = new PageResponse<>();
        int page = requestBO.getPage();
        int pageSize = requestBO.getPageSize();
        Page<ActivityResourceDO> doPage = PageHelper.startPage(page, pageSize).doSelectPage(
                () -> repository.query(ActivityResourceTransfer.INSTANCE.toRequest(requestBO))
        );
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPage(doPage.getPages());
        pageResponse.setTotalCount(doPage.getTotal());
        pageResponse.setItems(ActivityResourceTransfer.INSTANCE.toBOList(doPage));
        return pageResponse;
    }

    @Override
    public Boolean save(ActivityResourceBO activityResourceBO) {
        if (Objects.isNull(activityResourceBO.getId())) {
            return repository.insert(ActivityResourceTransfer.INSTANCE.toDO(activityResourceBO));
        }
        return repository.update(ActivityResourceTransfer.INSTANCE.toDO(activityResourceBO));
    }

    @Override
    public Boolean deleteByPk(int pk) {
        return repository.delete(pk);
    }
}
