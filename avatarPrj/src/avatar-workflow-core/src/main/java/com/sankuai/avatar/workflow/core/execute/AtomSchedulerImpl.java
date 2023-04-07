package com.sankuai.avatar.workflow.core.execute;

import com.sankuai.avatar.common.threadPool.ThreadPoolFactory;
import com.sankuai.avatar.common.threadPool.ThreadPoolType;
import com.sankuai.avatar.workflow.core.engine.process.impl.ExecuteFlowProcess;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventEnum;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.eventInput.ExecuteCallbackEvent;
import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomResult;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import com.sankuai.avatar.workflow.core.execute.atom.AtomTemplate;
import com.sankuai.avatar.workflow.core.execute.listener.AtomEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * atom调度器
 * 解析atom状态，提交到执行器
 *
 * @author xk
 */
@Slf4j
@Component
public class AtomSchedulerImpl implements AtomScheduler{
    @Autowired
    private AtomExecute atomExecute;

    @Autowired
    private AtomEvent atomEvent;

    @Autowired
    private ExecuteFlowProcess executeProcess;

    /**
     * 调度线程池
     */
    private final ThreadPoolExecutor atomScheduler = ThreadPoolFactory.factory(ThreadPoolType.DEFAULT,
            10, "atomScheduler");
    @Override
    public void dispatch(AtomTemplate atomTemplate) {
        // 加入线程池，异步调度, 不阻塞任务提交
        atomScheduler.execute(() -> this.doDispatch(atomTemplate));
    }
    /**
     * 执行atom调度, 或执行、或成功、或失败、或挂起等
     * 由于支持atom并发执行, 回调决策时会有数据一致性问题, 需要加锁
     *
     * @param atomTemplate 原子模板
     */
    private synchronized void doDispatch(AtomTemplate atomTemplate) {
        // 获取调度决策
        AtomStatus atomStatus = this.decisionDispatch(atomTemplate);
        if (atomStatus == null) {
            // 丢弃
            return;
        }
        switch (atomStatus) {
            case NEW:
                // 第一次调度
                List<AtomContext> atomContextList = this.getAtomContexts(atomTemplate, atomTemplate.getIndex());
                this.submitExecute(atomTemplate, atomContextList);
                break;
            case PENDING:
                // 挂起
                this.submitFlowExecute(atomTemplate, AtomStatus.PENDING);
                break;
            case FAIL:
                // 失败
                this.submitFlowExecute(atomTemplate, AtomStatus.FAIL);
                break;
            case SUCCESS:
                // 成功
                this.submitExecute(atomTemplate, this.moveAndGetAtomContexts(atomTemplate));
                break;
            default:
                break;
        }
    }

    /**
     * 调度决策器, 解析编排模板、结合执行结果, 返回调度决策
     *
     * @param atomTemplate 原子模板
     * @return AtomStatus {@link AtomStatus}
     */
    private AtomStatus decisionDispatch(AtomTemplate atomTemplate) {
        List<AtomContext> atomContextList = this.getAtomContexts(atomTemplate, atomTemplate.getIndex());
        if (CollectionUtils.isEmpty(atomContextList)) {
            // 没有需要执行的atom任务
            return AtomStatus.SUCCESS;
        }
        // 获取执行结果
        List<AtomStatus> atomStatuses = atomContextList.stream().map(AtomContext::getAtomStatus).collect(Collectors.toList());

        // 解析执行结果
        if (atomStatuses.stream().anyMatch(state -> state.equals(AtomStatus.NEW))) {
            // 第一次调度
            return AtomStatus.NEW;
        }

        if (atomStatuses.contains(AtomStatus.SCHEDULER) || atomStatuses.contains(AtomStatus.RUNNING)) {
            // 不决策, 并发模式, 有部分atom未执行结束，等待全部结束后决策
            return null;
        }
        if (atomStatuses.stream().filter(state -> state.equals(AtomStatus.SUCCESS)).count() == atomStatuses.size()) {
            // 执行成功
            return AtomStatus.SUCCESS;
        }
        if (atomStatuses.stream().anyMatch(state -> state.equals(AtomStatus.FAIL))) {
            // 执行失败
            return AtomStatus.FAIL;
        }
        if (atomStatuses.stream().anyMatch(state -> state.equals(AtomStatus.PENDING))) {
            // 执行挂起
            return AtomStatus.PENDING;
        }
        return AtomStatus.SUCCESS;
    }

    /**
     * 移动指针并获取下一组AtomContext
     *
     * @param atomTemplate 原子模板
     * @return {@link List}<{@link AtomContext}>
     */
    private List<AtomContext> moveAndGetAtomContexts(AtomTemplate atomTemplate) {
        List<AtomContext> atomContextList = this.getAtomContexts(atomTemplate, atomTemplate.getIndex() + 1);
        if (CollectionUtils.isNotEmpty(atomContextList)) {
            atomTemplate.setIndex(atomTemplate.getIndex() + 1);
        }
        return atomContextList;
    }
    /**
     * 获取指定index的atom上下文列表
     *
     * @param atomTemplate 原子模板
     * @param index        指针
     * @return {@link List}<{@link AtomContext}>
     */
    private List<AtomContext> getAtomContexts(AtomTemplate atomTemplate, Integer index) {
        return atomTemplate.getAtomContextList().stream()
                .filter(atomContext -> atomContext.getSeq().equals(index))
                .collect(Collectors.toList());
    }

    /**
     * atom 任务列表执行结束, 回调flow调度器继续流程转flow
     */
    private void submitFlowExecute(AtomTemplate atomTemplate, AtomStatus state) {
        // 构建调度事件上下文
        SchedulerEventContext eventContext = SchedulerEventContext.builder()
                .schedulerEventEnum(SchedulerEventEnum.EXECUTE_CALLBACK)
                .eventInput(ExecuteCallbackEvent.of(state, atomTemplate.getAtomContextList()))
                .build();
        this.executeProcess.process(atomTemplate.getProcessContext(), eventContext);
    }

    /**
     * 提交atom任务到执行器
     *
     * @param atomTemplate    流程模板
     * @param atomContextList atom上下文列表
     */
    private void submitExecute(AtomTemplate atomTemplate, List<AtomContext> atomContextList) {
        if (CollectionUtils.isEmpty(atomContextList)) {
            // 下一个atom为空, 表示所有atom成功执行,  回调flowProcess
            this.submitFlowExecute(atomTemplate, AtomStatus.SUCCESS);
            return;
        }

        // 不为空则提交继续执行
        for (AtomContext atomContext : atomContextList) {
            // 设置编排模板
            atomContext.setAtomTemplate(atomTemplate);
            // 设置atom为调度状态
            atomContext.setAtomStatus(AtomStatus.SCHEDULER);
            // 初始化result对象
            atomContext.setAtomResult(AtomResult.of());
            // 推送事件
            atomEvent.pushEvent(atomContext);
        }
        atomContextList.forEach(this.atomExecute::execute);
    }
}
