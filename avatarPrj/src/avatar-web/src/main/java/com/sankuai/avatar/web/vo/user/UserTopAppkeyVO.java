package com.sankuai.avatar.web.vo.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author caoyang
 * @create 2022-12-27 17:35
 */
@Data
public class UserTopAppkeyVO {

    @NotBlank(message = "appkey不能为空")
    private String appkey;

    private String user;
}
