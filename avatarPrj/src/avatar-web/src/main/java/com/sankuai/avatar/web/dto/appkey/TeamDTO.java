package com.sankuai.avatar.web.dto.appkey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 团队
 *
 * @author qinwei05
 * @date 2023/01/06
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private String orgName;
    private String orgId;
}
