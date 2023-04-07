package com.sankuai.avatar.web.crane;

import com.cip.crane.client.spring.annotation.Crane;
import com.cip.crane.client.spring.annotation.CraneConfiguration;
import com.meituan.mdp.boot.starter.threadpool.NamedRunnableTask;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.NamedThreadFactory;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.tree.AppkeyTreeResource;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreePdlBO;
import com.sankuai.avatar.resource.tree.bo.UserBgBO;
import com.sankuai.avatar.resource.user.UserResource;
import com.sankuai.avatar.resource.user.bo.UserBO;
import com.sankuai.avatar.resource.user.request.UserRequestBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2023-02-06 11:51
 */

@CraneConfiguration
@Component
@Slf4j
public class CraneAppkeyTree {

    private final UserResource userResource;
    private final AppkeyTreeResource treeResource;

    @Autowired
    public CraneAppkeyTree(AppkeyTreeResource treeResource,
                           UserResource userResource) {
        this.treeResource = treeResource;
        this.userResource = userResource;
    }

    private static final int BATCH_MULTI_SET_SIZE = 200;

    private final ExecutorService executor =  Executors.newFixedThreadPool(25, new NamedThreadFactory("AppkeyTreePool"));


    /**
     * 获取所有用户信息
     *
     * @return {@link List}<{@link UserBO}>
     */
    private List<String> getAllValidUser(){
        int page = 1;
        UserRequestBO requestBO = UserRequestBO.builder().build();
        requestBO.setPage(page++);
        requestBO.setPageSize(BATCH_MULTI_SET_SIZE);
        PageResponse<UserBO> pageResponse = userResource.queryPage(requestBO);
        Set<String> userList = new HashSet<>();
        while (Objects.nonNull(pageResponse) && CollectionUtils.isNotEmpty(pageResponse.getItems())){
            Set<String> list = pageResponse.getItems().stream()
                    .map(UserBO::getMis)
                    .filter(StringUtils::isNotEmpty)
                    .collect(Collectors.toSet());
            userList.addAll(list);
            requestBO.setPage(page++);
            pageResponse = userResource.queryPage(requestBO);
        }
        return new ArrayList<>(new HashSet<>(userList));
    }

    @Crane("com.sankuai.avatar.web.cacheUserBg")
    public void cacheUserBg(){
        List<String> userList = getAllValidUser();
        CountDownLatch latch = new CountDownLatch(userList.size());
        AtomicInteger count = new AtomicInteger(0);
        for (String user : userList) {
            executor.submit(new NamedRunnableTask("cacheUserBg", () -> {
                try {
                    List<String> bgList = treeResource.getUserBgNoCache(user);
                    treeResource.cacheUserBg(user, bgList);
                    count.getAndIncrement();
                } catch (SdkCallException | SdkBusinessErrorException e) {
                    log.error("缓存{}的bg列表失败:{}", user,e.getMessage());
                } finally {
                    latch.countDown();
                }
            }));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("成功缓存{}个人员的bg列表", count.get());
    }

    @Crane("com.sankuai.avatar.web.cacheUserBgTree")
    public void cacheUserBgTree(){
        List<String> userList = getAllValidUser();
        AtomicInteger count = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(userList.size());
        for (String user : userList) {
            executor.submit(new NamedRunnableTask("cacheUserBg", () -> {
                    try {
                        List<UserBgBO> userBgBOList = treeResource.getUserBgTreeNoCache(user);
                        treeResource.cacheUserBgTree(user, userBgBOList);
                        count.getAndIncrement();
                    } catch (SdkCallException | SdkBusinessErrorException e) {
                        log.error("缓存{}的服务树失败:{}", user, e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                }
            ));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("成功缓存{}个人员的服务树信息", count.get());
    }

    @Crane("com.sankuai.avatar.web.cachePdlSrvCount")
    public void cachePdlSrvCount(){
        List<AppkeyTreePdlBO> pdlList = treeResource.getPdlList();
        for (AppkeyTreePdlBO pdlBO : pdlList) {
            if (StringUtils.isEmpty(pdlBO.getKey())) {
                continue;
            }
            Integer srvCount = treeResource.getPdlSrvCountNoCache(pdlBO.getKey());
            if (Objects.nonNull(srvCount)) {
                treeResource.cachePdlSrvCount(pdlBO.getKey(), srvCount);
            }
        }
    }

}
