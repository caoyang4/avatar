package com.sankuai.avatar.web.service;

import com.sankuai.avatar.resource.orgtree.OrgTreeResource;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeNodeBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeOrgInfoBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeUserBO;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeNodeDTO;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeOrgInfoDTO;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeUserDTO;
import com.sankuai.avatar.web.service.impl.OrgTreeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * OrgTreeService的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class OrgTreeServiceTest {

    @Mock
    private OrgTreeResource mockOrgTreeResource;

    private OrgTreeService orgTreeServiceImpl;

    @Before
    public void setUp() {
        orgTreeServiceImpl = new OrgTreeServiceImpl(mockOrgTreeResource);
    }

    /**
     * 测试getOrgTree功能逻辑
     */
    @Test
    public void testGetOrgTree() {
        // Setup
        final List<OrgTreeNodeDTO> expectedResult = Arrays.asList(OrgTreeNodeDTO.builder()
                .name("基础研发平台")
                .id(100046)
                .children(Arrays.asList(
                        OrgTreeNodeDTO.builder()
                                .name("信息安全部")
                                .id(1418).children(Arrays.asList(
                                        OrgTreeNodeDTO.builder()
                                                .name("入侵对抗")
                                                .id(104007)
                                                .build())
                                ).build()
                ))
                .build());

        // Configure OrgTreeResource.getOrgTree(...).
        final List<OrgTreeNodeBO> orgTreeNodeBOS = Arrays.asList(OrgTreeNodeBO.builder()
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
        when(mockOrgTreeResource.getOrgTree("user")).thenReturn(orgTreeNodeBOS);

        // Run the test
        final List<OrgTreeNodeDTO> result = orgTreeServiceImpl.getOrgTree("user");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    /**
     * 测试getOrgTree边界条件：Resource的getOrgTree返回空列表
     */
    @Test
    public void testGetOrgTreeThatOrgTreeResourceReturnsNoItems() {
        // Setup
        when(mockOrgTreeResource.getOrgTree("user")).thenReturn(Collections.emptyList());

        // Run the test
        final List<OrgTreeNodeDTO> result = orgTreeServiceImpl.getOrgTree("user");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    /**
     * 测试getOrgInfo功能逻辑
     */
    @Test
    public void testGetOrgInfo() {
        // Setup
        final OrgTreeOrgInfoDTO expectedResult = OrgTreeOrgInfoDTO.builder()
                .appKeyCount(61)
                .applicationCount(17)
                .leader(OrgTreeUserDTO.builder()
                        .mis("zhangsan")
                        .name("张三")
                        .build())
                .build();

        // Configure OrgTreeResource.getOrgInfo(...).
        final OrgTreeOrgInfoBO orgTreeOrgInfoBO = OrgTreeOrgInfoBO.builder()
                .appKeyCount(61)
                .applicationCount(17)
                .leader(OrgTreeUserBO.builder()
                        .mis("zhangsan")
                        .name("张三")
                        .build())
                .build();
        when(mockOrgTreeResource.getOrgInfo("orgIds")).thenReturn(orgTreeOrgInfoBO);

        // Run the test
        final OrgTreeOrgInfoDTO result = orgTreeServiceImpl.getOrgInfo("orgIds");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
