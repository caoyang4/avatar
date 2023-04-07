package com.sankuai.avatar.web.vo.orgtree;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author caoyang
 * @create 2023-01-06 15:58
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserPdlVO {
    /**
     * 产品线中文名
     */
    private String pdlName;
    /**
     * 产品线
     */
    private String pdl;
}