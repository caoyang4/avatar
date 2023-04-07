package com.sankuai.avatar.web.vo.tree;

import lombok.Data;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-02-01 13:41
 */
@Data
public class OwtTreeVO {

    private String text;

    private String value;

    private List<PdlTreeVO> children;
}
