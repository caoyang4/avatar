package com.sankuai.avatar.dao.cache.impl;

import com.sankuai.avatar.dao.cache.UserCacheRepository;
import com.sankuai.avatar.dao.cache.CacheClient;
import com.sankuai.avatar.dao.cache.exception.CacheException;
import com.sankuai.avatar.dao.resource.repository.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserCacheRepository 实现类
 * @author caoyang
 * @create 2022-10-19 18:08
 */
@Slf4j
@Repository
public class UserCacheRepositoryImpl implements UserCacheRepository {

    private static final String CATEGORY = "avatar-user-dx";

    private final CacheClient cache;

    @Autowired
    public UserCacheRepositoryImpl(CacheClient cache){
        this.cache = cache;
    }

    @Override
    public UserDO get(String mis) throws CacheException{
        return cache.get(CATEGORY, mis, UserDO.class);
    }

    @Override
    public List<UserDO> multiGet(List<String> misList) throws CacheException{
        return cache.multiGet(CATEGORY, misList, UserDO.class);
    }

    @Override
    public boolean set(UserDO userDO, int expireTime) throws CacheException{
        return cache.set(CATEGORY, userDO.getLoginName(), userDO, expireTime);
    }

    @Override
    public boolean multiSet(List<UserDO> userDOList, int expireTime) throws CacheException{
        Map<String, UserDO> keyValues = new HashMap<>(16);
        for (UserDO userDO : userDOList) {
            if (!keyValues.containsKey(userDO.getLoginName())) {
                keyValues.put(userDO.getLoginName(), userDO);
            }
        }
        return cache.multiSet(CATEGORY, keyValues, expireTime);
    }
}
