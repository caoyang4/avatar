package com.sankuai.avatar.resource.tree.bo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author caoyang
 * @create 2023-01-05 17:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserPdlBO {
    /**
     * 产品线中文名
     */
    private String pdlName;
    /**
     * 产品线
     */
    private String pdl;
}