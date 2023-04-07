package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.user.UserResource;
import com.sankuai.avatar.resource.user.bo.UserBO;
import com.sankuai.avatar.resource.user.request.UserRequestBO;
import com.sankuai.avatar.web.dto.user.DxUserDTO;
import com.sankuai.avatar.web.dto.user.UserDTO;
import com.sankuai.avatar.web.request.UserPageRequest;
import com.sankuai.avatar.web.service.UserService;
import com.sankuai.avatar.web.transfer.user.UserDTOTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * UserService 实现类
 * @author caoyang
 * @create 2022-11-01 17:38
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    private final UserResource userResource;

    @Autowired
    public UserServiceImpl(UserResource userResource){
        this.userResource = userResource;
    }

    @Override
    public PageResponse<UserDTO> queryPage(UserPageRequest userPageRequest) {
        UserRequestBO userRequestBO = new UserRequestBO();
        userRequestBO.setPage(userPageRequest.getPage());
        userRequestBO.setPageSize(userPageRequest.getPageSize());
        userRequestBO.setOrgId(userPageRequest.getOrgId());
        userRequestBO.setSearch(userPageRequest.getLoginName());
        userRequestBO.setId(userPageRequest.getId());
        userRequestBO.setOrgPath(userPageRequest.getOrgPath());

        PageResponse<UserBO> userBoPageResponse = userResource.queryPage(userRequestBO);
        PageResponse<UserDTO> pageResponse = new PageResponse<>();
        pageResponse.setPage(userBoPageResponse.getPage());
        pageResponse.setPageSize(userBoPageResponse.getPageSize());
        pageResponse.setTotalCount(userBoPageResponse.getTotalCount());
        pageResponse.setTotalPage(userBoPageResponse.getTotalPage());
        pageResponse.setItems(UserDTOTransfer.INSTANCE.toDTOList(userBoPageResponse.getItems()));
        return pageResponse;
    }

    @Override
    public List<UserDTO> queryUserByMis(List<String> misList) {
        List<UserBO> userBOList = userResource.queryByMisWithOrder(misList);
        if (CollectionUtils.isEmpty(userBOList)) {
            return new ArrayList<>();
        }
        return UserDTOTransfer.INSTANCE.toDTOList(userBOList);
    }

    @Override
    public List<UserDTO> queryUserByMisNoOrder(List<String> misList) {
        List<UserBO> userBOList = userResource.getUserByCacheOrDB(misList);
        if (CollectionUtils.isEmpty(userBOList)) {
            return new ArrayList<>();
        }
        return UserDTOTransfer.INSTANCE.toDTOList(userBOList);
    }

    @Override
    public List<UserDTO> queryUserByCacheDbOrg(List<String> misList) {
        List<UserBO> userBOList = userResource.queryByMis(misList);
        if (CollectionUtils.isEmpty(userBOList)) {
            return new ArrayList<>();
        }
        return UserDTOTransfer.INSTANCE.toDTOList(userBOList);
    }

    @Override
    public List<DxUserDTO> getDxUserByMis(List<String> misList) {
        List<UserBO> userBOList = userResource.queryByMisWithOrder(misList);
        if (CollectionUtils.isEmpty(userBOList)) {
            return new ArrayList<>();
        }
        return  UserDTOTransfer.INSTANCE.toDxUserDTOList(userBOList);
    }

    @Override
    public String getUserJobStatus(String mis) {
        return userResource.isUserOnJob(mis) ? "在职" : "离职";
    }

    @Override
    public UserDTO getDxUserByClient(String mis) {
        try {
            UserBO orgDx = userResource.getUserByOrgDx(mis);
            asyncAddUser(orgDx);
            return UserDTOTransfer.INSTANCE.toDTO(orgDx);
        } catch (SdkBusinessErrorException ignored) {
        }
        return null;
    }

    private void asyncAddUser(UserBO userBO){
        if (Objects.isNull(userBO)) {
            return;
        }
        if (StringUtils.isEmpty(userBO.getName())) {
            userBO.setName(userBO.getMis());
        }
        if (StringUtils.isEmpty(userBO.getRole())) {
            userBO.setRole("rd");
        }
        userResource.asyncAddUser(userBO);
    }

    @Override
    public void updateDBUser(UserDTO userDTO) {
        userResource.asyncUpdateUserRegister(UserDTOTransfer.INSTANCE.toBO(userDTO));
    }

    @Override
    public boolean isOpsSre(String mis) {
        return userResource.isOpsSre(mis);
    }
}

