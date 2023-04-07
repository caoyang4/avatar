package com.sankuai.avatar.web.crane;

import com.cip.crane.client.spring.annotation.Crane;
import com.cip.crane.client.spring.annotation.CraneConfiguration;
import com.dianping.cat.Cat;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.user.UserResource;
import com.sankuai.avatar.resource.user.bo.UserBO;
import com.sankuai.avatar.resource.user.request.UserRequestBO;
import com.sankuai.avatar.web.exception.CraneTaskException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author caoyang
 * @create 2022-11-08 20:30
 */

@CraneConfiguration
@Component
@Slf4j
public class CraneDxOrgUser {

    private final UserResource userResource;

    @Autowired
    public CraneDxOrgUser(UserResource userResource) {
        this.userResource = userResource;
    }

    private static final int BATCH_MULTI_SET_SIZE = 200;

    /**
     * 获取所有用户信息
     *
     * @return {@link List}<{@link UserBO}>
     */
    private List<UserBO> getAllUserBOs(){
        int page = 1;
        UserRequestBO requestBO = UserRequestBO.builder().build();
        requestBO.setPage(page++);
        requestBO.setPageSize(BATCH_MULTI_SET_SIZE);
        PageResponse<UserBO> pageResponse = userResource.queryPage(requestBO);
        List<UserBO> userBOList = new ArrayList<>();
        while (Objects.nonNull(pageResponse) && CollectionUtils.isNotEmpty(pageResponse.getItems())){
            userBOList.addAll(pageResponse.getItems());
            requestBO.setPage(page++);
            pageResponse = userResource.queryPage(requestBO);
        }
        return userBOList;
    }


    /**
     * 补充用户dx、org 等信息，清理离职人员信息
     */
    @Crane("com.sankuai.avatar.web.supplementUserDxOrg")
    public void supplementUser(){
        final String dismissStatus = "离职";
        List<UserBO> userBOList = getAllUserBOs();
        List<String> dismissUserList = new ArrayList<>();
        AtomicInteger update = new AtomicInteger();
        final String underLine = "_";
        try {
            userBOList.forEach(
                userBO -> {
                    String mis = userBO.getMis();
                    if (StringUtils.isEmpty(mis)) {return;}
                    try {
                        UserBO userByOrgDx = userResource.getUserByOrgDx(mis);
                        if (dismissStatus.equals(userByOrgDx.getJobStatus())
                                && !mis.contains(underLine)
                                && !Objects.equals("DX", userByOrgDx.getSource())) {
                            dismissUserList.add(userByOrgDx.getMis());
                        } else {
                            userBO.setName(ObjectUtils.null2Empty(userByOrgDx.getName()));
                            userBO.setOrgPath(ObjectUtils.null2Empty(userByOrgDx.getOrgPath()));
                            userBO.setOrganization(ObjectUtils.null2Empty(userByOrgDx.getOrganization()));
                            userBO.setLeader(ObjectUtils.null2Empty(userByOrgDx.getLeader()));
                            userBO.setUserImage(ObjectUtils.null2Empty(userByOrgDx.getUserImage()));
                            userBO.setOrgId(ObjectUtils.null2Empty(userByOrgDx.getOrgId()));
                            if (StringUtils.isNotEmpty(userByOrgDx.getSource())) {
                                userBO.setSource(userByOrgDx.getSource());
                            }
                            userResource.save(userBO);
                            update.getAndIncrement();
                        }
                    } catch (SdkBusinessErrorException ignored){
                    }
                });
            log.info("成功补齐头像、直属leader、org信息{}条", update.intValue());
            if (CollectionUtils.isNotEmpty(dismissUserList)) {
                for (int i = 0; i * BATCH_MULTI_SET_SIZE < dismissUserList.size(); i++) {
                    int from = i * BATCH_MULTI_SET_SIZE;
                    int to = Math.min((i + 1) * BATCH_MULTI_SET_SIZE, dismissUserList.size());
                    userResource.deleteByCondition(UserRequestBO.builder().misList(dismissUserList.subList(from, to)).build());
                }
                log.info("成功清理离职人员信息{}条", dismissUserList.size());
            }
        } catch (Exception e){
            Cat.logError(e);
            throw new CraneTaskException("crane 任务 [supplementUserDxOrg] 执行异常：" + e.getMessage());
        }
    }

    /**
     * 缓存用户信息
     */
    @Crane("com.sankuai.avatar.web.cacheUserDxOrg")
    public void cacheUser(){
        try {
            List<UserBO> userBOList = getAllUserBOs();
            for (int i = 0; i * BATCH_MULTI_SET_SIZE < userBOList.size(); i++) {
                int from = i * BATCH_MULTI_SET_SIZE;
                int to = Math.min((i + 1) * BATCH_MULTI_SET_SIZE, userBOList.size());
                List<UserBO> boList = userBOList.subList(from, to);
                for (UserBO userBO : boList) {
                    try {
                        UserBO userByOrgDx = userResource.getUserByOrgDx(userBO.getMis());
                        if (Objects.nonNull(userByOrgDx)) {
                            userBO.setJobStatus(userByOrgDx.getJobStatus());
                        }
                    } catch (SdkBusinessErrorException ignored) {}
                }
                userResource.cacheUserBO(boList);
                log.info("成功缓存人员数量：{}", boList.size());
            }
            log.info("人员信息缓存执行成功");
        } catch (Exception e){
            Cat.logError(e);
            throw new CraneTaskException("crane 任务 [cacheUserDxOrg] 执行异常：" + e.getMessage());
        }
    }

}
