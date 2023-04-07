package com.sankuai.avatar.common.utils;

import com.sankuai.avatar.common.vo.PageResponse;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 分页
 *
 * @author qinwei05
 * @date 2023/01/12
 */
public class PageHelperUtils {

    private PageHelperUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 数据分页
     *
     * @param pageNum    页面
     * @param pageSize   页面大小
     * @param sourceList 源列表
     * @return {@link PageResponse}<{@link T}>
     */
    public static <T> PageResponse<T> toPageResponse(Integer pageNum, Integer pageSize, List<T> sourceList){

        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setPage(pageNum);
        pageResponse.setPageSize(pageSize);

        if (CollectionUtils.isEmpty(sourceList)) {
            pageResponse.setTotalCount(0);
            pageResponse.setTotalPage(1L);
            pageResponse.setItems(Collections.emptyList());
            return pageResponse;
        }

        int total = sourceList.size();
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = pageSize <= 0 ? 10 : pageSize;
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<T> items = (start < end) ? sourceList.subList(start, end) : Collections.emptyList();

        pageResponse.setItems(items);
        pageResponse.setTotalCount(total);
        pageResponse.setTotalPage((total - 1) / pageSize + 1L);
        return pageResponse;
    }

    /**
     * 数据分页转换（支持显式传入已经分页完成的总数总数）
     *
     * @param pageNum    页面
     * @param pageSize   页面大小
     * @param totalCount 总数
     * @param sourceList 源列表
     * @return {@link PageResponse}<{@link T}>
     */
    public static <T> PageResponse<T> toPageResponse(Integer pageNum, Integer pageSize, Integer totalCount, List<T> sourceList){
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setPage(pageNum);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPage((totalCount - 1) / pageSize + 1L);
        pageResponse.setTotalCount(totalCount);
        pageResponse.setItems(sourceList);
        return pageResponse;
    }

    /**
     * pageResponse转换
     *
     * @param pageResponse pageResponse
     * @param function     函数式接口
     * @return {@link PageResponse}<{@link T}>
     */
    public static <T, K> PageResponse<T> convertPageResponse(PageResponse<K> pageResponse, Function<List<K>, List<T>> function){
        PageResponse<T> response = new PageResponse<>();
        response.setPage(pageResponse.getPage());
        response.setPageSize(pageResponse.getPageSize());
        response.setTotalPage(pageResponse.getTotalPage());
        response.setTotalCount(pageResponse.getTotalCount());
        response.setItems(function.apply(pageResponse.getItems()));
        return response;
    }
}
