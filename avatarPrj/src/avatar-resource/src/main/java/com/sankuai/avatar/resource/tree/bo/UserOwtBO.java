package com.sankuai.avatar.resource.tree.bo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
/**
 * @author caoyang
 * @create 2023-01-05 17:38
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserOwtBO {
    private String owt;
    private String owtName;
    private List<UserPdlBO> pdlList;
}