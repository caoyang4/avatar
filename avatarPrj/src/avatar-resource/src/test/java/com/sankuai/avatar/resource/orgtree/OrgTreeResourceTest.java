package com.sankuai.avatar.resource.orgtree;

import com.sankuai.avatar.client.soa.ScHttpClient;
import com.sankuai.avatar.client.soa.model.ScOrg;
import com.sankuai.avatar.client.soa.model.ScOrgTreeNode;
import com.sankuai.avatar.client.soa.model.ScUser;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeNodeBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeOrgInfoBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeUserBO;
import com.sankuai.avatar.resource.orgtree.impl.OrgTreeResourceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * OrgTreeResource的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class OrgTreeResourceTest {

    @Mock
    private ScHttpClient mockScHttpClient;

    private OrgTreeResource orgTreeResourceImplUnderTest;

    @Before
    public void setUp() {
        orgTreeResourceImplUnderTest = new OrgTreeResourceImpl(mockScHttpClient);
    }

    /**
     * 测试getOrgInfo功能逻辑
     */
    @Test
    public void testGetOrgInfo() {
        // Setup
        final OrgTreeOrgInfoBO expectedResult = OrgTreeOrgInfoBO.builder()
                .appKeyCount(60)
                .applicationCount(17)
                .leader(OrgTreeUserBO.builder().name("zhangsan").build())
                .build();
        when(mockScHttpClient.getOrg("orgIds")).thenReturn(ScOrg.builder()
                .appKeyCount(60)
                .applicationCount(17)
                .leader(ScUser.builder().name("zhangsan").build())
                .build());

        // Run the test
        final OrgTreeOrgInfoBO result = orgTreeResourceImplUnderTest.getOrgInfo("orgIds");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    /**
     * 测试getOrgInfo边界条件：Client的getOrg方法返回null
     */
    @Test
    public void testGetOrgInfoScHttpClientReturnsNoItems() {
        // Setup
        final OrgTreeOrgInfoBO expectedResult = null;
        when(mockScHttpClient.getOrg("orgIds")).thenReturn(null);

        // Run the test
        final OrgTreeOrgInfoBO result = orgTreeResourceImplUnderTest.getOrgInfo("orgIds");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    /**
     * 测试getOrgTree功能逻辑
     */
    @Test
    public void testGetOrgTree() {
        // Setup
        final List<OrgTreeNodeBO> expectedResult = Arrays.asList(OrgTreeNodeBO.builder()
                .name("基础研发平台")
                .id(100046)
                .children(Arrays.asList(
                        OrgTreeNodeBO.builder()
                                .name("信息安全部")
                                .id(1418).children(Arrays.asList(
                                        OrgTreeNodeBO.builder()
                                                .name("入侵对抗")
                                                .id(104007)
                                                .build())
                                ).build()
                ))
                .build());

        // Configure ScHttpClient.getOrgTreeByUser(...).
        final List<ScOrgTreeNode> scOrgTreeNodes = Arrays.asList(ScOrgTreeNode.builder()
                .name("基础研发平台")
                .id(100046)
                .children(Arrays.asList(
                        ScOrgTreeNode.builder()
                                .name("信息安全部")
                                .id(1418).children(Arrays.asList(
                                        ScOrgTreeNode.builder()
                                                .name("入侵对抗")
                                                .id(104007)
                                                .build())
                                ).build()
                ))
                .build());
        when(mockScHttpClient.getOrgTreeByUser("user")).thenReturn(scOrgTreeNodes);

        // Run the test
        final List<OrgTreeNodeBO> result = orgTreeResourceImplUnderTest.getOrgTree("user");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    /**
     * 测试getOrgTree边界条件：Client的getOrgTree返回空列表
     */
    @Test
    public void testGetOrgTreeThatScHttpClientReturnsNoItems() {
        // Setup
        when(mockScHttpClient.getOrgTreeByUser("user")).thenReturn(Collections.emptyList());

        // Run the test
        final List<OrgTreeNodeBO> result = orgTreeResourceImplUnderTest.getOrgTree("user");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    /**
     * 测试getOrgInfo边界条件：ScHttpClient抛出SdkBusinessErrorException
     */
    @Test
    public void testGetOrgInfoThatThrowsSdkBusinessErrorException() {
        // Setup
        when(mockScHttpClient.getOrg("orgIds")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> orgTreeResourceImplUnderTest.getOrgInfo("orgIds"))
                .isInstanceOf(SdkBusinessErrorException.class);
        verify(mockScHttpClient).getOrg(Mockito.anyString());
    }

    /**
     * 测试getOrgInfo边界条件：ScHttpClient抛出SdkCallException
     */
    @Test
    public void testGetOrgInfoThatThrowsSdkCallException() {
        // Setup
        when(mockScHttpClient.getOrg("orgIds")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> orgTreeResourceImplUnderTest.getOrgInfo("orgIds"))
                .isInstanceOf(SdkCallException.class);
        verify(mockScHttpClient).getOrg(Mockito.anyString());
    }

    /**
     * 测试getOrgTree边界条件：ScHttpClient抛出SdkBusinessErrorException
     */
    @Test
    public void testGetOrgTreeThatThrowsSdkBusinessErrorException() {
        // Setup
        when(mockScHttpClient.getOrgTreeByUser("user")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> orgTreeResourceImplUnderTest.getOrgTree("user"))
                .isInstanceOf(SdkBusinessErrorException.class);
        verify(mockScHttpClient).getOrgTreeByUser(Mockito.anyString());
    }

    /**
     * 测试getOrgTree边界条件：ScHttpClient抛出SdkCallException
     */
    @Test
    public void testGetOrgTree_ScHttpClientThrowsSdkCallException() {
        // Setup
        when(mockScHttpClient.getOrgTreeByUser("user")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> orgTreeResourceImplUnderTest.getOrgTree("user")).isInstanceOf(SdkCallException.class);
        verify(mockScHttpClient).getOrgTreeByUser(Mockito.anyString());
    }
}
