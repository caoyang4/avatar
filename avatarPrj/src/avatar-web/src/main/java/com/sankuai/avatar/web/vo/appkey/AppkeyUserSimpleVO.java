package com.sankuai.avatar.web.vo.appkey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qinwei05
 * @date 2023/1/9 15:02
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppkeyUserSimpleVO {

    /**
     * MIS号
     */
    private String loginName;

    /**
     * 中文名字
     */
    private String name;
}
