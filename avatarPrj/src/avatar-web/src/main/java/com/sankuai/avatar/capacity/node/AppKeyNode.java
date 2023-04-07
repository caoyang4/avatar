package com.sankuai.avatar.capacity.node;

import com.sankuai.avatar.capacity.constant.MiddleWareName;
import com.sankuai.avatar.capacity.constant.WhiteApp;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
@Builder
@Data
@AllArgsConstructor
public class AppKeyNode implements Node {
    private String appkey;

    private String orgPath;

    private String orgDisplayName;

    private Boolean stateful;
    /**
     * srv
     */
    private String srv;
    /**
     * 服务等级
     */
    private String rank;
    /**
     * set 标签
     */
    private SetName setName;
    /**
     * 是否是paas服务
     */
    private Boolean paas;
    /**
     * 是否plus发布过
     */
    private Boolean plusDeployed;
    /**
     * 是否接入弹性伸缩
     */
    private Boolean elastic;
    /**
     * 是否可单机重启
     */
    private Boolean singleHostRestart;

    /**
     * nest
     */
    private Boolean nest;

    /**
     * 宿主机
     */
    private Boolean parasited;

    /**
     * jbox服务
     */
    private Boolean jbox;
    /**
     * 白名单列表
     */
    private List<WhiteInfo> whiteInfoList = new ArrayList<>();
    /**
     * 中间件信息
     */
    private List<MiddleWare> middleWareInfoList = new ArrayList<>();
    /**
     * 主机列表
     */
    private List<Host> hostList = new ArrayList<>();
    /**
     * 主机机房分布
     */
    private Map<String, List<Host>> idcHostMap;
    /**
     * http 节点
     */
    private List<OctoProvider> octoHttpProviderList = new ArrayList<>();
    /**
     * http节点机房分布
     */
    private Map<String, List<OctoProvider>> idcOctoHttpProviderMap;
    /**
     * thrift节点
     */
    private List<OctoProvider> octoThriftProviderList = new ArrayList<>();
    /**
     * thrift 节点机房分布
     */
    private Map<String, List<OctoProvider>> idcOctoThriftProviderMap;
    /**
     * 资源利用率
     */
    private ResourceUtil resourceUtil;
    /**
     * 达标等级
     */
    private Integer standardLevel;

    /**
     * owt
     */
    private String owt;

    /**
     * 是否需要计算
     */
    private Boolean calculate = true;

    public AppKeyNode(String appKey, String srv, String owt, String rank, Boolean singleHostRestart){
        this.appkey = appKey;
        this.srv = srv;
        this.owt = owt;
        this.rank = rank;
        this.singleHostRestart = singleHostRestart;
    }

    public AppKeyNode(String appKey, String srv, String owt, String rank, Boolean singleHostRestart, SetName setName){
        this.appkey = appKey;
        this.srv = srv;
        this.owt = owt;
        this.rank = rank;
        this.singleHostRestart = singleHostRestart;
        this.setName = setName;
    }

    @Override
    public Boolean isElastic() {
        return Boolean.TRUE.equals(getElastic());
    }

    @Override
    public Boolean isNest() {
        return Boolean.TRUE.equals(getNest());
    }

    @Override
    public Boolean isPaas() {
        return Boolean.TRUE.equals(getPaas());
    }

    @Override
    public Boolean isParasited() {
        return Boolean.TRUE.equals(getParasited());
    }

    @Override
    public Boolean isJbox() {
        return Boolean.TRUE.equals(getJbox());
    }

    @Override
    public Boolean isCalculate() {
        return Boolean.TRUE.equals(getCalculate());
    }

    @Override
    public Boolean isSingleHostRestart() {
        return Boolean.TRUE.equals(getSingleHostRestart());
    }

    @Override
    public Boolean isPlusDeployed() {
        return Boolean.TRUE.equals(getPlusDeployed());
    }

    @Override
    public Boolean hasMiddleWare(MiddleWareName middleWareName) {
        for (MiddleWare middleWareInfo: getMiddleWareInfoList()){
            if(middleWareInfo.middleWareName.equals(middleWareName) && middleWareInfo.used.equals(Boolean.TRUE)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isWhiteApp(WhiteApp whiteApp) {
        if (getWhiteInfoList() == null) {
            return false;
        }
        for (WhiteInfo whiteInfo: getWhiteInfoList()) {
            if (whiteInfo.whiteApp.equals(whiteApp)) {
                return true;
            }
        }
        return false;
    }
}

