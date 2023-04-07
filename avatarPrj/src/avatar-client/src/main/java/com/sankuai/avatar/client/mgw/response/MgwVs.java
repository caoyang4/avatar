package com.sankuai.avatar.client.mgw.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * mgw vs
 *
 * @author qinwei05
 * @date 2022/12/25 20:02
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MgwVs {

    /**
     * 链接超时时间
     */
    @JsonProperty("establish_timeout")
    private String establishTimeout;

    /**
     * 延迟重试
     */
    @JsonProperty("delay_before_retry")
    private String delayBeforeRetry;

    /**
     * 同地域挂载
     */
    @JsonProperty("same_region_mount")
    private String sameRegionMount;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 状态码
     */
    @JsonProperty("status_code")
    private String statusCode;

    /**
     * 自动挂载
     */
    @JsonProperty("auto_mount")
    private String autoMount;

    /**
     * 是否开启session同步功能
     */
    @JsonProperty("session_sync")
    private String sessionSync;

    /**
     * 集群
     */
    private MgwCluster cluster;

    /**
     * vip
     */
    private String vip;

    /**
     * vs env
     */
    @JsonProperty("vs_env")
    private String vsEnv;

    /**
     * 负责人列表
     */
    private String owners;

    /**
     * vport
     */
    private Integer vport;

    /**
     * id
     */
    private Integer id;

    /**
     * 后端端口
     */
    @JsonProperty("backend_port")
    private String backendPort;

    /**
     * 额外信息
     */
    @JsonProperty("extra_info")
    private String extraInfo;

    /**
     * ht get重试
     */
    @JsonProperty("htget_retry")
    private String htGetRetry;

    /**
     * 自动挂载/卸载环境
     */
    @JsonProperty("auto_env")
    private String autoEnv;

    /**
     * 连接超时
     */
    @JsonProperty("connect_timeout")
    private String connectTimeout;

    /**
     * vs的业务名称
     */
    @JsonProperty("service_name")
    private String serviceName;

    /**
     * 服务树挂载点
     */
    @JsonProperty("stree_path")
    private String streePath;

    /**
     * 自动挂载
     */
    @JsonProperty("auto_unmount")
    private String autoUnmount;

    /**
     * 健康检查
     */
    @JsonProperty("health_check")
    private String healthCheck;

    /**
     * 健康检查重试次数
     */
    @JsonProperty("nbget_retry")
    private String nbgetRetry;

    /**
     * 负载均衡调度策略
     */
    private String schedule;

    /**
     * 检查路径
     */
    @JsonProperty("check_path")
    private String checkPath;

    /**
     * synproxy状态，内网默认disable，外网默认enable
     */
    private String synproxy;

    /**
     * 自动挂载检查
     */
    @JsonProperty("auto_mount_check")
    private String autoMountCheck;

    /**
     * appkey
     */
    private String appkey;

    /**
     * 自动挂载rs端口
     */
    @JsonProperty("auto_mount_rs_port")
    private String autoMountRsPort;

    /**
     * 服务是否有效阀值
     */
    private String quorum;

    /**
     * 后段IP组名称
     */
    @JsonProperty("local_addr_gname")
    private String localAddrGname;

    /**
     * 转发方法
     */
    @JsonProperty("forwarding_method")
    private String forwardingMethod;

    /**
     * ACL访问限制规则
     */
    private String patch;

    /**
     * rs健康检查周期
     */
    @JsonProperty("delay_loop")
    private String delayLoop;

    /**
     * rs检查失败处理机制
     */
    @JsonProperty("inhibit_on_failure")
    private String inhibitOnFailure;

    /**
     * vs全称：根据vip、vport、protocol自动生成，如v010201001100T8080
     */
    @JsonProperty("vs_name")
    private String vsName;

    /**
     * ip版本
     */
    @JsonProperty("ip_version")
    private String ipVersion;

    /**
     * createtime
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    /**
     * updatetime
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;
}
