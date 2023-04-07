package com.sankuai.avatar.web.dal.customize;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * @author chenxinli
 */
@Mapper
public interface BatchUpdateMapper<T> {
    /**
     * 批量更新or新增
     * @param recordList list
     * @return int
     */
    @Options(useGeneratedKeys = true)
    @UpdateProvider(type = BatchUpdateProvider.class, method = "dynamicSQL")
    int batchUpdate(List<T> recordList);
}
