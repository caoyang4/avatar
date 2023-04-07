package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.emergency.EmergencySreDTO;
import com.sankuai.avatar.web.request.EmergencySrePageRequest;
import com.sankuai.avatar.web.service.EmergencySreService;
import com.sankuai.avatar.web.transfer.emergency.EmergencySreVOTransfer;
import com.sankuai.avatar.web.vo.emergency.EmergencySreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-02-01 19:51
 */
@Validated
@RestController
@RequestMapping("/api/v2/avatar/emergencySre")
public class EmergencySreController {

    private final EmergencySreService service;

    @Autowired
    public EmergencySreController(EmergencySreService service) {
        this.service = service;
    }

    @GetMapping("")
    PageResponse<EmergencySreVO> getPageEmergencySre(EmergencySrePageRequest pageRequest){
        PageResponse<EmergencySreVO> pageResponse = new PageResponse<>();
        PageResponse<EmergencySreDTO> dtoPageResponse = service.getPageEmergencySre(pageRequest);
        pageResponse.setPage(pageRequest.getPage());
        pageResponse.setPageSize(pageRequest.getPageSize());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setItems(EmergencySreVOTransfer.INSTANCE.toVOList(dtoPageResponse.getItems()));
        return pageResponse;
    }

    @GetMapping("/{id}")
    EmergencySreVO getEmergencySreByPk(@PathVariable("id") Integer id){
        return EmergencySreVOTransfer.INSTANCE.toVO(service.getEmergencySreByPk(id));
    }

    @PostMapping("")
    public Boolean saveEmergencySreAdmin(@Valid @RequestBody EmergencySreVO emergencySreVO){
        return service.saveEmergencySre(EmergencySreVOTransfer.INSTANCE.toDTO(emergencySreVO));
    }

    @PutMapping("/{id}")
    public Boolean updateEmergencySreAdminByPk(@PathVariable("id") Integer id, @RequestBody @Valid EmergencySreVO emergencySreVO){
        EmergencySreDTO emergencySreDTO = service.getEmergencySreByPk(id);
        if (Objects.isNull(emergencySreDTO)) {
            return false;
        }
        return service.saveEmergencySre(EmergencySreVOTransfer.INSTANCE.toDTO(emergencySreVO));
    }

    @DeleteMapping("/{id}")
    public Boolean deleteEmergencySreAdminByPk(@PathVariable("id") Integer id) {
        return service.deleteEmergencySreByPk(id);
    }

}
