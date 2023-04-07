package com.sankuai.avatar.web.vo;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.meituan.servicecatalog.api.annotations.FieldDoc;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-02-14
 **/

@Data
public class PageInfo<T> {
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
}
