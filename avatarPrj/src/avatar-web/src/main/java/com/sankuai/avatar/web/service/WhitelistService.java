package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.web.dto.whitelist.ServiceWhitelistDTO;
import com.sankuai.avatar.web.dto.whitelist.WhitelistAppDTO;
import com.sankuai.avatar.web.request.WhitelistPageRequest;

import java.util.List;

/**
 * 白名单业务 service 接口
 * @author caoyang
 * @create 2022-11-14 20:12
 */
public interface WhitelistService {

    /**
     * 分页查询，默认过滤到期失效白名单
     *
     * @param pageRequest 页面请求
     * @return {@link PageResponse}<{@link ServiceWhitelistDTO}>
     */
    PageResponse<ServiceWhitelistDTO> queryPage(WhitelistPageRequest pageRequest);

    /**
     * 查询所有白名单类型
     *
     * @return {@link List}<{@link WhitelistAppDTO}>
     */
    List<WhitelistAppDTO> getAllWhitelistType();


    /**
     * 查询服务生效白名单
     *
     * @param appkey appkey
     * @return {@link List}<{@link ServiceWhitelistDTO}>
     */
    List<ServiceWhitelistDTO> getServiceWhitelistByAppkey(String appkey);

    /**
     * 查询服务生效白名单
     *
     * @param appkey    appkey
     * @param whiteType 白色类型
     * @return {@link List}<{@link ServiceWhitelistDTO}>
     */
    List<ServiceWhitelistDTO> getServiceWhitelistByAppkey(String appkey, WhiteType whiteType);

    /**
     * 保存服务白名单
     *
     * @param serviceWhitelistDTO serviceWhitelistDTO
     * @return {@link Boolean}
     */
    Boolean saveServiceWhitelist(ServiceWhitelistDTO serviceWhitelistDTO);

    /**
     * 根据主键删除白名单
     *
     * @param pk pk
     * @return {@link Boolean}
     */
    Boolean deleteWhitelistByPk(int pk);

    /**
     * 根据appkey删除白名单
     *
     * @param appkey appkey
     * @return {@link Boolean}
     */
    Boolean deleteWhitelistByAppkey(String appkey);

    /**
     * 根据appkey和白名单类型删除白名单
     *
     * @param appkey appkey
     * @param type   类型
     * @return {@link Boolean}
     */
    Boolean deleteWhitelistByAppkeyAndType(String appkey, WhiteType type);

    /**
     * 取消白名单
     *
     * @param appkey    appkey
     * @param type      白名单类型
     * @param cellNames 单元名称
     * @return {@link Boolean}
     */
    Boolean cancelWhitelist(String appkey, WhiteType type, List<String> cellNames);

    /**
     * 发送取消白名单通知
     *
     * @param user         用户
     * @param appkey       appkey
     * @param type         类型
     * @param cellNames    set名称列表
     * @param whitelistDTO 白名单
     */
    void sendCancelWhiteNotice(String user, String appkey, WhiteType type, List<String> cellNames, ServiceWhitelistDTO whitelistDTO);


}
