package com.sankuai.avatar.web.controller;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.utils.PageHelperUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dal.entity.CasUser;
import com.sankuai.avatar.web.dto.activity.WindowPeriodResourceDTO;
import com.sankuai.avatar.web.request.WindowPeriodPageRequest;
import com.sankuai.avatar.web.service.WindowPeriodResourceService;
import com.sankuai.avatar.web.transfer.activity.WindowPeriodResourceVOTransfer;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.avatar.web.vo.activity.WindowPeriodHitVO;
import com.sankuai.avatar.web.vo.activity.WindowPeriodResourceVO;
import com.sankuai.avatar.web.vo.activity.WindowPermissionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-03-15 19:39
 */
@Validated
@RestController
@RequestMapping("/api/v2/avatar/window_period")
public class ResourceWindowPeriodController {

    @MdpConfig("HULK_SRE:[\"qinwei05\",\"jie.li.sh\",\"zhaozhifan02\"]")
    private String[] hulkSre;

    private final WindowPeriodResourceService service;

    @Autowired
    public ResourceWindowPeriodController(WindowPeriodResourceService service) {
        this.service = service;
    }

    @GetMapping("")
    PageResponse<WindowPeriodResourceVO> getPageResourceWindowPeriod(WindowPeriodPageRequest pageRequest){
        PageResponse<WindowPeriodResourceDTO> dtoPageResponse = service.queryPage(pageRequest);
        return PageHelperUtils.convertPageResponse(dtoPageResponse,
                dtoList -> WindowPeriodResourceVOTransfer.INSTANCE.toVOList(dtoPageResponse.getItems()));
    }

    @PostMapping("")
    public Integer saveResourceWindowPeriod(@Valid @RequestBody WindowPeriodResourceVO resourceVO) {
        Boolean save = service.save(WindowPeriodResourceVOTransfer.INSTANCE.toDTO(resourceVO));
        return Boolean.TRUE.equals(save) ? 1 : 0;
    }

    @GetMapping("/{id}")
    public WindowPeriodResourceVO getResourceWindowPeriodByPk(@PathVariable("id") Integer id){
        return WindowPeriodResourceVOTransfer.INSTANCE.toVO(service.getWindowPeriodByPk(id));
    }

    @GetMapping("/recent")
    public WindowPeriodResourceVO getRecentResourceWindowPeriod(){
        int id = service.getMaxWindowId();
        return WindowPeriodResourceVOTransfer.INSTANCE.toVO(service.getWindowPeriodByPk(id));
    }


    @DeleteMapping("/{id}")
    public Integer deleteResourceWindowPeriodByPk(@PathVariable("id") Integer id) {
        Boolean delete = service.deleteByPk(id);
        return Boolean.TRUE.equals(delete) ? 1 : 0;
    }

    @PutMapping("/{id}")
    public int updateResourceWindowPeriodByPk(@PathVariable("id") Integer id, @RequestBody @Valid WindowPeriodResourceVO resourceVO) {
        WindowPeriodResourceDTO resourceDTO = WindowPeriodResourceVOTransfer.INSTANCE.toDTO(resourceVO);
        resourceDTO.setId(id);
        Boolean update = service.save(resourceDTO);
        return Boolean.TRUE.equals(update) ? 1 : 0;
    }

    @GetMapping("/hit")
    public WindowPeriodHitVO hitResourceWindowPeriod(@RequestParam(value = "period", required = false, defaultValue = "") Integer pk) {
        WindowPeriodResourceDTO hitDTO = service.getHitWindowPeriod(pk);
        WindowPeriodHitVO hitVO = new WindowPeriodHitVO();
        if (Objects.nonNull(hitDTO)) {
            hitVO = WindowPeriodResourceVOTransfer.INSTANCE.toHitVO(hitDTO);
            hitVO.setDescription(String.format("当前资源窗口期为：%s", hitVO.getPeriod()));
        } else {
            hitVO.setHit(false);
            hitVO.setDescription("当前不在资源窗口期");
            hitVO.setWindowPeriodId(0);
            hitVO.setName("");
            hitVO.setPeriod("");
        }
        return hitVO;
    }

    @GetMapping("/permission")
    public WindowPermissionVO permission(@RequestParam(value = "mis", required = false, defaultValue = "") String mis) {
        WindowPermissionVO permissionVO = new WindowPermissionVO();
        if (StringUtils.isBlank(mis)){
            CasUser user = UserUtils.getCurrentCasUser();
            mis = !Objects.isNull(user) ? user.getLoginName() : "";
        }
        Boolean permission = Arrays.asList(hulkSre).contains(mis);
        permissionVO.setPermission(permission);
        permissionVO.setMis(mis);
        permissionVO.setMsg(Boolean.TRUE.equals(permission) ? "您有权限操作资源窗口期管理" : "您无权限操作资源窗口期管理");
        return permissionVO;
    }
}
