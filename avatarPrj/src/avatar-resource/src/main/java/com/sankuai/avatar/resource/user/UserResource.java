package com.sankuai.avatar.resource.user;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.user.bo.UserBO;
import com.sankuai.avatar.resource.user.request.UserRequestBO;

import java.util.List;

/**
 * 人员信息资源管理
 * @author caoyang
 * @create 2022-10-20 15:04
 */
public interface UserResource {

    /**
     * 分页查询user
     *
     * @param userRequestBO 请求
     * @return {@link PageResponse}<{@link UserBO}>
     */
    PageResponse<UserBO> queryPage(UserRequestBO userRequestBO);

    /**
     * 根据 mis 批量查询人员信息
     * @param misList misList
     * @return BOList
     */
    List<UserBO> queryByMis(List<String> misList);

    /**
     * 根据 mis 批量查询人员信息,保证顺序
     *
     * @param misList misList
     * @return {@link List}<{@link UserBO}>
     */
    List<UserBO> queryByMisWithOrder(List<String> misList);

    /**
     * 查询人员信息
     *
     * @param mis 管理信息系统列表
     * @return {@link List}<{@link UserBO}>
     */
    UserBO queryByMis(String mis);

    /**
     * 获取用户通过org dx
     * 通过 org、dx 获取用户信息
     *
     * @param mis 管理信息系统
     * @return {@link UserBO}
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    UserBO getUserByOrgDx(String mis) throws SdkBusinessErrorException;

    /**
     * 根据部门查询人员信息
     * @param orgId 部门 id
     * @return BOList
     */
    List<UserBO> queryByOrgId(String orgId);

    /**
     * 保存(insert/update) user 信息，并缓存
     * @param userBO BO
     * @return 是否成功
     */
    boolean save(UserBO userBO);

    /**
     * 异步添加用户
     *
     * @param userBO userBO
     */
    void asyncAddUser(UserBO userBO);

    /**
     * 异步更新用户登录时间
     *
     * @param userBO userBO
     */
    void asyncUpdateUserRegister(UserBO userBO);

    /**
     * 缓存用户信息
     *
     * @param userBOList 用户bolist
     * @return boolean
     */
    boolean cacheUserBO(List<UserBO> userBOList);

    /**
     * 根据条件清理人员信息
     * @param userRequestBO BO
     * @return 是否成功
     */
    boolean deleteByCondition(UserRequestBO userRequestBO);

    /**
     * 人员是否在职
     *
     * @param mis mis
     * @return boolean
     */
    boolean isUserOnJob(String mis);


    /**
     * 缓存批量获取用户信息
     *
     * @param misList misList
     * @return {@link List}<{@link UserBO}>
     */
    List<UserBO> getUserByCache(List<String> misList);

    /**
     * db批量获取用户信息
     *
     * @param misList misList
     * @return {@link List}<{@link UserBO}>
     */
    List<UserBO> getUserByDb(List<String> misList);

    /**
     * 批量获取用户信息，优先查缓存，缓存未命中，则查 db，不依赖第三方
     *
     * @param misList misList
     * @return {@link List}<{@link UserBO}>
     */
    List<UserBO> getUserByCacheOrDB(List<String> misList);

    /**
     * 是否是 ops sre
     *
     * @param mis mis
     * @return boolean
     */
    boolean isOpsSre(String mis);
}
