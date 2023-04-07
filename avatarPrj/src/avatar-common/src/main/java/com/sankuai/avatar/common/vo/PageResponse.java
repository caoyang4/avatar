package com.sankuai.avatar.common.vo;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.meituan.servicecatalog.api.annotations.FieldDoc;
import com.meituan.servicecatalog.api.annotations.TypeDoc;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@TypeDoc(
        description = "分页查询"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    /**
     * 总条数: total number
     */
    @FieldDoc(description = "总条数")
    @ApiModelProperty(value = "总条数", name = "总条数")
    private long totalCount;

    /**
     * 当前页数: current number
     */
    @FieldDoc(description = "当前页数")
    @ApiModelProperty(value = "当前页数", name = "当前页数")
    private int page;

    /**
     * 总页数: page number
     */
    @FieldDoc(description = "总页数")
    @ApiModelProperty(value = "总页数", name = "总页数")
    private int totalPage;

    /**
     * 分页阈值 size number
     */
    @FieldDoc(description = "分页阈值")
    @ApiModelProperty(value = "分页阈值", name = "分页阈值")
    private int pageSize;

    /**
     * 数据元素
     */
    @FieldDoc(description = "数据元素")
    @ApiModelProperty(value = "数据元素", name = "数据元素")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<T> items;


    public void setPage(long page) {
        this.page = (int) page;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = (int) totalPage;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = (int) pageSize;
    }

    public void setCurTotalSize(long total) {
        this.totalCount = total;
        if (total == -1) {
            totalPage = 1;
            return;
        }
        if (pageSize > 0) {
            totalPage = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));
        } else {
            totalPage = 0;
        }
    }
}
