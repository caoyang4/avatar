package com.sankuai.avatar.dao.cache.model;

import lombok.Data;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-01-09 15:33
 */
@Data
public class UserBg {

    private String bgName;

    private Integer appkeyCount;

    private List<UserOwt> owtList;

}
