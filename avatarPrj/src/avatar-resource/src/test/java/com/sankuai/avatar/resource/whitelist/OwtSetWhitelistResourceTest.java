package com.sankuai.avatar.resource.whitelist;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.dao.resource.repository.OwtSetWhitelistRepository;
import com.sankuai.avatar.dao.resource.repository.model.OwtSetWhitelistDO;
import com.sankuai.avatar.dao.resource.repository.request.OwtSetWhitelistRequest;
import com.sankuai.avatar.resource.whitelist.bo.OwtSetWhitelistBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.resource.whitelist.impl.OwtSetWhitelistResourceImpl;
import com.sankuai.avatar.resource.whitelist.request.OwtSetWhitelistRequestBO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
/**
 * @author caoyang
 * @create 2022-10-27 21:00
 */
public class OwtSetWhitelistResourceTest extends TestBase {

    private OwtSetWhitelistResource resource;

    @Mock
    private OwtSetWhitelistRepository repository;

    private static final OwtSetWhitelistDO owtSetWhitelistDO = new OwtSetWhitelistDO();
    static {
        owtSetWhitelistDO.setOwt("test-owt1");
        owtSetWhitelistDO.setApp("capacity");
        owtSetWhitelistDO.setSetName(UUID.randomUUID().toString());
        owtSetWhitelistDO.setApplyBy("unitTest");
        owtSetWhitelistDO.setReason("无可奉告");
        owtSetWhitelistDO.setStartTime(new Date());
        owtSetWhitelistDO.setEndTime(new Date());
    }

    private static final OwtSetWhitelistBO owtSetWhitelistBO = new OwtSetWhitelistBO();
    static {
        owtSetWhitelistBO.setOwt("test-owt");
        owtSetWhitelistBO.setSetName("test-set");
        owtSetWhitelistBO.setApp(WhiteType.CAPACITY);
        owtSetWhitelistBO.setReason("无可奉告");
        owtSetWhitelistBO.setApplyBy("test");
        owtSetWhitelistBO.setStartTime(new Date());
        owtSetWhitelistBO.setEndTime(new Date());
    }

    @Before
    @Override
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        EntityHelper.initEntityNameMap(OwtSetWhitelistDO.class, new Config());
        resource = new OwtSetWhitelistResourceImpl(repository);
    }

    @Test
    public void queryPage(){
        when(repository.query(Mockito.any(OwtSetWhitelistRequest.class))).thenReturn(Collections.singletonList(owtSetWhitelistDO));
        PageResponse<OwtSetWhitelistBO> pageResponse = resource.queryPage(OwtSetWhitelistRequestBO.builder().build());
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(10, pageResponse.getPageSize());
    }

    @Test
    public void query() {
        when(repository.query(Mockito.any(OwtSetWhitelistRequest.class))).thenReturn(Collections.singletonList(owtSetWhitelistDO));
        List<OwtSetWhitelistBO> boList = resource.query(OwtSetWhitelistRequestBO.builder().build());
        Assert.assertEquals("test-owt1", boList.get(0).getOwt());
        Assert.assertEquals(1, boList.size());
        Assert.assertEquals(WhiteType.CAPACITY, boList.get(0).getApp());
    }

    @Test
    public void save() {
        when(repository.query(Mockito.any(OwtSetWhitelistRequest.class))).thenReturn(Collections.emptyList());
        when(repository.insert(Mockito.any(OwtSetWhitelistDO.class))).thenReturn(true);
        Assert.assertTrue(resource.save(owtSetWhitelistBO));
    }

    @Test
    public void deleteByCondition() {
        Assert.assertFalse(resource.deleteByCondition(null));
        owtSetWhitelistDO.setId(1);
        when(repository.query(Mockito.any(OwtSetWhitelistRequest.class))).thenReturn(Collections.singletonList(owtSetWhitelistDO));
        when(repository.delete(Mockito.any(Integer.class))).thenReturn(true);
        Assert.assertTrue(resource.deleteByCondition(OwtSetWhitelistRequestBO.builder().owt("x").build()));
    }

    @Test
    public void isWhiteOfOwtSet() {
        when(repository.query(Mockito.any(OwtSetWhitelistRequest.class))).thenReturn(Collections.singletonList(owtSetWhitelistDO));
        Assert.assertTrue(resource.isWhiteOfOwtSet("x", "y"));
    }

    @Test
    public void getExpireWhitelist() {
        when(repository.query(Mockito.any(OwtSetWhitelistRequest.class))).thenReturn(Collections.singletonList(owtSetWhitelistDO));
        List<OwtSetWhitelistBO> expireWhitelist = resource.getExpireWhitelist();
        Assert.assertEquals(1, expireWhitelist.size());
    }
}