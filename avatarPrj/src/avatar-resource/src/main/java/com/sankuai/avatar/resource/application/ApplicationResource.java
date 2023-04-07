package com.sankuai.avatar.resource.application;

import com.sankuai.avatar.client.soa.model.ScPageResponse;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.application.bo.ApplicationBO;
import com.sankuai.avatar.resource.application.bo.ApplicationDetailBO;
import com.sankuai.avatar.resource.application.bo.UserOwnerApplicationBO;
import com.sankuai.avatar.resource.application.request.ApplicationPageRequestBO;
import com.sankuai.avatar.resource.application.request.ScQueryApplicationBO;

import java.util.List;

/**
 * 应用信息资源
 *
 * @author zhangxiaoning07
 * @date 2022/11/24
 */
public interface ApplicationResource {

    /**
     * 获取应用程序列表
     *
     * @param requestBO 请求对象
     * @return {@link List}<{@link ApplicationBO}>
     */
    PageResponse<ApplicationBO> getApplications(ApplicationPageRequestBO requestBO);

    /**
     * 获取PAAS应用程序列表
     *
     * @return {@link List}<{@link String}>
     */
    List<String> getPaasApplications();

    /**
     * 获取用户组内应用
     *
     * @param requestBO 请求
     * @return {@link PageResponse}<{@link UserOwnerApplicationBO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    PageResponse<UserOwnerApplicationBO> getUserOwnerApplications(ApplicationPageRequestBO requestBO) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 查询检索应用信息（带结算单元信息）
     *
     * @param requestBO 请求
     * @return {@link ScPageResponse}<{@link ScQueryApplicationBO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    PageResponse<ScQueryApplicationBO> queryApplications(ApplicationPageRequestBO requestBO) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取应用程序信息
     *
     * @param name 应用名称
     * @return {@link ApplicationBO}
     */
    ApplicationDetailBO getApplication(String name);
}
