package com.sankuai.avatar.workflow.core.checker;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.threadPool.ThreadPoolFactory;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 预检处理器, 执行预检
 *
 * @author xk
 */
@Component
public class CheckHandler {

    @Autowired
    private List<Checker> checkers;

    /**
     * 预检并发线程池
     */
    private final ThreadPoolExecutor checkPool = ThreadPoolFactory.factory("preCheck");

    /**
     * 获取预检列表
     *
     * @param flowContext 流上下文
     * @return {@link List}<{@link Checker}>
     */
    private List<Checker> buildCheckerList(FlowContext flowContext) {
        return this.checkers.stream().filter(checker -> checker.matchCheck(flowContext)).collect(Collectors.toList());
    }

    /**
     * 并发预检
     *
     * @param flowContext 流程上下文
     * @return {@link List}<{@link CheckResult}>
     */
    @RaptorTransaction
    public List<CheckResult> checker(FlowContext flowContext) {
        // 获取预检列表
        List<Checker> checkerList = this.buildCheckerList(flowContext);
        // 并发同步计数器
        CountDownLatch countDownLatch = new CountDownLatch(checkerList.size());
        // 并发预检
        ArrayList<Future<CheckResult>> futures = new ArrayList<>();
        checkerList.forEach(checker -> futures.add(checkPool.submit(()->this.doChecker(checker, flowContext, countDownLatch))));

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<CheckResult> checkResults = new ArrayList<>();
        // 遍历future, 获取并发预检结果
        for (Future<CheckResult> future : futures) {
            try {
                CheckResult checkResult = future.get(10, TimeUnit.MILLISECONDS);
                // 通过的默认忽略
                if (checkResult == null || checkResult.getCheckState().equals(CheckState.PRE_CHECK_ACCEPTED)) {
                    continue;
                }
                // 存储批量结果数据
                if (checkResult.getResultItems() != null) {
                    checkResults.addAll(checkResult.getResultItems());
                    continue;
                }
                // 存储单条结果数据
                checkResults.add(checkResult);
            } catch (Exception e) {
                Cat.logError(e);
            }
        }
        return checkResults;
    }

    /**
     * 执行预检函数
     *
     * @param checker     检查程序
     * @param flowContext 流上下文
     * @return {@link CheckResult}
     */
    private CheckResult doChecker(Checker checker, FlowContext flowContext, CountDownLatch countDownLatch) {
        Transaction transaction = Cat.newTransaction("preChecker", checker.getClass().getSimpleName());
        try {
            CheckResult checkerResult = checker.check(flowContext);
            if (checkerResult.getCheckState().equals(CheckState.PRE_CHECK_REJECTED)) {
                // 遇到reject完成所有同步计数器, 提前结束不在等待后续预检
                for (long i = 0; i < countDownLatch.getCount(); i++) {
                    countDownLatch.countDown();
                }
            }
            transaction.setSuccessStatus();
            return checkerResult;
        } catch (Exception e) {
            transaction.setStatus(e);
        } finally {
            countDownLatch.countDown();
            transaction.complete();
        }
        return null;
    }
}
