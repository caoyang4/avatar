package com.sankuai.avatar.client.dayu.model;

import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Dayu业务分组查询对象
 *
 * @author qinwei05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupTagQueryRequest {

    /**
     * 分页页码（不填，默认1）
     */
    private Integer pageNum;

    /**
     * 分页每页大小（不填，默认10）
     */
    private Integer pageSize;

    /**
     * 需要搜索的内容
     */
    private String keyword;

    /**
     * 服务树owt
     */
    private String owt;

    /**
     * 是否隐藏（默认false）
     */
    private Boolean isHidden;

    /**
     * 映射成Map
     *
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public Map<String, String> toMap(){
        if (StringUtils.isBlank(owt)){
            throw new SdkCallException("owt参数不可以为空");
        }
        Map<String, String> queryRequestMap = new HashMap<>();
        queryRequestMap.put("owt", owt);
        if (isHidden != null){
            queryRequestMap.put("isHidden", String.valueOf(isHidden));
        }
        if (pageNum != null){
            queryRequestMap.put("pageNum", String.valueOf(pageNum));
        }
        if (pageSize != null){
            queryRequestMap.put("pageSize", String.valueOf(pageSize));
        }
        if (StringUtils.isNotBlank(keyword)){
            queryRequestMap.put("keyword", keyword);
        }
        return queryRequestMap;
    }
}
