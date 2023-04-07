package com.sankuai.avatar.workflow.core.engine.process.response;

import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 执行模块的结果
 * @author Jie.li.sh
 * @create 2023-03-17
 **/
@AllArgsConstructor
public class ExecuteResult extends Result {
    /**
     * 执行原子任务结果
     */
    @Getter
    List<AtomContext> atomContextList;

    public static ExecuteResult of(List<AtomContext> atomContextList) {
        return new ExecuteResult(atomContextList);
    }
}
