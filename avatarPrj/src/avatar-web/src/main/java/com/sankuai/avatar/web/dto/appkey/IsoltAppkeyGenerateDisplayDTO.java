package com.sankuai.avatar.web.dto.appkey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * sc appkey
 *
 * @author qinwei05
 * @date 2022/11/07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsoltAppkeyGenerateDisplayDTO {

    /**
     * 演练服务名称
     */
    private String isoltAppkeyName;

    /**
     * 描述
     */
    private String msg;
}
