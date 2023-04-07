package com.sankuai.avatar.resource.favor.impl;

import com.sankuai.avatar.dao.resource.repository.UserRelationRepository;
import com.sankuai.avatar.dao.resource.repository.model.UserRelationDO;
import com.sankuai.avatar.dao.resource.repository.request.UserRelationRequest;
import com.sankuai.avatar.resource.favor.UserRelationResource;
import com.sankuai.avatar.resource.favor.bo.UserRelationBO;
import com.sankuai.avatar.resource.favor.transfer.UserRelationTransfer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2022-12-27 16:35
 */
@Component
public class UserRelationResourceImpl implements UserRelationResource {

    private final UserRelationRepository repository;

    @Autowired
    public UserRelationResourceImpl(UserRelationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> getUserTopAppkey(String mis) {
        List<UserRelationDO> relationDOList = repository.query(UserRelationRequest.builder()
                        .loginName(mis).tag("top").build());
        return relationDOList.stream().map(UserRelationDO::getAppkey).distinct().collect(Collectors.toList());
    }

    @Override
    public Boolean saveUserRelation(UserRelationBO userRelationBO) {
        if (Objects.isNull(userRelationBO)) {
            return false;
        }
        UserRelationDO userRelationDO = UserRelationTransfer.INSTANCE.toDO(userRelationBO);
        userRelationDO.setUpdateTime(new Date());
        List<UserRelationDO> relationDOList = repository.query(UserRelationRequest.builder()
                .loginName(userRelationBO.getLoginName()).appkey(userRelationBO.getAppkey()).tag("top").build());
        if (CollectionUtils.isNotEmpty(relationDOList)) {
            userRelationDO.setId(relationDOList.get(0).getId());
            return repository.update(userRelationDO);
        } else {
            return repository.insert(userRelationDO);
        }
    }

    @Override
    public Boolean cancelUserRelation(String user, String appkey) {
        List<UserRelationDO> relationDOList = repository.query(UserRelationRequest.builder()
                .loginName(user).appkey(appkey).tag("top").build());
        boolean success = true;
        for (UserRelationDO userRelationDO : relationDOList) {
            if (!repository.delete(userRelationDO.getId())) {
                success = false;
            }
        }
        return success;
    }
}
