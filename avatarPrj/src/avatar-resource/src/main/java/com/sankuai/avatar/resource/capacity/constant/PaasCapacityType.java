package com.sankuai.avatar.resource.capacity.constant;

/**
 * 容灾实体类型
 * @author caoyang
 * @create 2022-09-27 10:54
 */
public enum PaasCapacityType {
    /*
      squirrel 集群类型
     */
    CLUSTER("CLUSTER"),
    /*
     * mafka topic 类型
     */
    TOPIC("TOPIC"),
    /*
     * appkey 类型
     */
    APPKEY("APPKEY");

    private final String capacityType;
    PaasCapacityType(String type) {
        this.capacityType = type;
    }

    public String getCapacityType(){
        return capacityType;
    }

}

