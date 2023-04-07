package com.sankuai.avatar.web.vo.whitelist;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author caoyang
 * @create 2023-02-02 11:46
 */
@Data
public class CancelWhiteParamVO {

    private String user;

    /**
     * appkey
     */
    @NotBlank(message = "appkey不能为空")
    private String appkey;

    /**
     * 白名单类型
     */
    @NotBlank(message = "白名单类型不能为空")
    private String app;

    /**
     * set链路
     */
    private List<String> cellNames;

}
