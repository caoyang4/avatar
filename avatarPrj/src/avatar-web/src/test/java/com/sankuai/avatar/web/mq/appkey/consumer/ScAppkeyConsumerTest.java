package com.sankuai.avatar.web.mq.appkey.consumer;

import com.meituan.mafka.client.consumer.ConsumeStatus;
import com.sankuai.avatar.TestBase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * sc appkey消费者测试
 *
 * @author qinwei05
 * @date 2022/11/04
 */
public class ScAppkeyConsumerTest extends TestBase {

    @Autowired
    private ScAppkeyConsumer scAppkeyListener;

    @Test
    public void testAddConsume() {
        String msgBody = "{\n" +
                "    \"appKey\": \"com.sankuai.sgad.web.marketing\",\n" +
                "    \"type\": \"FRONTEND\",\n" +
                "    \"description\": \"闪购品牌通营销模块，包括平台活动、品牌活动、预算管控等，其中平台与品牌活动主要包括商品券、运费券、单品折扣、单品减运费等活动形式。\",\n" +
                "    \"admin\": {\n" +
                "        \"mis\": \"guoli06\",\n" +
                "        \"name\": \"郭丽\"\n" +
                "    },\n" +
                "    \"team\": {\n" +
                "        \"id\": \"40003801\",\n" +
                "        \"name\": \"应用终端组\",\n" +
                "        \"displayName\": \"到家事业群/到家研发平台/闪购技术部/终端研发组/应用终端组\",\n" +
                "        \"orgIdList\": \"103100/153262/105800/155186/40003801\"\n" +
                "    },\n" +
                "    \"members\": [\n" +
                "        {\n" +
                "            \"mis\": \"guoli06\",\n" +
                "            \"name\": \"郭丽\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"mis\": \"guosong06\",\n" +
                "            \"name\": \"郭松\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"mis\": \"shenguozu\",\n" +
                "            \"name\": \"沈国祖\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"validateState\": \"AUTHENTICATING\",\n" +
                "    \"qualificationType\": \"AUTHENTICATED\",\n" +
                "    \"applicationId\": 8134,\n" +
                "    \"applicationAdmin\": {\n" +
                "        \"mis\": \"liuhongjun03\",\n" +
                "        \"name\": \"刘红军\"\n" +
                "    },\n" +
                "    \"applicationName\": \"sgad\",\n" +
                "    \"applicationChName\": \"闪购广告\",\n" +
                "    \"moduleName\": \"web\",\n" +
                "    \"serviceName\": \"marketing\",\n" +
                "    \"language\": \"JavaScript\",\n" +
                "    \"categories\": [\n" +
                "        \"用户端\",\n" +
                "        \"商家端\"\n" +
                "    ],\n" +
                "    \"tags\": [],\n" +
                "    \"createTime\": 1675392426000,\n" +
                "    \"updateTime\": 1675392425000,\n" +
                "    \"serviceLevel\": \"DEFAULT\",\n" +
                "    \"billingUnit\": \"到家事业群-闪购\",\n" +
                "    \"billingUnitId\": 234,\n" +
                "    \"gitRepository\": \"ssh://git@git.sankuai.com/set/online_shangou_brand_marketing.git\",\n" +
                "    \"stateful\": null,\n" +
                "    \"containerType\": null,\n" +
                "    \"operation\": \"ADD\",\n" +
                "    \"subBillingUnitId\": 23,\n" +
                "    \"serviceType\": null\n" +
                "}";
        ConsumeStatus consumeStatus = scAppkeyListener.consume(msgBody);
        Assert.assertEquals(ConsumeStatus.CONSUME_SUCCESS, consumeStatus);
    }

    @Test
    public void testUpdateConsume() {
        String msgBody = "{\n" +
                "    \"appKey\": \"com.sankuai.avatartestapp.ceshiwaicai\",\n" +
                "    \"type\": \"BACKEND\",\n" +
                "    \"description\": \"app2\",\n" +
                "    \"admin\": {\n" +
                "        \"mis\": \"qinwei05\",\n" +
                "        \"name\": \"秦伟\"\n" +
                "    },\n" +
                "    \"team\": {\n" +
                "        \"id\": \"1021866\",\n" +
                "        \"name\": \"变更管理开发组\",\n" +
                "        \"displayName\": \"基础研发平台/基础技术部/服务运维部/运维工具开发组/变更管理开发组\",\n" +
                "        \"orgIdList\": \"100046/150042/1573/150044/1021866\"\n" +
                "    },\n" +
                "    \"members\": [\n" +
                "        {\n" +
                "            \"mis\": \"qinwei05\",\n" +
                "            \"name\": \"秦伟\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"mis\": \"zhaozhifan02\",\n" +
                "            \"name\": \"赵志凡\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"validateState\": \"UNAUTHENTICATED\",\n" +
                "    \"qualificationType\": \"AUTHENTICATED\",\n" +
                "    \"applicationId\": 10025,\n" +
                "    \"applicationAdmin\": {\n" +
                "        \"mis\": \"qinwei05\",\n" +
                "        \"name\": \"秦伟\"\n" +
                "    },\n" +
                "    \"applicationName\": \"AvatarTestApp\",\n" +
                "    \"applicationChName\": \"Avatar开发测试应用\",\n" +
                "    \"moduleName\": \"app3\",\n" +
                "    \"serviceName\": \"app3\",\n" +
                "    \"language\": \"\",\n" +
                "    \"categories\": [],\n" +
                "    \"tags\": [],\n" +
                "    \"createTime\": 1640595540000,\n" +
                "    \"updateTime\": 1667532745000,\n" +
                "    \"serviceLevel\": \"CORE\",\n" +
                "    \"billingUnit\": \"物理机\",\n" +
                "    \"billingUnitId\": 31,\n" +
                "    \"gitRepository\": \"\",\n" +
                "    \"stateful\": false,\n" +
                "    \"containerType\": \"\",\n" +
                "    \"operation\": \"UPDATE\",\n" +
                "    \"subBillingUnitId\": 7193,\n" +
                "    \"serviceType\": \"Other: Other\"\n" +
                "}";
        ConsumeStatus consumeStatus = scAppkeyListener.consume(msgBody);
        Assert.assertEquals(ConsumeStatus.CONSUME_SUCCESS, consumeStatus);
    }

    @Test
    public void testDeleteConsume() {
        String msgBody = "{\n" +
                "    \"appKey\": \"com.sankuai.avatartestapp.ceshiwaicaii\",\n" +
                "    \"type\": \"BACKEND\",\n" +
                "    \"description\": \"大数据SRE测试用appkey，用途为存放个人测试机器以及线上测试使用。\",\n" +
                "    \"admin\": {\n" +
                "        \"mis\": \"mengxiangze\",\n" +
                "        \"name\": \"孟祥泽\"\n" +
                "    },\n" +
                "    \"team\": {\n" +
                "        \"id\": \"104687\",\n" +
                "        \"name\": \"运维开发组\",\n" +
                "        \"displayName\": \"基础研发平台/数据科学与平台部/数据平台中心/运维开发组\",\n" +
                "        \"orgIdList\": \"100046/114633/1834/104687\"\n" +
                "    },\n" +
                "    \"members\": [\n" +
                "        {\n" +
                "            \"mis\": \"mengxiangze\",\n" +
                "            \"name\": \"孟祥泽\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"mis\": \"matong02\",\n" +
                "            \"name\": \"马桐\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"validateState\": \"ACCEPTED\",\n" +
                "    \"qualificationType\": \"AUTHENTICATED\",\n" +
                "    \"applicationId\": 5542,\n" +
                "    \"applicationAdmin\": {\n" +
                "        \"mis\": \"zhangxiaowei05\",\n" +
                "        \"name\": \"张晓伟\"\n" +
                "    },\n" +
                "    \"applicationName\": \"Admin\",\n" +
                "    \"applicationChName\": \"管理中心\",\n" +
                "    \"moduleName\": \"sre\",\n" +
                "    \"serviceName\": \"test\",\n" +
                "    \"language\": \"\",\n" +
                "    \"categories\": [\n" +
                "        \"基础服务\"\n" +
                "    ],\n" +
                "    \"tags\": [],\n" +
                "    \"createTime\": 1626342757000,\n" +
                "    \"updateTime\": 1675289558000,\n" +
                "    \"serviceLevel\": \"NON_CORE\",\n" +
                "    \"billingUnit\": \"数据平台\",\n" +
                "    \"billingUnitId\": 58,\n" +
                "    \"gitRepository\": \"\",\n" +
                "    \"stateful\": false,\n" +
                "    \"containerType\": \"\",\n" +
                "    \"operation\": \"DELETE\",\n" +
                "    \"subBillingUnitId\": 7153,\n" +
                "    \"serviceType\": \"Other: Other\"\n" +
                "}";
        ConsumeStatus consumeStatus = scAppkeyListener.consume(msgBody);
        Assert.assertEquals(ConsumeStatus.CONSUME_SUCCESS, consumeStatus);
    }
}