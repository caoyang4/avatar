package com.sankuai.avatar.web.vo.appkey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qinwei05
 * @date 2023/1/17 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContainerTypeVO {

    /**
     * 默认服务类型
     */
    private String defaultType;

    private List<Items> items;

    @Data
    public static class Items {
        private List<Item> item;
        private String name;
        private String key;
    }

    @Data
    public static class Item {
        private List<Soft> soft;
        private String key;
        private String val;
    }

    @Data
    public static class Soft {
        private String text;
        private String val;
        private String key;
    }
}
