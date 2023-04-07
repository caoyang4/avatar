package com.sankuai.avatar.common.vo;

import com.meituan.servicecatalog.api.annotations.FieldDoc;
import com.meituan.servicecatalog.api.annotations.Requiredness;
import com.meituan.servicecatalog.api.annotations.TypeDoc;
import lombok.Data;

/**
 * @author caoyang
 * @create 2022-11-15 14:11
 */
@TypeDoc(description = "分页参数")
@Data
public abstract class PageRequest {
    /**
     * 最大分页限制
     */
    private static final int MAX_PAGE_SIZE = 500;

    /**
     * 默认分页偏移 1
     */
    @FieldDoc(name = "page", description = "页码", rule = "必须正整数，默认为1", requiredness = Requiredness.OPTIONAL, defaultValue = "1", example = "1")
    private Integer page = 1;

    /**
     * 默认分页大小 25
     */
    @FieldDoc(name = "pageSize", description = "每页数量", rule = "必须正整数，默认为10", requiredness = Requiredness.OPTIONAL, defaultValue = "10", example = "10")
    private Integer pageSize = 10;

    /**
     * 限制分页偏移量 > 0
     * @return 分页偏移量
     */
    public Integer getPage() {
        return page < 0 ? 1: page;
    }

    /**
     * 限制分页大小 1 ~ 500
     * @return 分页大小
     */
    public Integer getPageSize() {
        if (pageSize < 0 ) {
            return 10;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            return MAX_PAGE_SIZE;
        }
        return pageSize;
    }
}
