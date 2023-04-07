package com.sankuai.avatar.web.service.impl;

import com.dianping.squirrel.client.StoreKey;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.capacity.util.SquirrelUtils;
import com.sankuai.avatar.web.service.DXUserService;
import com.sankuai.avatar.web.vo.DXUserVO;
import com.sankuai.xm.udb.thrift.AppTenantEnum;
import com.sankuai.xm.udb.thrift.UdbOpenThriftBeans;
import com.sankuai.xm.udb.thrift.UdbOpenThriftClient;
import com.sankuai.xm.uinfo.thrift.model.UinfoEntity;
import com.sankuai.xm.uinfo.thrift.service.UinfoOpenThriftBeans;
import com.sankuai.xm.uinfo.thrift.service.UinfoOpenThriftClientService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jie.li.sh
 * @create 2020-02-18
 **/

@Service
public class DXUserServiceImpl implements DXUserService {
    private static final String C_KEY = "dx_avatar_url";

    private static final String APP_KEY = "800ta54932309112";
    private static final Long PUB_ID = 137442896082L;

    private static final String APP_KEY_TEST = "0p920521371416p9";
    private static final Long PUB_ID_TEST = 137438988150L;

    @MdpConfig("APP_TOKEN:80c6a724228d36aa0f744098d467dcf2")
    private static String APP_TOKEN;

    @MdpConfig("TENANT:sankuai.info")
    private static String TENANT;

    @MdpConfig("TENANT:maoyan.com")
    private static String MAOYAN_TENANT;

    private UdbOpenThriftClient udbOpenThriftClient = UdbOpenThriftBeans.get(UdbOpenThriftClient.class);
    private UinfoOpenThriftClientService uinfoOpenThriftClientService = UinfoOpenThriftBeans.get(UinfoOpenThriftClientService.class);

    private DXUserVO getUserVoByTenant(String mis, String tenant) {
        DXUserVO user = new DXUserVO();
        user.setMis(mis);
        try {
            Long uid = udbOpenThriftClient.getUid(mis, tenant);
            if (null == uid) {
                return user;
            }
            Set<Long> uidSet = new HashSet<>();
            uidSet.add(uid);

            Map<Long, UinfoEntity> uidUinfoEntityMap = uinfoOpenThriftClientService.getUinfoEntities(uidSet);
            if (null == uidUinfoEntityMap || uidUinfoEntityMap.size() == 0) {
                return user;
            }
            user.setAvatarUrl(uidUinfoEntityMap.get(uid).getAvatar_url());
            user.setName(uidUinfoEntityMap.get(uid).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public DXUserVO getUserVo(String mis) {
        DXUserVO user = getUserVoByTenant(mis, TENANT);
        if (user.getName() == null || user.getName().isEmpty()) {
            user = getUserVoByTenant(mis, AppTenantEnum.APPID_1_MAOYAN.getTenant());
        }
        return user;
    }

    @Override
    public Map<String, String> batchGetUserAvatarUrlCache(List<String> mis){
        Map<String, String> misUrlMap = new HashMap<>();
        Map<StoreKey, Object> values = SquirrelUtils.multiGet(mis.stream().map(i -> String.format("%s_%s", C_KEY, i)).collect(Collectors.toList()));
        for (Map.Entry<StoreKey, Object> entry : values.entrySet()){
            if (!entry.getKey().getParamsAsList().isEmpty() && entry.getValue() != null) {
                misUrlMap.put(StringUtils.join(entry.getKey().getParamsAsList(), ""), entry.getValue().toString());
            }
        }
        return misUrlMap;
    }

    @Override
    public String getUserAvatarUrlByCache(String mis, Map<String, String> userUrlMap) {
        String value = userUrlMap.get(String.format("%s_%s", C_KEY, mis));
        return StringUtils.isNotEmpty(value) ? value : getUserVo(mis).getAvatarUrl();
    }

    @Override
    public List<DXUserVO> getUserVo(List<String> misList) {
        List<DXUserVO> userList = new ArrayList<>();
        Map<String, UinfoEntity> misUinfoEntityMap = getuidUinfoEntityMap(misList);
        misList.forEach(mis->{
            if ("".equals(mis)) {
                return;
            }
            DXUserVO userVO = new DXUserVO();
            userVO.setMis(mis);
            UinfoEntity uinfoEntity = null;
            if (misUinfoEntityMap != null) {
                uinfoEntity = misUinfoEntityMap.get(mis);
            }
            if (uinfoEntity != null) {
                userVO.setName(uinfoEntity.getName());
                userVO.setAvatarUrl(uinfoEntity.getAvatar_url());
            }
            userList.add(userVO);
        });
        return userList;
    }
    private Map<String, UinfoEntity> getuidUinfoEntityMap(List<String> misList, String tenant) {
        try {
            Map<String, Long> misUidMap = udbOpenThriftClient.getUids(misList, tenant);
            if (null == misUidMap || misUidMap.size() == 0) {
                return null;
            }
            Set<Long> uidSet = new HashSet<>();
            misUidMap.forEach((mis, uid) -> uidSet.add(uid));
            Map<Long, UinfoEntity> uidUinfoEntityMap = uinfoOpenThriftClientService.getUinfoEntities(uidSet);
            if (null == uidUinfoEntityMap || uidUinfoEntityMap.size() == 0) {
                return null;
            }
            Map<String, UinfoEntity> misUinfoEntityMap = new HashMap<>();

            misUidMap.forEach((mis, uid) -> {
                if (!uidUinfoEntityMap.containsKey(uid)) {
                    return;
                }
                misUinfoEntityMap.put(mis, uidUinfoEntityMap.get(uid));
            });
            return misUinfoEntityMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private Map<String, UinfoEntity> getuidUinfoEntityMap(List<String> misList) {
        Map<String, UinfoEntity> combineResultMap = new HashMap<>();
        Map<String, UinfoEntity> misUidMap = getuidUinfoEntityMap(misList, TENANT);
        Map<String, UinfoEntity> misUidMaoYanMap = new HashMap<>();
        if (misUidMap !=  null){
            combineResultMap.putAll(misUidMap);
            if (misUidMap.size() < misList.size()){
                misUidMaoYanMap = getuidUinfoEntityMap(misList, AppTenantEnum.APPID_1_MAOYAN.getTenant());
            }
        } else {
            misUidMaoYanMap = getuidUinfoEntityMap(misList, AppTenantEnum.APPID_1_MAOYAN.getTenant());
        }
        if (misUidMaoYanMap != null){
            combineResultMap.putAll(misUidMaoYanMap);
        }
        return combineResultMap;
    }
}
