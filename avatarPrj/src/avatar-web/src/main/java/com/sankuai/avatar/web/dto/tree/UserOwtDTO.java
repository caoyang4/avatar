package com.sankuai.avatar.web.dto.tree;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
/**
 * @author caoyang
 * @create 2023-01-06 15:24
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserOwtDTO {
    private String owt;
    private String owtName;
    private List<UserPdlDTO> pdlList;
}