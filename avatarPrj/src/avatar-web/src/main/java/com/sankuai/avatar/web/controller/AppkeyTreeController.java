package com.sankuai.avatar.web.controller;

import com.google.common.collect.ImmutableMap;
import com.sankuai.avatar.web.dto.tree.*;
import com.sankuai.avatar.web.dto.user.UserDTO;
import com.sankuai.avatar.web.request.UserBgRequest;
import com.sankuai.avatar.web.service.AppkeyTreeService;
import com.sankuai.avatar.web.service.UserService;
import com.sankuai.avatar.web.transfer.orgtree.UserBgTransfer;
import com.sankuai.avatar.web.transfer.tree.AppkeyTreeOwtTransfer;
import com.sankuai.avatar.web.transfer.tree.AppkeyTreePdlTransfer;
import com.sankuai.avatar.web.transfer.user.UserVOTransfer;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.avatar.web.vo.tree.AppkeyTreeOwtVO;
import com.sankuai.avatar.web.vo.tree.AppkeyTreePdlVO;
import com.sankuai.avatar.web.vo.tree.BgTreeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务树Controller
 *
 * @author zhangxiaoning07
 * @date 2022-10-24
 */
@Slf4j
@RestController
@RequestMapping("/api/v2/avatar/appkeyTree")
public class AppkeyTreeController {

    private final AppkeyTreeService appkeyTreeService;

    private final UserService userService;

    @Autowired
    public AppkeyTreeController(AppkeyTreeService appkeyTreeService, UserService userService) {
        this.appkeyTreeService = appkeyTreeService;
        this.userService = userService;
    }

    /**
     * 查询用户的BG列表接口函数
     *
     * @return 用户的BG列表
     */
    @GetMapping("/user/bg")
    public List<String> getBgs(@Valid UserBgRequest request) {
        String user = request.getUser();
        if (StringUtils.isEmpty(user)) {
            user = UserUtils.getCurrentCasUser().getLoginName();
        }
        if (Boolean.TRUE.equals(request.getAddSrv())) {
            return appkeyTreeService.getBgForAddSrv(user);
        } else {
            return appkeyTreeService.getUserBg(user);
        }
    }

    /**
     * 查询OWT列表接口函数
     *
     * @param bg      BG的名称
     * @param keyword 搜索关键词，可以是OWT的key字段或name字段包含的内容
     * @return OWT对象列表
     */
    @GetMapping("/owt")
    public List<Map<String, String>> getOwts(@RequestParam(value = "user", required = false, defaultValue = "") String user,
                                             @RequestParam(required = false, defaultValue = "") String bg,
                                             @RequestParam(required = false, defaultValue = "") String keyword) {
        if (StringUtils.isEmpty(user)) {
            user = UserUtils.getCurrentCasUser().getLoginName();
        }
        List<AppkeyTreeOwtDTO> owtList = appkeyTreeService.getUserOwtList(user, bg, keyword);
        List<Map<String, String>> list = new ArrayList<>();
        for (AppkeyTreeOwtDTO owtDTO : owtList) {
            list.add(ImmutableMap.of("key", owtDTO.getKey(), "name", owtDTO.getName()));
        }
        return list;
    }

    /**
     * 查询OWT详情接口函数
     *
     * @param key OWT的key, 例如服务运维部是dianping.tbd
     * @return OWT的细节信息对象
     */
    @GetMapping("/owt/{key}")
    public AppkeyTreeOwtVO getOwtByKey(@PathVariable String key,
                                       @RequestParam(value = "user", required = false, defaultValue = "") String user) {
        if (StringUtils.isEmpty(user)) {
            user = UserUtils.getCurrentCasUser().getLoginName();
        }
        AppkeyTreeOwtDTO owtDTO = appkeyTreeService.getOwtByKey(key, user);
        AppkeyTreeOwtVO owtVO = AppkeyTreeOwtTransfer.INSTANCE.toVO(owtDTO);
        if (Objects.nonNull(owtDTO)) {
            owtVO.setRdAdmin(UserVOTransfer.INSTANCE.toDxUserVOList(userService.queryUserByMis(owtDTO.getRdAdmin())));
            owtVO.setOpAdmin(UserVOTransfer.INSTANCE.toDxUserVOList(userService.queryUserByMis(owtDTO.getOpAdmin())));
            owtVO.setEpAdmin(UserVOTransfer.INSTANCE.toDxUserVOList(userService.queryUserByMis(owtDTO.getEpAdmin())));
        }
        return owtVO;
    }

    /**
     * 查询OWT下的PDL列表接口函数
     *
     * @param owtKey OWT的key, 例如服务运维部是dianping.tbd
     * @return PDL对象列表
     */
    @GetMapping("/owt/{owtKey}/pdl")
    public List<AppkeyTreePdlVO> getPdlList(@PathVariable String owtKey,
                                            @RequestParam(required = false, defaultValue = "") String keyword) {
        List<AppkeyTreePdlDTO> pdlList = appkeyTreeService.getPdlListByOwtKey(owtKey, keyword);
        List<AppkeyTreePdlVO> voList = new ArrayList<>();
        Set<String> users = new HashSet<>();
        for (AppkeyTreePdlDTO pdlDTO : pdlList) {
            users.addAll(pdlDTO.getRdAdmin());
            users.addAll(pdlDTO.getOpAdmin());
            users.addAll(pdlDTO.getEpAdmin());
        }
        List<UserDTO> userDTOList = userService.queryUserByMisNoOrder(new ArrayList<>(users));
        Map<String, UserDTO> userDTOMap = userDTOList.stream().collect(Collectors.toMap(UserDTO::getMis, userDTO -> userDTO));
        for (AppkeyTreePdlDTO pdlDTO : pdlList) {
            AppkeyTreePdlVO pdlVO = AppkeyTreePdlTransfer.INSTANCE.toVO(pdlDTO);
            pdlVO.setRdAdmin(UserVOTransfer.INSTANCE.toDxUserVOList(getAdmin(pdlDTO.getRdAdmin(), userDTOMap)));
            pdlVO.setOpAdmin(UserVOTransfer.INSTANCE.toDxUserVOList(getAdmin(pdlDTO.getOpAdmin(), userDTOMap)));
            pdlVO.setEpAdmin(UserVOTransfer.INSTANCE.toDxUserVOList(getAdmin(pdlDTO.getEpAdmin(), userDTOMap)));
            voList.add(pdlVO);
        }
        return voList;
    }
    private List<UserDTO> getAdmin(List<String> users, Map<String, UserDTO> userDTOMap){
        return users.stream().map(user -> userDTOMap.getOrDefault(user, null)).collect(Collectors.toList());
    }

    /**
     * 查询PDL详情接口函数
     *
     * @param key PDL的key字段，如变更管理工具是dianping.tbd.change
     * @return PDL的细节信息对象
     */
    @GetMapping("/pdl/{key}")
    public AppkeyTreePdlVO getPdlByKey(@PathVariable String key,
                                       @RequestParam(value = "user", required = false, defaultValue = "") String user) {
        if (StringUtils.isEmpty(user)) {
            user = UserUtils.getCurrentCasUser().getLoginName();
        }
        AppkeyTreePdlDTO pdlDTO = appkeyTreeService.getPdlByKey(key, user);
        AppkeyTreePdlVO pdlVO = AppkeyTreePdlTransfer.INSTANCE.toVO(pdlDTO);
        if (Objects.nonNull(pdlDTO)) {
            pdlVO.setRdAdmin(UserVOTransfer.INSTANCE.toDxUserVOList(userService.queryUserByMis(pdlDTO.getRdAdmin())));
            pdlVO.setOpAdmin(UserVOTransfer.INSTANCE.toDxUserVOList(userService.queryUserByMis(pdlDTO.getOpAdmin())));
            pdlVO.setEpAdmin(UserVOTransfer.INSTANCE.toDxUserVOList(userService.queryUserByMis(pdlDTO.getEpAdmin())));
            AppkeyTreeOwtVO owtVO = AppkeyTreeOwtTransfer.INSTANCE.toVO(pdlDTO.getOwt());
            if (Objects.nonNull(pdlDTO.getOwt())) {
                owtVO.setRdAdmin(UserVOTransfer.INSTANCE.toDxUserVOList(userService.queryUserByMis(pdlDTO.getOwt().getRdAdmin())));
                owtVO.setOpAdmin(UserVOTransfer.INSTANCE.toDxUserVOList(userService.queryUserByMis(pdlDTO.getOwt().getOpAdmin())));
                owtVO.setEpAdmin(UserVOTransfer.INSTANCE.toDxUserVOList(userService.queryUserByMis(pdlDTO.getOwt().getEpAdmin())));
            }
            pdlVO.setOwt(owtVO);
        }
        return pdlVO;
    }

    /**
     * 查询服务树的接口函数
     *
     * @return 用户的服务树
     */
    @GetMapping("/user/tree")
    public List<BgTreeVO> getBgOwtPdlTree(@RequestParam(value = "user", required = false, defaultValue = "") String user,
                                          @RequestParam(value = "query", required = false, defaultValue = "") String query,
                                          @RequestParam(value = "mine", required = false, defaultValue = "true") Boolean mine) {
        List<UserBgDTO> dtoList;
        if (StringUtils.isEmpty(user)) {
            user = UserUtils.getCurrentCasUser().getLoginName();
        }
        // 仅与我相关
        if (Boolean.TRUE.equals(mine)) {
            dtoList = appkeyTreeService.getUserBgTree(user);
            if (CollectionUtils.isEmpty(dtoList)) {
                dtoList = appkeyTreeService.getBgTree(user, true);
            }
        } else {
            dtoList = appkeyTreeService.getBgTree(user, false);
        }
        List<UserBgDTO> result = new ArrayList<>(dtoList);
        // 支持 bg、owt、pdl 搜索
        if (StringUtils.isNotEmpty(query)) {
            result = new ArrayList<>();
            for (UserBgDTO userBgDTO : dtoList) {
                if (userBgDTO.getBgName().contains(query)) {
                    result.add(userBgDTO);
                } else {
                    for (UserOwtDTO owtDTO : userBgDTO.getOwtList()) {
                        if (owtDTO.getOwt().contains(query) || owtDTO.getOwtName().contains(query)) {
                            result.add(userBgDTO);
                            break;
                        } else {
                            boolean find = false;
                            for (UserPdlDTO pdlDTO : owtDTO.getPdlList()) {
                                if (pdlDTO.getPdl().contains(query) || pdlDTO.getPdlName().contains(query)) {
                                    result.add(userBgDTO);
                                    find = true;
                                    break;
                                }
                            }
                            if (find) {break;}
                        }
                    }
                }

            }
        }
        return UserBgTransfer.INSTANCE.toBgTreeVOList(result);
    }

}
