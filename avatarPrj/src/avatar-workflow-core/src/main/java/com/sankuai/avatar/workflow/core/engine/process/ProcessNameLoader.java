package com.sankuai.avatar.workflow.core.engine.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 启动时加载processNameMap
 * @author Jie.li.sh
 **/
@Component
public class ProcessNameLoader {
    @Autowired
    private Map<String, FlowProcess> processNameMap;

    /**
     * 根据name加载process
     * @param name process bean name
     * @return flow process
     */
    @SuppressWarnings("unchecked")
    public <T extends FlowProcess> T getFlowProcessByName(String name) {
        return (T) processNameMap.get(name);
    }

    /**
     * 根据process获取name
     * @param flowProcess {@link FlowProcess}
     * @return flow name
     */
    public String getName(FlowProcess flowProcess) {
        for (Map.Entry<String, FlowProcess> entry : processNameMap.entrySet()) {
            if (entry.getValue().equals(flowProcess)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 根据processClass获取name
     * @param flowProcessClass {@link FlowProcess}
     * @return flow name
     */
    public String getName(Class<? extends FlowProcess> flowProcessClass) {
        for (Map.Entry<String, FlowProcess> entry : processNameMap.entrySet()) {
            if (entry.getValue().getClass().equals(flowProcessClass)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
