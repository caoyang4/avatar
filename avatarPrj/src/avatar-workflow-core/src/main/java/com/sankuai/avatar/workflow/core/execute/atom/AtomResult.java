package com.sankuai.avatar.workflow.core.execute.atom;

import com.sankuai.avatar.workflow.core.execute.atom.atomOutput.AtomOutput;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * atom 执行结果
 *
 * @author xk
 */
@Data
public class AtomResult {

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 持续时间
     */
    private Integer duration;

    /**
     * 输入
     */
    private Object input;

    /**
     * 输出
     */
    private AtomOutput output;

    /**
     * atom状态
     */
    private AtomStatus atomStatus;

    /**
     * 异常信息
     */
    private Exception exception;

    public static AtomResult of() {
        return new AtomResult();
    }
}
