package com.sankuai.avatar.web.vo.tree;

import lombok.Data;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-02-01 13:41
 */
@Data
public class BgTreeVO {

    private String text;

    private String value;

    private Integer appkeyCount;

    private List<OwtTreeVO> children;

}
