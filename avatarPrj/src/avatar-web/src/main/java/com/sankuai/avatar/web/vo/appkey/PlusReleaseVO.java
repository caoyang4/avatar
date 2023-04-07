package com.sankuai.avatar.web.vo.appkey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qinwei05
 * @date 2022/10/28 11:12
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlusReleaseVO {
    /**
     * appkey绑定发布项列表
     */
    private List<PlusBindReleaseVO> bindPlus;

    /**
     * appkey应用发布项列表
     */
    private List<PlusAppliedReleaseVO> appliedPlus;

}
