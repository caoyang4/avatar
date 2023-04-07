package com.sankuai.avatar.workflow.core.execute;

import cn.hutool.core.lang.Assert;
import com.sankuai.avatar.dao.workflow.repository.*;
import com.sankuai.avatar.dao.workflow.repository.entity.*;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomTemplate;
import com.sankuai.avatar.workflow.core.execute.listener.AtomEventImpl;
import com.sankuai.avatar.workflow.core.input.plus.AddPlusFlowInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AtomLoaderImplTest {

    @Mock
    private AtomScheduler mockAtomScheduler;
    @Mock
    private AtomEventImpl mockAtomEvent;
    @Mock
    private AtomStepRepository mockAtomStepRepository;
    @Mock
    private FlowDisplayRepository mockFlowDisplayRepository;
    @Mock
    private FlowDataRepository mockFlowDataRepository;
    @Mock
    private FlowAtomContextRepository mockFlowAtomContextRepository;
    @Mock
    private FlowAtomTemplateRepository mockFlowAtomTemplateRepository;

    @InjectMocks
    private AtomLoaderImpl atomLoaderImplTest;


    /**
     * 已存在流程，从数据库中加载atom上下文
     */
    @Test
    public void testLoadAtomTemplate() {
        // mock flowContext
        FlowContext flowContext = FlowContext.builder().id(1).atomIndex(1).flowInput(AddPlusFlowInput.builder().build()).build();
        // mock ProcessContext
        ProcessContext processContext = mock(ProcessContext.class);
        // mock Display
        FlowDisplayEntity displayEntity = FlowDisplayEntity.builder().id(1).build();
        // mock flowDisplayRepository
        when(mockFlowDisplayRepository.getFlowDisplayByFlowId(anyInt())).thenReturn(displayEntity);
        // mock  FlowAtomContextEntity
        FlowAtomContextEntity flowAtomContextEntity = FlowAtomContextEntity.builder().status("NEW").seq(1).build();
        // mock mockFlowAtomContextRepository
        when(mockFlowAtomContextRepository.getFlowAtomContextByFlowId(anyInt())).thenReturn(Arrays.asList(flowAtomContextEntity, flowAtomContextEntity));
        // mock mockFlowDataRepository
        FlowDataEntity flowDataEntity = FlowDataEntity.builder().build();
        when(mockFlowDataRepository.getFlowDataByFlowId(anyInt())).thenReturn(flowDataEntity);

        // Run the test
        atomLoaderImplTest.loadAtomTemplate(processContext, flowContext);

        // Verify the results
        verify(mockFlowDisplayRepository, times(1)).getFlowDisplayByFlowId(1);
        verify(mockFlowAtomContextRepository, times(1)).getFlowAtomContextByFlowId(anyInt());
        ArgumentCaptor<AtomTemplate> argumentCaptor = ArgumentCaptor.forClass(AtomTemplate.class);
        verify(this.mockAtomScheduler, times(1)).dispatch(argumentCaptor.capture());
        Assert.isTrue(argumentCaptor.getValue().getIndex().equals(flowContext.getAtomIndex()));
        Assert.isTrue(argumentCaptor.getValue().getFlowContext().equals(flowContext));
    }

    /**
     * 已存在流程，从数据库中加载atom上下文, 指针异常异常场景
     */
    @Test
    public void testLoadAtomTemplate2() {
        // mock flowContext, 这里atomIndex指针错误, 队列只有0和1，这里指向了2
        FlowContext flowContext = FlowContext.builder().id(1).atomIndex(2).flowInput(AddPlusFlowInput.builder().build()).build();
        // mock ProcessContext, 指针错误
        ProcessContext processContext = mock(ProcessContext.class);
        //when(processContext.getSeq()).thenReturn(2);
        // mock Display
        FlowDisplayEntity displayEntity = FlowDisplayEntity.builder().id(1).build();
        // mock flowDisplayRepository
        when(mockFlowDisplayRepository.getFlowDisplayByFlowId(anyInt())).thenReturn(displayEntity);
        // mock  FlowAtomContextEntity
        FlowAtomContextEntity flowAtomContextEntity = FlowAtomContextEntity.builder().status("NEW").seq(1).build();
        when(mockFlowAtomContextRepository.getFlowAtomContextByFlowId(anyInt())).thenReturn(Arrays.asList(flowAtomContextEntity, flowAtomContextEntity));
        // mock mockFlowDataRepository
        FlowDataEntity flowDataEntity = FlowDataEntity.builder().build();
        when(mockFlowDataRepository.getFlowDataByFlowId(anyInt())).thenReturn(flowDataEntity);

        // Run the test
        atomLoaderImplTest.loadAtomTemplate(processContext, flowContext);

        // Verify the results
        verify(this.mockAtomScheduler, never()).dispatch(any(AtomTemplate.class));
    }

    /**
     * 新流程，从模板创建atom上下文
     */
    @Test
    public void testLoadAtomTemplate3() {
        // mock flowContext
        FlowContext flowContext = FlowContext.builder().id(1).atomIndex(1).flowInput(AddPlusFlowInput.builder().build()).build();
        // mock ProcessContext
        ProcessContext processContext = mock(ProcessContext.class);
        // mock Display
        FlowDisplayEntity displayEntity = FlowDisplayEntity.builder().id(1).build();
        // mock flowDisplayRepository
        when(mockFlowDisplayRepository.getFlowDisplayByFlowId(anyInt())).thenReturn(displayEntity);
        // mock FlowAtomTemplateEntity
        FlowAtomTemplateEntity templateEntity = FlowAtomTemplateEntity.builder().atomName("atomName").seq(1).build();
        // mock flowAtomTemplateRepository
        when(mockFlowAtomTemplateRepository.getFlowAtomTemplate(any(), any())).thenReturn(Arrays.asList(templateEntity, templateEntity));
        // mock AtomStepEntity
        AtomStepEntity atomStepEntity = mock(AtomStepEntity.class);
        // mock atomStepRepository
        when(mockAtomStepRepository.getAtomStepByName(anyString())).thenReturn(atomStepEntity);
        // mock mockFlowDataRepository
        FlowDataEntity flowDataEntity = FlowDataEntity.builder().build();
        when(mockFlowDataRepository.getFlowDataByFlowId(anyInt())).thenReturn(flowDataEntity);

        // Run the test
        atomLoaderImplTest.loadAtomTemplate(processContext, flowContext);

        // Verify the results
        verify(mockFlowAtomTemplateRepository, times(1)).getFlowAtomTemplate(null, null);
        verify(mockAtomStepRepository, times(2)).getAtomStepByName(anyString());
        verify(mockFlowDisplayRepository, times(1)).getFlowDisplayByFlowId(1);
        verify(mockFlowAtomContextRepository, times(1)).getFlowAtomContextByFlowId(anyInt());
        verify(mockAtomEvent, times(2)).pushEvent(any());
        ArgumentCaptor<AtomTemplate> argumentCaptor = ArgumentCaptor.forClass(AtomTemplate.class);
        verify(this.mockAtomScheduler, times(1)).dispatch(argumentCaptor.capture());
        Assert.isTrue(argumentCaptor.getValue().getIndex().equals(flowContext.getAtomIndex()));
        Assert.isTrue(argumentCaptor.getValue().getFlowContext().equals(flowContext));
    }
}
