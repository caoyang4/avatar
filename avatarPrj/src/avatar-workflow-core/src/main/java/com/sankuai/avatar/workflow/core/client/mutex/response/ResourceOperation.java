package com.sankuai.avatar.workflow.core.client.mutex.response;

/**
 * 资源操作和对应的流程模板
 *
 * @author zhaozhifan02
 */
public enum ResourceOperation {

    /**
     * 扩容
     */
    EXPLAND("expand", "扩容", "service_expand"),

    /**
     * 下线
     */
    REDUCED("reduced", "下线", "reduced_service"),

    /**
     * 迁移
     */
    TRANSFER("transfer", "迁移", "service_transfer"),

    /**
     * 赠予
     */
    EXCHANGE("exchange", "赠予", "hosts_exchange"),

    /**
     * 重启
     */
    REBOOT("reboot", "重启", "hosts_reboot");

    private String value;

    private String desc;

    private String templateName;

    ResourceOperation(String value, String desc, String templateName) {
        this.value = value;
        this.desc = desc;
        this.templateName = templateName;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getOperationState() {
        return String.format("%s中", desc);
    }

    public static ResourceOperation getByTemplateName(String templateName) {
        for (ResourceOperation i : values()) {
            if (templateName.equals(i.getTemplateName())) {
                return i;
            }
        }
        return null;
    }
}
