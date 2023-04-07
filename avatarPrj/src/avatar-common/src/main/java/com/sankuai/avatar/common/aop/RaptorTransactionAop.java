package com.sankuai.avatar.common.aop;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * cat transaction 切面
 * @author xk
 */
@Aspect
@Component
@Slf4j
public class RaptorTransactionAop {
    /**
     * 定义切点，关联到cat注解
     */
    @Pointcut("@annotation(com.sankuai.avatar.common.annotation.RaptorTransaction)")
    public void pointCut(){
    }

    /**
     * 定义环绕通知, 执行cat.transaction功能
     */
    @Around("pointCut() && @annotation(annotation)")
    public Object around(ProceedingJoinPoint joinPoint, RaptorTransaction annotation) throws Throwable {

        String name  = StringUtils.isNotEmpty(annotation.name())? annotation.name(): joinPoint.getSignature().getName();
        String type  = StringUtils.isNotEmpty(annotation.type())? annotation.type(): joinPoint.getSignature().getDeclaringType().getSimpleName();

        Transaction transaction = Cat.newTransaction(type ,name);
        Object result = null;
        try {
            // 执行切点的方法（即真正的业务逻辑）
            result = joinPoint.proceed();
            transaction.setStatus(Message.SUCCESS);
        } catch (Throwable e) {
            transaction.setStatus(e);
            log.error(e.getMessage());
            if (joinPoint.getArgs().length > 0){
                try {
                    String args = JsonUtil.bean2Json(joinPoint.getArgs());
                    if (StringUtils.isNotBlank(args)) {
                        transaction.addData(args);
                    }
                } catch (Exception err){
                    log.error(err.getMessage());
                }
            }
            // 默认不抑制异常抛出
            if (annotation.throwException()) {
                throw e;
            }
        }
        finally {
            transaction.complete();
        }
        return result;
    }
}
