package com.sankuai.avatar.collect.appkey.process.impl;

import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.dao.resource.repository.AppkeyRepository;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbProcessorImplTest {

    @Mock
    private AppkeyRepository mockAppkeyRepository;

    private DbProcessorImpl dbProcessorImplUnderTest;

    @Before
    public void setUp() throws Exception {
        dbProcessorImplUnderTest = new DbProcessorImpl(mockAppkeyRepository);
    }

    @Test
    public void testProcess() {
        // Setup
        final Appkey appkey = new Appkey("appkey");

        // Configure AppkeyRepository.query(...).
        final List<AppkeyDO> appkeyDOS = Collections.singletonList(new AppkeyDO());

        when(mockAppkeyRepository.query(new AppkeyRequest("appkey"))).thenReturn(appkeyDOS);
        when(mockAppkeyRepository.update(any(AppkeyDO.class))).thenReturn(true);

        // Run the test
        Boolean result = dbProcessorImplUnderTest.process(appkey);

        // Verify the results
        Mockito.verify(mockAppkeyRepository, Mockito.times(1)).update(any());
        assertThat(result).isTrue();
    }

    @Test
    public void testProcess_AppkeyOffline() {
        // Setup
        final Appkey appkey = new Appkey("appkey");
        appkey.setIsOffline(Boolean.TRUE);

        // Configure AppkeyRepository.query(...).
        final List<AppkeyDO> appkeyDOS = new ArrayList<>();

        when(mockAppkeyRepository.query(new AppkeyRequest("appkey"))).thenReturn(appkeyDOS);
        // Run the test
        Boolean result = dbProcessorImplUnderTest.process(appkey);

        // Verify the results
        assertThat(result).isFalse();
        Mockito.verify(mockAppkeyRepository, Mockito.times(0)).update(any());
        Mockito.verify(mockAppkeyRepository, Mockito.times(0)).update(any());
    }

    @Test
    public void testProcess_AppkeyRepositoryQueryReturnsNoItems() {
        // Setup
        final Appkey appkey = new Appkey("appkey");

        // Configure AppkeyRepository.query(...).
        final List<AppkeyDO> appkeyDOS = new ArrayList<>();

        when(mockAppkeyRepository.query(new AppkeyRequest("appkey"))).thenReturn(appkeyDOS);
        when(mockAppkeyRepository.insert(any(AppkeyDO.class))).thenReturn(true);

        // Run the test
        Boolean result = dbProcessorImplUnderTest.process(appkey);

        // Verify the results
        Mockito.verify(mockAppkeyRepository, Mockito.times(1)).insert(any());
        assertThat(result).isTrue();
    }
}
