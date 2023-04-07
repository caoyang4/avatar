package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.user.DxUserDTO;
import com.sankuai.avatar.web.dto.user.UserDTO;
import com.sankuai.avatar.web.request.UserPageRequest;

import java.util.List;


public interface UserService {

    /**
     * 分页查询user
     *
     * @param userPageRequest  查询条件
     * @return {@link PageResponse}<{@link UserDTO}>
     */
    PageResponse<UserDTO> queryPage(UserPageRequest userPageRequest);

    /**
     * 查询用户信息，只查缓存和 db，有序返回
     *
     * @param misList mis列表
     * @return {@link List}<{@link UserDTO}>
     */
    List<UserDTO> queryUserByMis(List<String> misList);

    /**
     * 查询用户信息，只查缓存和 db，无序返回
     *
     * @param misList mis列表
     * @return {@link List}<{@link UserDTO}>
     */
    List<UserDTO> queryUserByMisNoOrder(List<String> misList);

    /**
     * 查询用户信息，支持溯源，即查缓存和db，org/dx
     *
     * @param misList 管理信息系统列表
     * @return {@link List}<{@link UserDTO}>
     */
    List<UserDTO> queryUserByCacheDbOrg(List<String> misList);

    /**
     * 得到dx用户信息
     *
     * @param misList misList
     * @return {@link List}<{@link DxUserDTO}>
     */
    List<DxUserDTO> getDxUserByMis(List<String> misList);

    /**
     * 获取用户工作状态
     *
     * @param mis mis
     * @return {@link String}
     */
    String getUserJobStatus(String mis);

    /**
     * 通过org/dx溯源获取 user
     *
     * @param mis mis
     * @return {@link UserDTO}
     */
    UserDTO getDxUserByClient(String mis);


    /**
     * 更新用户信息
     *
     * @param userDTO 用户dto
     */
    void updateDBUser(UserDTO userDTO);

    /**
     * 是否是 ops sre
     *
     * @param mis mis
     * @return boolean
     */
    boolean isOpsSre(String mis);
}
