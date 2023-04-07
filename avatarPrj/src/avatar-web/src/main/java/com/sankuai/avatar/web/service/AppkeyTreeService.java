package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.web.dto.tree.*;

import java.util.List;

/**
 * 服务树业务逻辑Service接口
 *
 * @author zhangxiaoning07
 * @create 2022-10-19
 */
public interface AppkeyTreeService {

    /**
     * 获取所有bg列表
     *
     * @return {@link List}<{@link String}>
     */
    List<String> getAllBgList();

    /**
     * 用户 bg 列表
     *
     * @param user 用户
     * @return {@link List}<{@link String}>
     */
    List<String> getUserBg(String user);

    /**
     * 新增服务时，用户 bg 列表
     *
     * @param user 用户
     * @return {@link List}<{@link String}>
     */
    List<String> getBgForAddSrv(String user);

    /**
     * 获取用户的服务树
     *
     * @param user 当前登陆用户, BG级对象为用户有权限的BG, OWT与PDL级为相应上级下所有的对象
     * @return 用户的服务树。目前包含BG,OWT,PDL三级。
     */
    List<UserBgDTO> getUserBgTree(String user);

    /**
     * 获取用户的服务树
     *
     * @param user 用户
     * @param mine 是否只和我相关
     * @return {@link List}<{@link UserBgDTO}>
     */
    List<UserBgDTO> getBgTree(String user, Boolean mine);

    /**
     * 获取Owt列表
     *
     * @param user    用户
     * @param bg      business group
     * @param keyword 搜索关键字
     * @return Owt对象列表
     */
    List<AppkeyTreeOwtDTO> getUserOwtList(String user, String bg, String keyword);

    /**
     * owt
     *
     * @param key  owt
     * @param user 用户
     * @return {@link AppkeyTreeOwtDTO}
     */
    AppkeyTreeOwtDTO getOwtByKey(String key, String user);

    /**
     * 获取Owt的Pdl列表
     *
     * @param owtKey     Owt的key, 例如服务运维部是dianping.tbd
     * @param keyword 搜索关键字
     * @return Pdl对象列表
     */
    List<AppkeyTreePdlDTO> getPdlListByOwtKey(String owtKey, String keyword);

    /**
     * 获取Pdl详情
     *
     * @param key  Pdl的key字段，如变更管理工具是dianping.tbd.change
     * @param user 当前登陆用户, 判断是否有权限修改
     * @return Pdl详情对象
     */
    AppkeyTreePdlDTO getPdlByKey(String key, String user);

    /**
     * 获取srv服务树信息（包含pdl、owt信息）
     *
     * @param srv 名称
     * @return {@link AppkeyTreeDTO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    AppkeyTreeDTO getAppkeyTreeByKey(String srv)  throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取srv服务树信息（包含plus信息）
     * @param srv 名称
     * @return {@link AppkeyTreeSrvDetailDTO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    AppkeyTreeSrvDetailDTO getAppkeyTreeSrvDetailByKey(String srv)  throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取srv服务树节点关注人
     *
     * @param srv 名称
     * @return {@link List<String>}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<String> getSrvSubscribers(String srv)  throws SdkCallException, SdkBusinessErrorException;

}
