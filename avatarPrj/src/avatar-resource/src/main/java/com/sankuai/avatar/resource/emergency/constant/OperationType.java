package com.sankuai.avatar.resource.emergency.constant;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-12-02 21:27
 */
@Getter
public enum OperationType {

    /**
     * 紧急资源上线
     */
    ECS_ONLINE(0, "新增机器"),

    /**
     * 紧急资源下线
     */
    ECS_OFFLINE(1, "机器下线");

    private final Integer code;
    private  final String desc;

    OperationType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OperationType getInstance(String type){
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        return Arrays.stream(OperationType.values())
                .filter(operationType -> Objects.equals(type, operationType.name())).findFirst().orElse(null);
    }

}
