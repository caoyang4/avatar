package com.sankuai.avatar.web.controller;

import com.google.common.collect.ImmutableMap;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.utils.PageHelperUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.constant.ResourceStatusType;
import com.sankuai.avatar.web.dal.entity.CasUser;
import com.sankuai.avatar.web.dto.activity.ActivityResourceDTO;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.request.ActivityResourcePageRequest;
import com.sankuai.avatar.web.service.ActivityResourceService;
import com.sankuai.avatar.web.transfer.activity.ActivityResourceVOTransfer;
import com.sankuai.avatar.web.util.FileUtils;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.avatar.web.vo.activity.ActivityResourceChangeVO;
import com.sankuai.avatar.web.vo.activity.ActivityResourceExportVO;
import com.sankuai.avatar.web.vo.activity.ActivityResourceVO;
import com.sankuai.avatar.web.vo.activity.ResourceSumResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2023-03-08 17:12
 */
@Validated
@RestController
@RequestMapping("/api/v2/avatar/activity_resource")
public class ActivityResourceController {

    @MdpConfig("HULK_SRE:[\"qinwei05\",\"jie.li.sh\",\"zhaozhifan02\"]")
    private String[] hulkSre;

    private final ActivityResourceService service;

    @Autowired
    public ActivityResourceController(ActivityResourceService service) {
        this.service = service;
    }

    @GetMapping("")
    PageResponse<ActivityResourceVO> getPageActivityResource(@Valid ActivityResourcePageRequest pageRequest){
        PageResponse<ActivityResourceDTO> dtoPageResponse = service.getPageActivityResource(pageRequest);
        return PageHelperUtils.convertPageResponse(dtoPageResponse,
                dtoList -> ActivityResourceVOTransfer.INSTANCE.toVOList(dtoPageResponse.getItems())
        );
    }

    @GetMapping("/{id}")
    public ActivityResourceVO getSingleActivityResource(@PathVariable("id") Integer id) {
        ActivityResourceDTO resourceDTO = service.getActivityResourceByPk(id);
        return ActivityResourceVOTransfer.INSTANCE.toVO(resourceDTO);
    }

    @PostMapping("")
    public Integer saveActivityResource(@Valid @RequestBody ActivityResourceVO activityVO) {
        boolean save = service.save(ActivityResourceVOTransfer.INSTANCE.toDTO(activityVO));
        return save ? 1 : 0;
    }

    @DeleteMapping("/{id}")
    public Integer deleteActivityResourceByPk(@PathVariable("id") Integer id) {
        return service.delete(id) ? 1 : 0;
    }

    @PutMapping("/{id}")
    public int updateActivityResourceByPk(@PathVariable("id") Integer id, @RequestBody @Valid ActivityResourceVO activityVO) {
        ActivityResourceDTO resourceDTO = ActivityResourceVOTransfer.INSTANCE.toDTO(activityVO);
        resourceDTO.setId(id);
        return service.save(resourceDTO) ? 1 : 0;
    }

    @GetMapping("/permission")
    public Map<String, Boolean> checkUserHulkSre(@RequestParam(value = "user", required = false) String user){
        if (StringUtils.isEmpty(user)) {
            CasUser casUser = UserUtils.getCurrentCasUser();
            user = casUser.getLoginName();
        }
        return ImmutableMap.of("isHulkSre", Arrays.asList(hulkSre).contains(user));
    }

    @GetMapping("/org")
    public List<String> getActivityResourceOrg(@Valid ActivityResourcePageRequest pageRequest){
        String user = pageRequest.getCreateUser();
        if (StringUtils.isEmpty(user)) {
            user = UserUtils.getCurrentCasUser().getLoginName();
        }
        // 管理员可以查看所有信息，非管理员默认查看自身相关的信息
        pageRequest.setCreateUser(Arrays.asList(hulkSre).contains(user) ? null : user);
        List<ActivityResourceDTO> dtoList = service.getActivityResource(pageRequest);
        return dtoList.stream().map(ActivityResourceDTO::getOrgName)
                               .filter(StringUtils::isNotEmpty)
                               .distinct()
                               .sorted()
                               .collect(Collectors.toList());
    }

    @PutMapping("/status")
    public int updateStatusActivityResource(@RequestBody @Valid ActivityResourceChangeVO activityVO){
        ActivityResourceDTO resourceDTO;
        if (Objects.isNull(activityVO.getId()) || (resourceDTO = service.getActivityResourceByPk(activityVO.getId())) == null) {
            throw new SupportErrorException("订单不存在，请确认!");
        }
        if (ResourceStatusType.CLOSE.equals(resourceDTO.getStatus())) {
            throw new SupportErrorException("已交付订单无需被标记完成...");
        }
        if (ResourceStatusType.CLOSE.equals(activityVO.getStatus())
            && !ResourceStatusType.HOLDING.equals(resourceDTO.getStatus())) {
            throw new SupportErrorException("仅待交付状态的订单才可以撤销...");
        }
        // 权限校验
        checkUpdateActivityResourcePermission(resourceDTO.getCreateUser());
        resourceDTO.setStatus(activityVO.getStatus());
        return service.save(resourceDTO) ? 1 : 0;
    }

    @PutMapping("/updateTime")
    public int updateStartTimeActivityResource(@RequestBody @Valid ActivityResourceChangeVO activityVO){
        ActivityResourceDTO resourceDTO;
        if (Objects.isNull(activityVO.getId()) || (resourceDTO = service.getActivityResourceByPk(activityVO.getId())) == null) {
            throw new SupportErrorException("订单不存在，请确认!");
        }
        if (!ResourceStatusType.HOLDING.equals(resourceDTO.getStatus())) {
            throw new SupportErrorException("仅待交付状态的订单才可以操作...");
        }
        checkUpdateActivityResourcePermission(resourceDTO.getCreateUser());
        resourceDTO.setStartTime(activityVO.getStartTime());
        return service.save(resourceDTO) ? 1 : 0;
    }

    private void checkUpdateActivityResourcePermission(String createUser){
        String user = UserUtils.getCurrentCasUser().getLoginName();
        if (!Arrays.asList(hulkSre).contains(createUser)
                && !Objects.equals(createUser, user)){
            throw new SupportErrorException("无操作权限！请联系订单申请人或HULK SRE进行操作...");
        }
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response,
                         @RequestParam(value = "windowPeriodId", required = false) Integer windowPeriodId) {
        List<ActivityResourceDTO> dtoList = service.getActivityResourceByWindowId(windowPeriodId);
        List<ActivityResourceExportVO> exportVOList = ActivityResourceVOTransfer.INSTANCE.toExportVOList(dtoList);
        FileUtils.downloadCsv(response, "activity_resource.csv",
                new String[]{"appkey", "name", "description", "cpu", "memory", "disk", "city", "idcs", "count", "deliverCount", "expectedTime", "deliverTime", "returnTime", "createUser", "status"},
                new String[]{"appkey", "活动名称", "说动说明", "CPU", "内存", "磁盘", "地域", "期望机房", "申请机器数", "已交付机器数", "预期交付时间", "实际交付时间", "资源归还时间", "申请人", "订单状态"}
                , exportVOList);
    }

    @GetMapping("/summary")
    public ResourceSumResult getActivityResourceSum(@RequestParam(value = "windowPeriodId", required = false) Integer windowPeriodId) {
        return service.getActivityResourceSum(windowPeriodId);
    }

}
