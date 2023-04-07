package com.sankuai.avatar.web.dto.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


/**
 * OPS树返回体
 *
 * @author qinwei05
 * @date 2022/12/18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppkeyTreeSrvDetailDTO {

    /**
     * 主机数
     */
    private Integer hostCount;

    /**
     * appkeys
     */
    private List<String> appkeys;

    /**
     * 用户
     */
    private Map<String,String> users;

    /**
     * srv
     */
    private AppkeyTreeSrvDTO srv;
}
