package com.sankuai.avatar.web.service;

import com.sankuai.avatar.client.soa.model.ScPageResponse;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.sdk.manager.ServiceCatalogAppKey;
import com.sankuai.avatar.web.dto.application.ApplicationDTO;
import com.sankuai.avatar.web.dto.application.ApplicationDetailDTO;
import com.sankuai.avatar.web.dto.application.ScQueryApplicationDTO;
import com.sankuai.avatar.web.dto.application.UserOwnerApplicationDTO;
import com.sankuai.avatar.web.request.application.ApplicationPageRequestDTO;
import com.sankuai.avatar.web.vo.PageInfo;
import com.sankuai.avatar.web.vo.application.AppKeyVO;

/**
 * @author Jie.li.sh
 * @create 2020-02-18
 **/
public interface ApplicationService {

    /**
     * 获取应用下的服务列表
     * @param appKeyQueryParams 查询参数
     * @return
     */
    PageInfo<AppKeyVO> getAppKeyByApplication(ServiceCatalogAppKey.AppKeyQueryParams appKeyQueryParams);

    /**
     * 获取应用列表
     *
     * @param requestDTO 请求DTO
     * @return {@link PageResponse}<{@link ApplicationDTO}>
     */
    PageResponse<ApplicationDTO> getApplications(ApplicationPageRequestDTO requestDTO);

    /**
     * 获取用户组内应用
     *
     * @param requestDTO 请求
     * @return {@link PageResponse}<{@link UserOwnerApplicationDTO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    PageResponse<UserOwnerApplicationDTO> getUserOwnerApplications(ApplicationPageRequestDTO requestDTO) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 查询检索应用信息（带结算单元信息）
     *
     * @param requestDTO 请求
     * @return {@link ScPageResponse}<{@link ScQueryApplicationDTO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    PageResponse<ScQueryApplicationDTO> queryApplications(ApplicationPageRequestDTO requestDTO) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取应用信息
     *
     * @param name 应用名称
     * @return {@link ApplicationDetailDTO}
     */
    ApplicationDetailDTO getApplication(String name);
}
