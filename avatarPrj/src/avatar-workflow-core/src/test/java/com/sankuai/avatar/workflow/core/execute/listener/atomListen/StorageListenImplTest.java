package com.sankuai.avatar.workflow.core.execute.listener.atomListen;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import com.sankuai.avatar.workflow.core.execute.atom.AtomTemplate;
import com.sankuai.avatar.dao.workflow.repository.FlowAtomContextRepository;
import com.sankuai.avatar.dao.workflow.repository.FlowRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class StorageListenImplTest {

    @Mock
    private FlowAtomContextRepository flowAtomContextRepository;
    @Mock
    private FlowRepository flowRepository;

    @InjectMocks
    private StorageListenImpl storageListen;


    @Test
    public void atomNew() {
        // mock AtomContext
        AtomContext atomContext = AtomContext.builder().atomStatus(AtomStatus.NEW).build();
        // mock flowAtomContextRepository
        when(this.flowAtomContextRepository.addFlowAtomContext(any())).thenReturn(false);

        // Run the test
        this.storageListen.atomNew(atomContext);

        // verify
        verify(flowAtomContextRepository, times(1)).addFlowAtomContext(any());
    }

    @Test
    public void atomAll() {
        // setup
        FlowContext flowContext = FlowContext.builder().atomIndex(1).build();
        AtomTemplate atomTemplate = AtomTemplate.builder().flowContext(flowContext).index(2).build();
        AtomContext atomContext = AtomContext.builder().atomTemplate(atomTemplate).atomStatus(AtomStatus.SUCCESS).build();

        // Run the test
        this.storageListen.atomAll(atomContext);

        // verify
        verify(flowAtomContextRepository, times(1)).updateFlowAtomContext(any());
        verify(flowRepository, times(1)).updateFlow(any());
    }
}