package com.sankuai.avatar.web.crane;

import com.cip.crane.client.spring.annotation.Crane;
import com.cip.crane.client.spring.annotation.CraneConfiguration;
import com.dianping.cat.Cat;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.dxMessage.DxMessageResource;
import com.sankuai.avatar.resource.orgRole.OrgRoleAdminResource;
import com.sankuai.avatar.resource.orgRole.bo.OrgBO;
import com.sankuai.avatar.resource.orgRole.bo.OrgRoleAdminBO;
import com.sankuai.avatar.resource.orgRole.request.OrgRoleAdminRequestBO;
import com.sankuai.avatar.resource.user.UserResource;
import com.sankuai.avatar.resource.user.bo.UserBO;
import com.sankuai.avatar.web.exception.CraneTaskException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * @Author: Wellqin
 * @Date: 2020/7/14 16:38
 */

@CraneConfiguration
@Component
@Slf4j
public class CraneOrgRoleAdmin {

    private final OrgRoleAdminResource orgRoleAdminResource;
    private final UserResource userResource;
    private final DxMessageResource dxMessageResource;

    @Autowired
    public CraneOrgRoleAdmin(OrgRoleAdminResource orgRoleAdminResource,
                             UserResource userResource,
                             DxMessageResource dxMessageResource) {
        this.orgRoleAdminResource = orgRoleAdminResource;
        this.userResource = userResource;
        this.dxMessageResource = dxMessageResource;
    }

    private static final int BATCH_MULTI_SET_SIZE = 200;

    /**
     * 获取所有组织角色
     *
     * @return {@link List}<{@link OrgRoleAdminBO}>
     */
    private List<OrgRoleAdminBO> getAllOrgRoles(){
        int page = 1;
        OrgRoleAdminRequestBO requestBO = OrgRoleAdminRequestBO.builder().build();
        requestBO.setPage(page++);
        requestBO.setPageSize(BATCH_MULTI_SET_SIZE);
        PageResponse<OrgRoleAdminBO> pageResponse = orgRoleAdminResource.queryPage(requestBO);
        List<OrgRoleAdminBO> orgRoleAdminBOList = new ArrayList<>();
        while (Objects.nonNull(pageResponse) && CollectionUtils.isNotEmpty(pageResponse.getItems())){
            orgRoleAdminBOList.addAll(pageResponse.getItems());
            requestBO.setPage(page++);
            pageResponse = orgRoleAdminResource.queryPage(requestBO);
        }
        return orgRoleAdminBOList;
    }

    /**
     * 清理离职人员，获取有效运维人员
     *
     * @return {@link List}<{@link String}>
     */
    private List<String> getValidOpRoles(List<String> opRoles){
        List<String> roles = new ArrayList<>();
        for (String opRole : opRoles) {
            if (StringUtils.isEmpty(opRole)) {continue;}
            try {
                UserBO userByOrgDx = userResource.getUserByOrgDx(opRole);
                final String dismissStatus = "离职";
                if (Objects.isNull(userByOrgDx) || dismissStatus.equals(userByOrgDx.getJobStatus())) {
                    continue;
                }
            } catch (SdkBusinessErrorException ignored){continue;}
            roles.add(opRole);
        }
        return roles;
    }

    /**
     * 发送大象消息
     *
     * @param mis     用户 mis
     * @param orgName 组织名字
     */
    private void sendDxMessage(String mis, String orgName) {
        String text = "【组织架构运维负责人配置通知】\n"
                + "【现状】%s存在运维负责人离职,当前组织架构下无运维负责人\n"
                + "【详情】Avatar将默认您作为新的运维负责人, 如需更改请前往【Avatar-部门】对应的组织架构进行运维负责人配置";
        String sendText = String.format(text, orgName);
        try {
            dxMessageResource.pushDxMessage(Collections.singletonList(mis), sendText);
        } catch (SdkBusinessErrorException ignored) {}
    }

    /**
     * 校正部门及角色信息
     */
    @Crane("com.sankuai.avatar.web.correctOrgRoleUser")
    public void correctOrgRoleUser(){
        List<OrgRoleAdminBO> allOrgRoles = getAllOrgRoles();
        try {
            for (OrgRoleAdminBO orgRole : allOrgRoles) {
                if (StringUtils.isEmpty(orgRole.getOrgId())) {
                    continue;
                }
                List<String> opRoles = Arrays.asList(orgRole.getRoleUsers().split(","));
                List<String> validOpRoles = getValidOpRoles(opRoles);
                // 更新SRE负责人信息，对于离职人员，若清理后其ORG的SRE为空，则补充Leader为SRE并发送通知
                if (CollectionUtils.isNotEmpty(opRoles)) {
                    if (!CollectionUtils.isEmpty(validOpRoles) && opRoles.size() > validOpRoles.size()) {
                        orgRole.setRoleUsers(String.join(",", validOpRoles));
                        log.info("Org存在SRE离职变更: " + opRoles + "-->" + validOpRoles);
                    } else if (CollectionUtils.isEmpty(validOpRoles)) {
                        UserBO userBO = userResource.getUserByOrgDx(opRoles.get(0));
                        if (Objects.nonNull(userBO) && StringUtils.isNotEmpty(userBO.getLeader())) {
                            orgRole.setRoleUsers(userBO.getLeader());
                            log.info("Org存在SRE离职变更, 同时目前无SRE, 将选取离职SRE的上级将变更为新的SRE: " + opRoles + "-->" + userBO.getLeader());
                            sendDxMessage(userBO.getLeader(), orgRole.getOrgName());
                            log.info("SRE离职变更, 通知Leader:{} 消息发送成功", userBO.getLeader());
                        }
                    }
                }
                // 从客户端获取 org 信息进行更新
                try {
                    OrgBO orgBO = orgRoleAdminResource.getOrgByOrgClient(orgRole.getOrgId());
                    if (Objects.nonNull(orgBO)) {
                        if (StringUtils.isNotEmpty(orgBO.getOrgNamePath())) {
                            orgRole.setOrgName(orgBO.getOrgNamePath());
                        }
                        if (StringUtils.isNotEmpty(orgBO.getOrgPath())) {
                            orgRole.setOrgPath(orgBO.getOrgPath());
                        }
                    } else {
                        log.error("orgId:{} 无效", orgRole.getOrgId());
                    }
                    orgRoleAdminResource.save(orgRole);
                } catch (SdkBusinessErrorException ignored){}
            }
        } catch (Exception e){
            Cat.logError(e);
            throw new CraneTaskException("crane 任务 [correctOrgRoleUser] 执行异常：" + e.getMessage());
        }
    }


    /**
     * 清理无效org
     */
    @Crane("com.sankuai.avatar.web.deleteInvalidOrg")
    public void deleteInValidOrg() {
        List<OrgRoleAdminBO> allOrgRoles = getAllOrgRoles();
        List<Integer> validIds = new ArrayList<>();
        try {
            for (OrgRoleAdminBO orgRole : allOrgRoles) {
                String orgId = orgRole.getOrgId();
                if (StringUtils.isEmpty(orgId)) {
                    validIds.add(orgRole.getId());
                }
                try {
                    OrgBO orgBO = orgRoleAdminResource.getOrgByOrgClient(orgId);
                    if (Objects.nonNull(orgBO) && Objects.equals(0, orgBO.getStatus())) {
                        validIds.add(orgRole.getId());
                        log.info("组织架构{}已经失效", orgId);
                    }
                } catch (SdkBusinessErrorException ignored) {}
            }
            if (CollectionUtils.isNotEmpty(validIds) && validIds.size() < 10) {
                orgRoleAdminResource.deleteByCondition(OrgRoleAdminRequestBO.builder().ids(validIds).build());
                log.info("成功清理{}条失效 orgRoleAdmin 记录", validIds.size());
            } else {
                log.info("无失效 org 部门信息，无需清理");
            }
        } catch (Exception e){
            Cat.logError(e);
            throw new CraneTaskException("crane 任务 [deleteInValidOrg] 执行异常：" + e.getMessage());
        }
    }

    /**
     * 缓存组织角色管理
     */
    @Crane("com.sankuai.avatar.web.cacheOrgRoleAdmin")
    public void cacheOrgRoleAdmin(){
        try {
            List<OrgRoleAdminBO> allOrgRoles = getAllOrgRoles();
            for (int i = 0; i * BATCH_MULTI_SET_SIZE < allOrgRoles.size(); i++) {
                int from = i * BATCH_MULTI_SET_SIZE;
                int to = Math.min((i + 1) * BATCH_MULTI_SET_SIZE, allOrgRoles.size());
                boolean result = orgRoleAdminResource.cacheOrgRoleAdminBO(allOrgRoles.subList(from, to));
                if (result) {
                    log.info("crane 任务 [cacheOrgRoleAdmin] 执行成功");
                }
            }
        } catch (Exception e){
            Cat.logError(e);
            throw new CraneTaskException("crane 任务 [cacheOrgRoleAdmin] 执行异常：" + e.getMessage());
        }
    }

}
