package com.sankuai.avatar.sdk.entity.servicecatalog;

import com.meituan.servicecatalog.api.annotations.FieldDoc;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class Slice<T> {
    /**
     * 总条数: total number
     */
    @FieldDoc(description = "总条数")
    @ApiModelProperty(value = "总条数", name = "总条数")
    private long tn;

    /**
     * 当前页数: current number
     */
    @FieldDoc(description = "当前页数")
    @ApiModelProperty(value = "当前页数", name = "当前页数")
    private int cn;

    /**
     * 总页数: page number
     */
    @FieldDoc(description = "总页数")
    @ApiModelProperty(value = "总页数", name = "总页数")
    private int pn;

    /**
     * 分页阈值 size number
     */
    @FieldDoc(description = "分页阈值")
    @ApiModelProperty(value = "分页阈值", name = "分页阈值")
    private int sn;

    /**
     * 数据元素
     */
    @FieldDoc(description = "数据元素")
    @ApiModelProperty(value = "数据元素", name = "数据元素")
    private List<T> items;
}
