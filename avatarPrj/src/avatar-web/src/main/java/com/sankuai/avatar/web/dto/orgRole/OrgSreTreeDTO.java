package com.sankuai.avatar.web.dto.orgRole;

import com.sankuai.avatar.web.dto.user.DxUserDTO;
import lombok.Data;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-14 11:12
 */
@Data
public class OrgSreTreeDTO {

    private String id;

    private String name;

    private Integer appkeyCount;

    private String displayName;

    private String orgPath;

    private String roleUsers;

    private List<OrgSreTreeDTO> children;

    private List<DxUserDTO> opAdmins;
}
