package com.sankuai.avatar.dao.es.config;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.meituan.poros.client.PorosHighLevelClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qinwei05
 */
@Configuration
public class EagleConfig {
    @MdpConfig("ES_CLUSTER_NAME:eagle_avatar_zf")
    private String clusterName;

    @MdpConfig("ES_APPKEY")
    private String appKey;

    @MdpConfig("ES_ACCESS_KEY:F639C0586EE9A0FE8627D61498D96C4E")
    private String accessKey;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return PorosHighLevelClientBuilder.builder()
                .clusterName(clusterName)
                .appKey(appKey)
                .accessKey(accessKey)
                .httpIOThreadCount(20)
                .timeoutMillis(3000)
                .callESDirectly(true)
                .build();
    }
}
