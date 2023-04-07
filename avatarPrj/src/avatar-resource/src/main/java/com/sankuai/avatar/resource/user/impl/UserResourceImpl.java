package com.sankuai.avatar.resource.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sankuai.avatar.client.dx.DxClient;
import com.sankuai.avatar.client.dx.model.DxUser;
import com.sankuai.avatar.client.org.OrgClient;
import com.sankuai.avatar.client.org.model.OrgUser;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.cache.UserCacheRepository;
import com.sankuai.avatar.dao.cache.exception.CacheException;
import com.sankuai.avatar.dao.resource.repository.UserRepository;
import com.sankuai.avatar.dao.resource.repository.model.UserDO;
import com.sankuai.avatar.dao.resource.repository.request.UserRequest;
import com.sankuai.avatar.resource.constants.ThreadPoolConstant;
import com.sankuai.avatar.resource.user.UserResource;
import com.sankuai.avatar.resource.user.bo.UserBO;
import com.sankuai.avatar.resource.user.request.UserRequestBO;
import com.sankuai.avatar.resource.user.transfer.UserRequestTransfer;
import com.sankuai.avatar.resource.user.transfer.UserTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * UserResource 实现类
 * @author caoyang
 * @create 2022-10-20 15:24
 */
@Slf4j
@Component
public class UserResourceImpl implements UserResource {

    private final UserRepository userRepository;
    private final UserCacheRepository userCacheRepository;
    private final OrgClient orgClient;
    private final DxClient dxClient;

    @Autowired
    public UserResourceImpl(UserRepository userRepository,
                            UserCacheRepository userCacheRepository,
                            OrgClient orgClient,
                            DxClient dxClient){
        this.userRepository = userRepository;
        this.userCacheRepository = userCacheRepository;
        this.orgClient = orgClient;
        this.dxClient = dxClient;
    }

    @Override
    public PageResponse<UserBO> queryPage(UserRequestBO userRequestBO) {
        Integer page = userRequestBO.getPage();
        Integer pageSize = userRequestBO.getPageSize();
        Page<UserDO> userDoPage = PageHelper.startPage(page, pageSize).doSelectPage(
                () -> userRepository.query(UserRequestTransfer.INSTANCE.toUserRequest(userRequestBO))
        );
        PageResponse<UserBO> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalCount(userDoPage.getTotal());
        pageResponse.setTotalPage(userDoPage.getPages());
        pageResponse.setItems(UserTransfer.INSTANCE.toBOList(userDoPage));
        return pageResponse;
    }

    @Override
    public List<UserBO> queryByMis(List<String> misList) {
        if (Objects.isNull(misList) || misList.isEmpty()) {return Collections.emptyList();}
        List<UserDO> userDOList = userCacheRepository.multiGet(misList);
        if (CollectionUtils.isNotEmpty(userDOList) && userDOList.size() == misList.size()) {
            return UserTransfer.INSTANCE.toBOList(userDOList);
        }
        Map<String, UserDO> userMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(userDOList)) {
            userMap = userDOList.stream()
                    .filter(userDO -> StringUtils.isNotEmpty(userDO.getLoginName()) && StringUtils.isNotEmpty(userDO.getName()))
                    .collect(Collectors.toMap(UserDO::getLoginName, userDO -> userDO));
        }
        List<UserBO> userBOList = new ArrayList<>();
        for (String mis : misList) {
            // 缓存存在，跳过
            if (MapUtils.isNotEmpty(userMap) && userMap.containsKey(mis)) {
                userBOList.add(UserTransfer.INSTANCE.toBO(userMap.get(mis)));
                continue;
            }
            UserBO userBO = queryByMis(mis);
            if (Objects.nonNull(userBO)) {
                userBOList.add(userBO);
            }
        }
        asyncCacheUserBO(userBOList);
        return userBOList;
    }

    @Override
    public List<UserBO> queryByMisWithOrder(List<String> misList) {
        List<UserBO> userBOList = getUserByCacheOrDB(misList);
        // 按照数组传入顺序返回
        userBOList.sort((o1, o2) -> {
            int i = misList.indexOf(o1.getMis());
            int j = misList.indexOf(o2.getMis());
            return i - j;
        });
        return userBOList;
    }

    @Override
    public UserBO queryByMis(String mis) {
        if (StringUtils.isEmpty(mis)) {return null;}
        UserDO userDO = null;
        try {
            userDO = userCacheRepository.get(mis);
        } catch (CacheException ignore) {}
        if (Objects.isNull(userDO)) {
            List<UserDO> userList = userRepository.query(UserRequest.builder().misList(Collections.singletonList(mis)).build());
            if (CollectionUtils.isNotEmpty(userList)) {
                userDO = userList.get(0);
            } else {
                try {
                    return getUserByOrgDx(mis);
                } catch (SdkBusinessErrorException ignore){}
            }
        }
        return UserTransfer.INSTANCE.toBO(userDO);
    }

    @Override
    public UserBO getUserByOrgDx (String mis) throws SdkBusinessErrorException{
        try {
            OrgUser orgUser = orgClient.getOrgUserByMis(mis);
            DxUser dxUser = dxClient.getDxUserByMis(mis);
            UserBO userBO = new UserBO();
            if (Objects.nonNull(orgUser)) {
                userBO = UserTransfer.INSTANCE.orgAndDxToBO(orgUser, dxUser);
            } else {
                userBO.setMis(mis);
                userBO.setName(dxUser.getName());
                userBO.setUserImage(dxUser.getAvatarUrl());
                userBO.setSource("DX");
            }
            return userBO;
        } catch (SdkBusinessErrorException e){
            throw new SdkBusinessErrorException(String.format("mis:%s 不存在", mis));
        }
    }

    @Override
    public List<UserBO> queryByOrgId(String orgId) {
        List<UserDO> userDOList = userRepository.query(UserRequest.builder().orgId(orgId).build());
        return UserTransfer.INSTANCE.toBOList(userDOList);
    }

    @Override
    public boolean save(UserBO userBO) {
        if (Objects.isNull(userBO) || StringUtils.isEmpty(userBO.getMis())) {return false;}
        List<UserDO> userDOList = userRepository.query(UserRequest.builder()
                .misList(Collections.singletonList(userBO.getMis())).build());
        UserDO userDO = UserTransfer.INSTANCE.toDO(userBO);
        boolean success = false;
        if (userDOList.isEmpty()) {
            success = userRepository.insert(userDO);
        } else {
            userDO.setId(userDOList.get(0).getId());
            success = userRepository.update(userDO);
        }
        try {
            userCacheRepository.set(userDO, -1);
        } catch (CacheException ignore) {}
        return success;
    }

    @Override
    public void asyncAddUser(UserBO userBO) {
        if (Objects.isNull(userBO)) {
            return;
        }
        UserDO userDO = UserTransfer.INSTANCE.toDO(userBO);
        ThreadPoolConstant.RESOURCE_EXECUTOR.submit(() -> userRepository.insert(userDO));
    }

    @Override
    public void asyncUpdateUserRegister(UserBO userBO) {
        if (Objects.isNull(userBO)) {
            return;
        }
        userBO.setLoginTime(new Date());
        ThreadPoolConstant.RESOURCE_EXECUTOR.submit(() -> save(userBO));
    }

    @Override
    public boolean cacheUserBO(List<UserBO> userBOList) {
        if (CollectionUtils.isEmpty(userBOList)) {return false;}
        try {
            return userCacheRepository.multiSet(UserTransfer.INSTANCE.toDOList(userBOList), -1);
        } catch (CacheException ignore) {
        }
        return false;
    }

    @Override
    public boolean deleteByCondition(UserRequestBO userRequestBO) {
        if (ObjectUtils.checkObjAllFieldsIsNull(userRequestBO)) {
            return false;
        }
        List<UserDO> userDOList = userRepository.query(UserRequestTransfer.INSTANCE.toUserRequest(userRequestBO));
        boolean success = true;
        for (UserDO userDO : userDOList) {
            if (!userRepository.delete(userDO.getId())) {
                success = false;
            }
        }
        return success;
    }

    @Override
    public boolean isUserOnJob(String mis) {
        try {
            final String onDuty = "在职";
            OrgUser orgUser = orgClient.getOrgUserByMis(mis);
            return Objects.nonNull(orgUser) && Objects.equals(onDuty, orgUser.getJobStatus());
        } catch (SdkBusinessErrorException ignored) {
        }
        return false;
    }

    @Override
    public List<UserBO> getUserByCache(List<String> misList) {
        if (Objects.isNull(misList) || misList.isEmpty()) {return Collections.emptyList();}
        try {
            return UserTransfer.INSTANCE.toBOList(userCacheRepository.multiGet(misList));
        } catch (CacheException ignored) {
        }
        return Collections.emptyList();
    }

    @Override
    public List<UserBO> getUserByDb(List<String> misList) {
        if (Objects.isNull(misList) || misList.isEmpty()) {return Collections.emptyList();}
        return UserTransfer.INSTANCE.toBOList(userRepository.query(UserRequest.builder().misList(misList).build()));
    }

    @Override
    public List<UserBO> getUserByCacheOrDB(List<String> misList) {
        List<UserBO> userBOList = getUserByCache(misList);
        // 缓存完全命中，直接返回
        if (CollectionUtils.isNotEmpty(userBOList) && misList.size() == userBOList.size()) {
            return userBOList;
        }
        // 未完全命中，查一次db
        userBOList = getUserByDb(misList);
        asyncCacheUserBO(userBOList);
        return userBOList;
    }

    /**
     * 异步缓存
     *
     * @param userBOList userBOList
     */
    private void asyncCacheUserBO(List<UserBO> userBOList){
        ThreadPoolConstant.RESOURCE_EXECUTOR.submit(() -> cacheUserBO(userBOList));
    }

    @Override
    public boolean isOpsSre(String mis) {
        // 150046:业务SRE一组
        // 40001511:业务SRE二组
        // 163003:基础组件SRE组
        // 103068:接入网关SRE组
        final List<String> sreOrg = Arrays.asList("150046", "40001511", "163003", "103068");
        List<UserBO> userBO = getUserByCacheOrDB(Collections.singletonList(mis));
        if (CollectionUtils.isEmpty(userBO) || StringUtils.isEmpty(userBO.get(0).getOrgPath())) {
            return false;
        }
        return sreOrg.stream().anyMatch(org -> userBO.get(0).getOrgPath().contains(org));
    }
}
