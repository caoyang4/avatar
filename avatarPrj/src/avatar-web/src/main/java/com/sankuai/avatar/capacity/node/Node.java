package com.sankuai.avatar.capacity.node;

import com.sankuai.avatar.capacity.constant.MiddleWareName;
import com.sankuai.avatar.capacity.constant.WhiteApp;

import java.util.List;
import java.util.Map;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
public interface Node {
   Boolean isElastic();

   Boolean isNest();

   /**
    * 是否是paas服务
    * @return true/false
    */
   Boolean isPaas();

   Boolean isParasited();

   Boolean isJbox();

   /**
    * 是否需要计算
    * @return true/false
    */
   Boolean isCalculate();
   /**
    * 是否可单机重启
    * @return true/false
    */
   Boolean isSingleHostRestart();
   /**
    * 是否plus发布过
    * @return true/false
    */
   Boolean isPlusDeployed();

   /**
    * 资源利用率
    * @return util
    */
   ResourceUtil getResourceUtil();

   /**
    * 是否已接入指定中间件
    * @param middleWareName 中间件名称
    * @return true/false
    */
   Boolean hasMiddleWare(MiddleWareName middleWareName);

   /**
    * 是否是指定白名单类型
    * @param whiteApp 白名单类型
    * @return true/false
    */
   Boolean isWhiteApp(WhiteApp whiteApp);
   /**
    * 白名单信息
    * @return list
    */
   List<WhiteInfo> getWhiteInfoList();

   /**
    * 中间间信息
    * @return list
    */
   List<MiddleWare> getMiddleWareInfoList();

   /**
    * 主机列表
    * @return host list
    */
   List<Host> getHostList();

   /**
    * 主机机房分布
    * @return idc:hostList
    */
   Map<String, List<Host>> getIdcHostMap();

   /**
    * octo节点列表
    * @return provider list
    */
   List<OctoProvider> getOctoHttpProviderList();

   /**
    * octo节点机房分布
    * @return provider list
    */
   Map<String, List<OctoProvider>> getIdcOctoHttpProviderMap();

   /**
    * octo节点列表
    * @return provider list
    */
   List<OctoProvider> getOctoThriftProviderList();

   /**
    * octo节点机房分布
    * @return provider list
    */
   Map<String, List<OctoProvider>> getIdcOctoThriftProviderMap();
}
