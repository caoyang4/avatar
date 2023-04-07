package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.AtomStepEntity;

/**
 * Atom 任务数据管理接口
 *
 * @author zhaozhifan02
 */
public interface AtomStepRepository {

    /**
     * 根据名称获取任务信息
     *
     * @param name atom 名称
     * @return {@link AtomStepEntity}
     */
    AtomStepEntity getAtomStepByName(String name);
}
