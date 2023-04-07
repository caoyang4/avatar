package com.sankuai.avatar.workflow.core.execute;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.sankuai.avatar.common.threadPool.ThreadPoolFactory;
import com.sankuai.avatar.common.threadPool.ThreadPoolType;
import com.sankuai.avatar.common.utils.ApplicationContextUtil;
import com.sankuai.avatar.workflow.core.execute.atom.Atom;
import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import com.sankuai.avatar.workflow.core.execute.listener.AtomEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * atom执行器
 *
 * 获取atom对象，设置上下文，异步执行atom任务
 *
 * @author xk
 */
@Slf4j
@Component
public class AtomExecuteImpl implements AtomExecute{

    @Autowired
    AtomScheduler atomScheduler;

    @Autowired
    AtomEvent atomEvent;

    /**
     * atom执行线程池
     */
    private final ThreadPoolExecutor atomExecute = ThreadPoolFactory.factory(ThreadPoolType.DEFAULT, 20, "atomExecute");

    @Override
    public void execute(AtomContext atomContext) {
        // 加入线程池异步调度，不阻塞调度器
        atomExecute.execute(() -> this.doExecute(atomContext));
    }

    /**
     * 执行atom
     *
     * @param atomContext atom上下文
     */
    private void doExecute(AtomContext atomContext) {
        // 加载atom对象
        Atom atom = this.loadAtom(atomContext);
        if (atom == null) {
            atomContext.setAtomStatus(AtomStatus.FAIL);
            atomEvent.pushEvent(atomContext);
        } else {
            // 执行atom
            for (int i = 0; i < atom.getRetryTimes() + 1; i++) {
                if (this.executeAtom(atom).equals(AtomStatus.SUCCESS)) {
                    // 如果成功跳出循环，否则根据设置继续重试
                    break;
                }
            }
        }

        // 当前atom执行结束, 提交到调度器继续流转
        this.atomScheduler.dispatch(atomContext.getAtomTemplate());
    }

    /**
     * 实际执行atom
     */
    private AtomStatus executeAtom(Atom atom) {
        AtomStatus atomStatus;
        Transaction transaction = Cat.newTransaction("executeAtom", atom.getClass().getSimpleName());
        try {
            atom.beforeProcess();
            atomStatus = atom.process();
            transaction.setSuccessStatus();
        } catch (Exception e) {
            atomStatus = AtomStatus.FAIL;
            transaction.setStatus(e);
            log.error("执行atom失败", e);
        } finally {
            try {
                atom.afterProcess();
            } catch (Exception e) {
                atomStatus = AtomStatus.FAIL;
                transaction.setStatus(e);
                log.error("执行atom.after失败", e);
            }
            transaction.complete();
        }
        return atomStatus;
    }

    /**
     * 从spring容器获取atom对象，只支持多例
     *
     * @param atomContext atom上下文
     * @return {@link Atom}
     */
    private Atom loadAtom(AtomContext atomContext) {
        try {
            Atom atom = ApplicationContextUtil.getBean(atomContext.getName(), Atom.class);
            atom.setAtomContext(atomContext);
            return atom;
        } catch (Exception e) {
            Cat.logError(e);
            log.error("加载atom失败: " + atomContext.getName(), e);
        }
        return null;
    }
}
