package com.sankuai.avatar.workflow.core.context.loader;

import com.sankuai.avatar.workflow.core.annotation.FlowMeta;
import com.sankuai.avatar.workflow.core.input.FlowInput;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 启动时加载InputMap
 *
 * @author Jie.li.sh
 * @create 2023-03-01
 **/
@Component
public class FlowInputNameLoader {

    private static Map<String, Class<? extends FlowInput>> inputNameMapping;

    @PostConstruct
    private static void init() {
        inputNameMapping = new HashMap<>(32);
        Reflections reflections = new Reflections("com.sankuai.avatar.workflow.core");
        Set<Class<? extends FlowInput>> implClassSet = reflections.getSubTypesOf(FlowInput.class);
        if (implClassSet.isEmpty()) {
            return;
        }
        for (Class<? extends FlowInput> clazz : implClassSet) {
            if (clazz.getDeclaredAnnotation(FlowMeta.class) == null) {
                continue;
            }
            inputNameMapping.put(clazz.getDeclaredAnnotation(FlowMeta.class).flowTemplateName(), clazz);
        }
    }

    public Class<? extends FlowInput> load(String flowTemplateName) {
        return inputNameMapping.get(flowTemplateName);
    }
}
