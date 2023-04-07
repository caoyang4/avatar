${title}
【变更名称】[#${flowContext.id?c}]${flowContext.cnName}
<#if flowContext.resource?has_content && flowContext.resource.appkey?has_content>
【变更服务】${flowContext.resource.appkey}
</#if>
<#if flowContext.env?has_content>
【变更环境】${flowContext.env}
</#if>
<#if flowContext.input?has_content && flowContext.input.reason?has_content>
【变更原因】${flowContext.input.reason}
</#if>
【发起人】<#if flowContext.createUserName?has_content>${flowContext.createUserName}/</#if>${flowContext.createUser}
<#if errorMsgList?has_content>
【异常信息】<#list errorMsgList as errMsg>${errMsg} </#list>
</#if>
 [查看详情|${avatarDomain}/#/home?flow_id=${flowContext.uuid}]
