package com.sankuai.avatar.web.vo.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 外购机器对象
 *
 * @author qinwei05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalHostVO {

    /**
     * 主机
     */
    private HostVO host;

    /**
     * 能否删除
     */
    private Boolean canDelete;

    /**
     * 能否更新
     */
    private Boolean canUpdate;
}
