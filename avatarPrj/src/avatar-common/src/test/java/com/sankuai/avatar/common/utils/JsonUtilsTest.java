package com.sankuai.avatar.common.utils;

import com.jayway.jsonpath.TypeRef;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JsonUtils测试
 *
 * @author qinwei05
 * @date 2022/11/15
 */
@Slf4j
public class JsonUtilsTest {

    @Data
    static class Book {
        /**
         * 分类
         */
        private String category;
        /**
         * 作者
         */
        private String author;
        /**
         * 标题
         */
        private String title;
        /**
         * 价格
         */
        private Double price;
        /**
         * 书籍编号
         */
        private String isbn;
    }

    @Data
    static class Bicycle {
        /**
         * color
         */
        private String color;
        /**
         * price
         */
        private Double price;
    }

    @Data
    static class Store {
        /**
         * book
         */
        private List<Book> book;
        /**
         * bicycle
         */
        private Bicycle bicycle;
    }

    @Data
    private static class Demo {
        /**
         * store
         */
        private Store store;
        /**
         * expensive
         */
        private Integer expensive;
    }

    @Test
    public void testJsonPath() {
        //Book book = JsonPath.parse(jsonData).read("$.store.book[0]", Book.class);
        //TypeRef<List<Book>> typeRef = new TypeRef<List<Book>>(){};
        //List<Book> books = JsonPath.parse(jsonData).read("$..book[*]", typeRef);
        //System.out.println(books);
        //
        //TypeRef<Demo> type$Ref = new TypeRef<Demo>(){};
        //// $ 代表整个json字符串
        //Demo demo = JsonPath.parse(jsonData).read("$", type$Ref);
        //System.out.println(demo);

        String jsonData = "{\n" +
                "   \n" +
                "    \"store\": {\n" +
                "   \n" +
                "        \"book\": [\n" +
                "            {\n" +
                "   \n" +
                "                \"category\": \"reference\",\n" +
                "                \"author\": \"Nigel Rees\",\n" +
                "                \"title\": \"Sayings of the Century\",\n" +
                "                \"price\": 8.95\n" +
                "            },\n" +
                "            {\n" +
                "   \n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Evelyn Waugh\",\n" +
                "                \"title\": \"Sword of Honour\",\n" +
                "                \"price\": 12.99\n" +
                "            },\n" +
                "            {\n" +
                "   \n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Herman Melville\",\n" +
                "                \"title\": \"Moby Dick\",\n" +
                "                \"isbn\": \"0-553-21311-3\",\n" +
                "                \"price\": 8.99\n" +
                "            },\n" +
                "            {\n" +
                "   \n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"J. R. R. Tolkien\",\n" +
                "                \"title\": \"The Lord of the Rings\",\n" +
                "                \"isbn\": \"0-395-19395-8\",\n" +
                "                \"price\": 22.99\n" +
                "            }\n" +
                "        ],\n" +
                "        \"bicycle\": {\n" +
                "   \n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        }\n" +
                "    },\n" +
                "    \"expensive\": 10\n" +
                "}";

        Book book = JsonUtil.jsonPath2Bean(jsonData, Book.class, "$.store.book[0]");
        Assert.assertEquals("reference", book.getCategory());

        List<Book> books = JsonUtil.jsonPath2List(jsonData, Book.class, "$..book[*]");
        Assert.assertEquals(4, books.size());

        Demo demo = JsonUtil.jsonPath2NestedBean(jsonData, new TypeRef<Demo>() {
        });
        Assert.assertNotNull(demo.getExpensive());

        Store store = JsonUtil.jsonPath2NestedBean(jsonData, new TypeRef<Store>() {
        }, "$.store");
        Assert.assertNotNull(store.getBook());
    }

    @Test
    public void testJsonPathToMap() {
        String json = "{\"code\":200,\"msg\":\"ok\",\"list\":[{\"id\":20,\"no\":\"1000020\",\"" +
                "items\":[{\"name\":\"n1\",\"price\":21,\"infos\":{\"feature\":\"\"}}]}]," +
                "\"metainfo\":{\"total\":20,\"info\":{\"owner\":\"qinshu\"," +
                "\"parts\":[{\"count\":13,\"time\":{\"start\":1230002456,\"end\":234001234}}]}}}";
        String code = JsonUtil.readValUsingJsonPath(json, "$.code");
        Assert.assertEquals(String.valueOf(200), code);

        String hello = JsonUtil.readValUsingJsonPath(json, "$.hello");
        Assert.assertEquals("", hello);
    }

    @Test
    public void testMapToBean() throws Exception {
        Map<String, Object> bicycleMap = new HashMap<>();
        bicycleMap.put("color", "green");
        bicycleMap.put("price", 1.1);
        Bicycle bicycle = JsonUtil.mapToBean(bicycleMap, Bicycle.class);
        Assert.assertNotNull(bicycle);
    }

    @Test(expected = Exception.class)
    public void testMapToBeanWithException() throws Exception {
        Map<String, Object> bicycleMap = new HashMap<>();
        bicycleMap.put("color", "green");
        bicycleMap.put("price", "foo");
        Bicycle bicycle = JsonUtil.mapToBean(bicycleMap, Bicycle.class);
        Assert.assertNull(bicycle);
    }


    @Test
    public void testMapToJson() throws Exception {
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("foo", "bar");
        String testMapString = JsonUtil.mapToJson(testMap);
        Assert.assertNotNull(testMapString);
    }
}