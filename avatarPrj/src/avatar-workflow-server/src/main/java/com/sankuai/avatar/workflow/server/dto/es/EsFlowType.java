package com.sankuai.avatar.workflow.server.dto.es;

/**
 * 流程类型
 *
 * @author zhaozhifan02
 */
public enum EsFlowType {

    /**
     * 查询
     */
    SEARCH(1, "search"),

    /**
     * atom 统计
     */
    ATOM(2, "atom"),

    /**
     * oceanus 流程
     */
    OCEANUS(3, "oceanus"),

    /**
     * 流程统计
     */
    STATISTICS(4, "statistics"),

    /**
     * 审核
     */
    AUDIT(5, "audit");

    EsFlowType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    private Integer value;

    private String name;

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static EsFlowType getByName(String name) {
        for (EsFlowType t : values()) {
            boolean equals = name.equals(t.getName());
            if (equals) {
                return t;
            }
        }
        return null;
    }
}
