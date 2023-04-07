package com.sankuai.avatar.web.dto.appkey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务负责人 DTO 对象
 * @author caoyang
 * @create 2022-11-01 15:21
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppkeyUserDTO {

    private String loginName;

    private String name;

    private String avatarUrl;

    private String orgName;
}
