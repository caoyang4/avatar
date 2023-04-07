package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.resource.favor.UserRelationResource;
import com.sankuai.avatar.resource.favor.bo.UserRelationBO;
import com.sankuai.avatar.web.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author caoyang
 * @create 2022-12-27 17:16
 */
@Service
public class UserRelationServiceImpl implements UserRelationService {

    private final UserRelationResource resource;

    @Autowired
    public UserRelationServiceImpl(UserRelationResource resource) {
        this.resource = resource;
    }

    @Override
    public Boolean saveUserTopAppkey(String user, String appkey) {
        UserRelationBO userRelationBO = UserRelationBO.builder().loginName(user).appkey(appkey).tag("top").build();
        return resource.saveUserRelation(userRelationBO);
    }

    @Override
    public Boolean cancelUserTopAppkey(String user, String appkey) {
        return resource.cancelUserRelation(user, appkey);
    }
}
