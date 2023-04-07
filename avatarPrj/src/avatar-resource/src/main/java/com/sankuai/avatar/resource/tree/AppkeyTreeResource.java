package com.sankuai.avatar.resource.tree;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.tree.bo.*;
import com.sankuai.avatar.resource.tree.request.SrvQueryRequestBO;

import java.util.List;
import java.util.Map;

/**
 * 服务树资源管理
 *
 * @author zhangxiaoning07
 * @create 2022-10-19
 */

public interface AppkeyTreeResource {

    /**
     * 获取srv服务树信息（包含pdl、owt信息）
     *
     * @param srv 名称
     * @return {@link AppkeyTreeBO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    AppkeyTreeBO getAppkeyTreeByKey(String srv)  throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取srv服务树信息（包含plus信息）
     * @param srv 名称
     * @return {@link AppkeyTreeSrvDetailBO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    AppkeyTreeSrvDetailBO getAppkeyTreeSrvDetailByKey(String srv)  throws SdkCallException, SdkBusinessErrorException;

    /**
     * 按照查询条件获取srv服务树信息
     * @param srvQueryRequestBO srv查询条件
     * @return {@link AppkeyTreeSrvDetailResponseBO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    AppkeyTreeSrvDetailResponseBO getSrvsByQueryRequest(SrvQueryRequestBO srvQueryRequestBO)  throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取srv服务树节点关注人
     *
     * @param srv 名称
     * @return {@link List<String>}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<String> getSrvSubscribers(String srv)  throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取所有BG
     *
     * @return BG列表
     */
    List<AppkeyTreeBgBO> getBgList();

    /**
     * 获取用户的BG
     *
     * @param user 用户
     * @return BG列表
     */
    List<AppkeyTreeBgBO> getBgListByUser(String user);

    /**
     * 获取所有的Owt
     *
     * @return Owt列表
     */
    List<AppkeyTreeOwtBO> getOwtList();

    /**
     * 根据key获取Owt详情
     *
     * @param key Owt的key, 比如服务运维部是dianping.tbd
     * @return Owt详情
     */
    AppkeyTreeOwtBO getOwtByKey(String key);

    /**
     * 根据Owt的key获取OPS的Pdl列表
     *
     * @param owt Owt的key, 比如服务运维部是dianping.tbd
     * @return OpsPdlBO对象
     */
    List<AppkeyTreePdlBO> getPdlListByOwt(String owt);

    /**
     * 获取OPS的Pdl列表
     *
     * @return OpsPdlBO对象
     */
    List<AppkeyTreePdlBO> getPdlList();

    /**
     * 根据Pdl的key查询Pdl信息
     *
     * @param key Pdl的key字段，如变更管理工具是dianping.tbd.change
     * @return OpsPdlDetailBO对象
     */
    AppkeyTreePdlBO getPdlByKey(String key);


    /**
     * 获取用户所属的 bg 列表
     *
     * @param user 用户
     * @return {@link List}<{@link String}>
     */
    List<String> getUserBg(String user);

    /**
     * 无缓存获取用户bg
     *
     * @param user 用户
     * @return {@link List}<{@link String}>
     */
    List<String> getUserBgNoCache(String user);

    /**
     * 缓存用户bg
     *
     * @param user   用户
     * @param bgList bg列表
     * @return {@link Boolean}
     */
    Boolean cacheUserBg(String user, List<String> bgList);

    /**
     * 无缓存获取用户bg树
     *
     * @param user 用户
     * @return {@link List}<{@link UserBgBO}>
     */
    List<UserBgBO> getUserBgTreeNoCache(String user);


    /**
     * 缓存获取用户bg树
     *
     * @param user   用户
     * @param bgTree bg树
     * @return {@link Boolean}
     */
    Boolean cacheUserBgTree(String user, List<UserBgBO> bgTree);

    /**
     * 获取用户所属bg-owt-pdl
     *
     * @param user 用户
     * @return {@link List}<{@link UserBgBO}>
     */
    List<UserBgBO> getUserBgTree(String user);

    /**
     * 无缓存获取bg树
     *
     * @param user 用户
     * @param mine 是否只和我相关
     * @return {@link List}<{@link UserBgBO}>
     */
    List<UserBgBO> getBgTreeNoCache(String user, boolean mine);

    /**
     * 获取产品线服务数量
     *
     * @param pdl pdl
     * @return {@link Integer}
     */
    Integer getPdlSrvCount(String pdl);

    /**
     * 批量获取产品线服务数量
     *
     * @param pdlList pdl列表
     * @return {@link Map}<{@link String}, {@link Integer}>
     */
    Map<String, Integer> batchCacheGetPdlSrvCount(List<String> pdlList);

    /**
     * 无缓存获取产品线服务数量
     *
     * @param pdl pdl
     * @return {@link Integer}
     */
    Integer getPdlSrvCountNoCache(String pdl);

    /**
     * 缓存产品线服务数量
     *
     * @param pdl   pdl
     * @param count 数量
     * @return {@link Boolean}
     */
    Boolean cachePdlSrvCount(String pdl, int count);

}
