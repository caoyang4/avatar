package com.sankuai.avatar.client.org.impl;

import com.dianping.cat.Cat;
import com.sankuai.avatar.client.org.OrgClient;
import com.sankuai.avatar.client.org.model.Org;
import com.sankuai.avatar.client.org.model.OrgUser;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.meituan.org.opensdk.model.domain.Emp;
import com.sankuai.meituan.org.opensdk.service.EmpService;
import com.sankuai.meituan.org.opensdk.service.OrgService;
import com.sankuai.meituan.org.queryservice.domain.param.DataScope;
import com.sankuai.meituan.org.queryservice.exception.MDMThriftException;
import com.sankuai.meituan.org.queryservice.utils.requestcontext.OrgRequestContext;
import com.sankuai.meituan.org.queryservice.utils.requestcontext.RequestHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * OrgClient 实现类
 * @author caoyang
 * @create 2022-10-25 15:43
 */
@Component
@Slf4j
public class OrgClientImpl implements OrgClient {

    private final EmpService empService;
    private final OrgService orgService;

    private static final String ORG_SOURCE = "ORG";
    private static final String MAOYAN_SOURCE = "MY";

    @Autowired
    public OrgClientImpl(EmpService empService,
                         OrgService orgService){
        this.empService = empService;
        this.orgService = orgService;
    }

    /**
     * 0,1,2,100046,150042,1573,150044,1021866 组织链取 1021866
     * @param orgId orgId
     * @return orgId 组织链末端id
     */
    private String formatOrgId(String orgId){
        final String splitDot = ",";
        if (orgId.contains(splitDot)) {
            return orgId.substring(orgId.lastIndexOf(splitDot)+1);
        }
        return orgId;
    }

    /**
     * 补齐OrgUser信息
     * @param orgUser orgUser
     * @param emp 员工对象
     */
    private void polishOrgUser(OrgUser orgUser, Emp emp){
        orgUser.setOrg(getOrgByOrgId(formatOrgId(emp.getOrgId())));
        orgUser.setName(emp.getName());
        orgUser.setJobStatus(emp.getJobStatus());
        orgUser.setLeader(emp.getReportEmpMis());
    }

    /**
     * org 源获取数据
     * @param mis 用户 id
     * @return OrgUser
     */
    private OrgUser getOrgUserByOrg(String mis) throws SdkBusinessErrorException{
        OrgUser orgUser = new OrgUser();
        orgUser.setMis(mis);
        try {
            Emp orgEmp = empService.queryByMis(mis, null);
            if (Objects.nonNull(orgEmp) && StringUtils.isNotEmpty(orgEmp.getOrgId()) && StringUtils.isNotEmpty(orgEmp.getName())) {
                polishOrgUser(orgUser, orgEmp);
                orgUser.setSource(ORG_SOURCE);
                return orgUser;
            }
            return null;
        } catch (MDMThriftException e) {
            throw new SdkBusinessErrorException("org源获取用户信息异常：" + e.getMessage());
        }
    }

    /**
     * 猫眼源获取数据
     * @param mis mis
     * @return OrgUser
     */
    private OrgUser getOrgUserByMaoyan(String mis) throws SdkBusinessErrorException{
        OrgUser orgUser = new OrgUser();
        orgUser.setMis(mis);
        try {
            OrgRequestContext.setRequestHeader(RequestHeader.of().setDataScope(DataScope.of().setTenantId(2).setSources(MAOYAN_SOURCE)));
            Emp myEmp = empService.queryByMis(mis, null);
            if (Objects.nonNull(myEmp) && StringUtils.isNotEmpty(myEmp.getOrgId()) && StringUtils.isNotEmpty(myEmp.getName())) {
                OrgRequestContext.setRequestHeader(RequestHeader.of().setDataScope(DataScope.of().setTenantId(2).setSources(MAOYAN_SOURCE)));
                polishOrgUser(orgUser, myEmp);
                orgUser.setSource(MAOYAN_SOURCE);
                return orgUser;
            }
        } catch (MDMThriftException e) {
            Cat.logError(e);
            throw new SdkBusinessErrorException("猫眼源获取用户信息异常：" + e.getMessage());
        }

        return null;
    }

    @Override
    @RaptorTransaction
    public OrgUser getOrgUserByMis(String mis) throws SdkBusinessErrorException{
        OrgUser orgUserByOrg = getOrgUserByOrg(mis);
        if (Objects.nonNull(orgUserByOrg)) {
            return orgUserByOrg;
        }
        OrgUser orgUserByMaoyan = getOrgUserByMaoyan(mis);
        if (Objects.nonNull(orgUserByMaoyan)) {
            return orgUserByMaoyan;
        }
        return null;
    }

    @Override
    @RaptorTransaction
    public Org getOrgByOrgId(String orgId) throws SdkBusinessErrorException{
        try {
            com.sankuai.meituan.org.opensdk.model.domain.Org sdkOrg = orgService.query(orgId, null);
            if (Objects.isNull(sdkOrg)) {
                OrgRequestContext.setRequestHeader(RequestHeader.of().setDataScope(DataScope.of().setTenantId(2).setSources("MY")));
                sdkOrg = orgService.query(orgId, null);
            }
            if (Objects.isNull(sdkOrg)) {return null;}
            Org org = new Org();
            org.setOrgId(sdkOrg.getOrgId());
            org.setOrgName(sdkOrg.getName());
            org.setOrgPath(sdkOrg.getOrgPath());
            org.setOrgNamePath(sdkOrg.getOrgNamePath());
            org.setBgId(sdkOrg.getBgId());
            org.setBgName(sdkOrg.getBgName());
            org.setStatus(sdkOrg.getStatus());
            return org;
        } catch (MDMThriftException e) {
            Cat.logError(e);
            throw new SdkBusinessErrorException("获取org对象异常，orgId为" + orgId + "，异常信息："+ e.getMessage());
        }
    }
}
