package com.sankuai.avatar.workflow.server.controller;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.cache.exception.CacheException;
import com.sankuai.avatar.workflow.server.dto.flow.FlowDTO;
import com.sankuai.avatar.workflow.server.dto.flow.FlowUserDTO;
import com.sankuai.avatar.workflow.server.request.FlowPageRequest;
import com.sankuai.avatar.workflow.server.service.FlowService;
import com.sankuai.avatar.workflow.server.service.FlowUserService;
import com.sankuai.avatar.workflow.server.transfer.FlowTransfer;
import com.sankuai.avatar.workflow.server.utils.UserUtils;
import com.sankuai.avatar.workflow.server.vo.flow.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程查询类接口, 流程列表、流程详情、我的流程、我的审批等各类查询场景
 *
 * @author zhaozhifan02
 */
@Slf4j
@RequestMapping("/api/v2/avatar/workflow/flow")
@RestController
public class FlowQueryController {

    @Autowired
    private FlowService flowService;

    @Autowired
    private FlowUserService userService;

    /**
     * 获取流程
     *
     * @param uuid uuid
     * @return {@link FlowVO}
     */
    @GetMapping("/{uuid}")
    public FlowVO getFlow(@PathVariable String uuid) {
        FlowDTO flowDTO = flowService.getFlowByUuid(uuid);
        return FlowTransfer.INSTANCE.toVO(flowDTO);
    }

    /**
     * 获取流程详情
     *
     * @param uuid 流程UUID
     * @return {@link FlowDetailVO}
     */
    @GetMapping("/{uuid}/detail")
    public FlowDetailVO getFlowDetail(@PathVariable String uuid) {
        return null;
    }

    /**
     * 获取流程权限
     *
     * @param uuid 流程UUID
     * @return {@link FlowPermissionVO}
     */
    @GetMapping("/{uuid}/permission")
    public FlowPermissionVO getFlowPermission(@PathVariable String uuid) {
        return null;
    }

    /**
     * 获取流程展示信息
     *
     * @param uuid 流程UUID
     * @return {@link FlowPermissionVO}
     */
    @GetMapping("/{uuid}/display")
    public FlowDisplayVO getFlowDisplay(@PathVariable String uuid) {
        return null;
    }


    /**
     * 所有流程
     *
     * @param pageRequest 请求
     * @return {@link PageResponse}<{@link FlowVO}>
     */
    @GetMapping("")
    public PageResponse<FlowHomeVO> getAllFlow(FlowPageRequest pageRequest){
        pageRequest.setStateSort(false);
        PageResponse<FlowDTO> dtoPageResponse = flowService.getPageFlow(pageRequest);
        return toPageResponse(dtoPageResponse);
    }

    /**
     * 我的申请
     *
     * @param pageRequest 请求
     * @return {@link PageResponse}<{@link FlowVO}>
     */
    @GetMapping("/apply")
    public PageResponse<FlowHomeVO> getMineFlow(FlowPageRequest pageRequest){
        if (StringUtils.isBlank(pageRequest.getCreateUser())){
            pageRequest.setCreateUser(UserUtils.getCurrentCasUser().getLoginName());
        }
        PageResponse<FlowDTO> dtoPageResponse = flowService.getPageFlow(pageRequest);
        return toPageResponse(dtoPageResponse);
    }

    /**
     * 我的审核
     *
     * @param pageRequest 请求
     * @return {@link PageResponse}<{@link FlowVO}>
     */
    @GetMapping("/approve")
    public PageResponse<FlowHomeVO> getApprovedFlow(FlowPageRequest pageRequest){
        if (StringUtils.isBlank(pageRequest.getApproveUsers())){
            pageRequest.setApproveUsers(UserUtils.getCurrentCasUser().getLoginName());
        }
        PageResponse<FlowDTO> dtoPageResponse = flowService.getPageFlow(pageRequest);
        return toPageResponse(dtoPageResponse);
    }

    private PageResponse<FlowHomeVO> toPageResponse(PageResponse<FlowDTO> dtoPageResponse){
        PageResponse<FlowHomeVO> pageResponse = new PageResponse<>();
        pageResponse.setPage(dtoPageResponse.getPage());
        pageResponse.setPageSize(dtoPageResponse.getPageSize());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        List<FlowDTO> dtoList = dtoPageResponse.getItems();
        List<FlowHomeVO> voList = FlowTransfer.INSTANCE.toHomeVOList(dtoList);
        Set<String> userSet = new HashSet<>();
        for (FlowHomeVO flowHomeVO : voList) {
            userSet.add(flowHomeVO.getCreateUser());
            userSet.addAll(flowHomeVO.getApproveUsers());
        }
        Map<String, FlowUserDTO> userMap = getUserMap(new ArrayList<>(userSet));
        voList.forEach(vo -> {
            FlowUserDTO userDTO = MapUtils.isNotEmpty(userMap) ? userMap.getOrDefault(vo.getCreateUser(), null) : null;
            vo.setApplyUserImage(Objects.nonNull(userDTO) ? userDTO.getUserImage() : "");
            vo.setApplyUser(Objects.nonNull(userDTO) && StringUtils.isNotEmpty(userDTO.getName())
                    ? (userDTO.getName() + "/" + vo.getCreateUser())
                    : vo.getCreateUser() + "/" + vo.getCreateUser());
            vo.setApproveUsers(toApproveUsers(vo.getApproveUsers(), userMap));
        });
        pageResponse.setItems(voList);
        return pageResponse;
    }

    private Map<String, FlowUserDTO> getUserMap( List<String> userList){
        List<FlowUserDTO> userDTOList = new ArrayList<>();
        try {
            userDTOList = userService.getFlowUserDTO(userList);
        } catch (CacheException ignored) {}
        if (CollectionUtils.isEmpty(userDTOList)) {return new HashMap<>(1);}
        return userDTOList.stream().collect(Collectors.toMap(FlowUserDTO::getLoginName, flowUserDTO -> flowUserDTO));
    }

    private List<String> toApproveUsers(List<String> approveUsers, Map<String, FlowUserDTO> userMap){
        if (CollectionUtils.isEmpty(approveUsers)) {
            return approveUsers;
        }
        List<String> users = new ArrayList<>();
        for (String approveUser : approveUsers) {
            FlowUserDTO userDTO = MapUtils.isNotEmpty(userMap) ? userMap.getOrDefault(approveUser, null) : null;
            users.add(Objects.nonNull(userDTO) && StringUtils.isNotEmpty(userDTO.getName())
                    ? (userDTO.getName() + "/" + approveUser)
                    : approveUser);
        }
        return users;
    }

}
