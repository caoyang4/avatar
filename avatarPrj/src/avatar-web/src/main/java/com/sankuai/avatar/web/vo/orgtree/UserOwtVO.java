package com.sankuai.avatar.web.vo.orgtree;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
/**
 * @author caoyang
 * @create 2023-01-06 15:58
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserOwtVO {
    private String owt;
    private String owtName;
    private List<UserPdlVO> pdlList;
}