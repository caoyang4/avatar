package com.sankuai.avatar.collect.appkey.process.impl;

import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.dao.es.AppkeyEsRepository;
import com.sankuai.avatar.dao.es.request.AppkeyUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EsProcessorImplTest {

    @Mock
    private AppkeyEsRepository mockAppkeyEsRepository;

    private EsProcessorImpl esProcessorImplUnderTest;

    @Before
    public void setUp() throws Exception {
        esProcessorImplUnderTest = new EsProcessorImpl(mockAppkeyEsRepository);
    }

    @Test
    public void testProcess() {
        // Setup
        final Appkey appkey = new Appkey("appkey");
        when(mockAppkeyEsRepository.update(any(AppkeyUpdateRequest.class))).thenReturn(true);

        // Run the test
        Boolean result = esProcessorImplUnderTest.process(appkey);

        // Verify the results
        assertThat(result).isTrue();
        Mockito.verify(mockAppkeyEsRepository, times(1)).update(any(AppkeyUpdateRequest.class));
    }
}
