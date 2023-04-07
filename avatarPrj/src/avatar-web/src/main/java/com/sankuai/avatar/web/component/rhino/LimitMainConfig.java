package com.sankuai.avatar.web.component.rhino;

import com.dianping.rhino.api.bean.HostQpsLimitStrategy;
import com.dianping.rhino.api.bean.LimitStrategyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author qinwei05
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LimitMainConfig {
    private SwitchStatus masterSwitch;
    private boolean debug;
    private Map<LimitStrategyType, Boolean> strategyMasterSwitch;
    private HostQpsLimitStrategy hostStrategy;
    private HostQpsLimitStrategy testHostStrategy;
    private HostQpsLimitStrategy adaptiveVmStrategy;
}
