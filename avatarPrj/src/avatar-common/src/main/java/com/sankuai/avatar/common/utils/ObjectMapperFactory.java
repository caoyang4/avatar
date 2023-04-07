package com.sankuai.avatar.common.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author qinwei05
 * @date 2022/12/1 14:42
 */

public class ObjectMapperFactory {

    private ObjectMapperFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static ObjectMapper getInstance() {
        return JacksonHolder.INSTANCE;
    }

    private static class JacksonHolder {
        private static final ObjectMapper INSTANCE = new JacksonObjectMapper();
    }

    private static class JacksonObjectMapper extends ObjectMapper {

        private static final long serialVersionUID = 4288193147502386170L;

        private static final Locale CHINA = Locale.CHINA;

        public JacksonObjectMapper(ObjectMapper objectMapper) {
            super(objectMapper);
        }

        public JacksonObjectMapper() {
            super();
            // 设置地点为中国
            super.setLocale(CHINA);
            // 设置为中国上海时区
            super.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            // 失败处理：忽略未定义的字段
            super.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 下划线转驼峰
            super.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        }

        @Override
        public ObjectMapper copy() {
            return new JacksonObjectMapper(this);
        }
    }
}
