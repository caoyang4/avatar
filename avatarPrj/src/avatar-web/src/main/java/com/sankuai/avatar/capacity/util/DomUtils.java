package com.sankuai.avatar.capacity.util;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonSyntaxException;
import com.sankuai.avatar.capacity.dto.DomResponse;
import com.sankuai.avatar.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by shujian on 2020/2/19.
 */
@Slf4j
public class DomUtils {
    private static final Logger logger = LoggerFactory.getLogger("logger_com.sankuai.avatar.web");
    private static final String Host = "http://dom-api.sankuai.com";
    private static final Map<String, String> headers = ImmutableMap.of("Authorization", "4e57faf0-1209-489a-84c7-b22f4f29f1f7");


    public static Map<String, Double> getUtilizationByBillingUnit(String unit) throws IOException {
        /*
        周资源利用率区分set
         */
        Map<String, Double> result = new HashMap<>();
        Map<String, String> lastWeekDateTime = getLastWeekDate();
        String start = lastWeekDateTime.get("head");
        String end = lastWeekDateTime.get("tail");
        String url = String.format(
                "%s/api/utilization/appkey?billing_unit=%s&period=week&start_date=%s&end_date=%s&include_white_list=1&groupby=cell",
                Host,
                unit,
                start,
                end
        );
        HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
        if (res.isSuccess()) {
            DomResponse dr = null;
            try {
                dr = GsonUtils.deserialization(res.getBody(), DomResponse.class);
            } catch (JsonSyntaxException e) {
                log.warn("billing unit: " + unit + "的资源利用率数据为空");
            }
            if (dr != null && dr.getCode() == 0 && !dr.getData().isEmpty()) {
                dr.getData().forEach(
                        item -> {
                            try {
                                String cell = (String) item.get("cell");
                                String appKey = (String) item.get("appkey");
                                Double util = (Double) item.get("utilization");
                                String key = cell == null ? appKey : appKey + "_" + cell;
                                result.put(key, (double) (Math.round(util * 10000)) / 10000);
                            } catch (Exception e) {
                                log.warn(String.format("资源利用率数据异常， 结算单元:%s, data: %s", unit, item.toString()));
                            }
                        }
                );
            }
        }
        return result;
    }


    public static Map<String, Double> getUtilizationByBillingUnitGroupByCell(String unit) throws IOException {
        return getUtilizationByBillingUnitGroupByCell(unit, -1);
    }

    public static Map<String, Double> getUtilizationByBillingUnitGroupByCell(String unit, Integer offset) throws IOException {
        /*
        前offset天资源利用率区分set
         */
        Map<String, Double> result = new HashMap<>();
        String yesd = MyDate.getStringOffsetDay(offset);
        String url = String.format(
                "%s/api/utilization/appkey?billing_unit=%s&start_date=%s&end_date=%s&groupby=cell",
                Host,
                unit,
                yesd,
                yesd
        );
        HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
        if (res.isSuccess()) {
            DomResponse dr = null;
            try {
                dr = GsonUtils.deserialization(res.getBody(), DomResponse.class);
            } catch (JsonSyntaxException e) {
                log.warn("billing unit group by cell: " + unit + "的资源利用率数据为空");
            }
            if (dr != null && dr.getCode() == 0 && !dr.getData().isEmpty()) {
                dr.getData().forEach(
                        item -> {
                            try {
                                String appKey = (String) item.get("appkey");
                                String cell = (String) item.get("cell");
                                Double util = (Double) item.get("utilization");
                                String key = cell == null ? appKey : appKey + "_" + cell;
                                result.put(key, (double) (Math.round(util * 10000)) / 10000);
                            } catch (Exception e) {
                                log.warn(String.format("资源利用率数据异常， 结算单元:%s, data: %s", unit, item.toString()));
                            }
                        }
                );
            }
        } else {
            log.warn(String.format("billing unit group by cell, return code is: %s", res.getCode()));
        }
        return result;
    }

    public static Map<String, Double> getAppKeyWeekUtilsByUnit(String unit) throws IOException {
        /*
        周资源利用率不区分set
         */
        Map<String, Double> result = new HashMap<>();
        Map<String, String> lastWeekDateTime = getLastWeekDate();
        String start = lastWeekDateTime.get("head");
        String end = lastWeekDateTime.get("tail");
        String url = String.format(
                "%s/api/utilization/appkey?billing_unit=%s&period=week&start_date=%s&end_date=%s&include_white_list=1",
                Host,
                unit,
                start,
                end
        );
        HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
        if (res.isSuccess()) {
            DomResponse dr = null;
            try {
                dr = GsonUtils.deserialization(res.getBody(), DomResponse.class);
            } catch (JsonSyntaxException e) {
                log.warn("billing unit: " + unit + "的资源利用率数据为空");
            }
            if (dr != null && dr.getCode() == 0 && !dr.getData().isEmpty()) {
                dr.getData().forEach(
                        item -> {
                            try {
                                String cell = (String) item.get("cell");
                                String appKey = (String) item.get("appkey");
                                Double util = (Double) item.get("utilization");
                                String key = cell == null ? appKey : appKey + "_" + cell;
                                result.put(key, (double) (Math.round(util * 10000)) / 10000);
                            } catch (Exception e) {
                                log.warn(String.format("资源利用率数据异常， 结算单元:%s, data: %s", unit, item.toString()));
                            }
                        }
                );
            }
        }
        return result;
    }

    public static Map<String, String> getLastWeekDate() {
        Map<String, String> lastWeekDate = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.DATE, -Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        lastWeekDate.put("head", sdf.format(calendar.getTime()));
        calendar.add(Calendar.DATE, 6);
        lastWeekDate.put("tail", sdf.format(calendar.getTime()));
        return lastWeekDate;
    }

    public static Double getYearPeakValue(String appKey) throws IOException {
        String url = String.format(
                "%s/api/dom/utilization/summary_by_appkey?appkey=%s&start_date=%s&end_date=%s",
                Host,
                appKey,
                MyDate.getStringOffsetDay(-365, "yyyyMMdd"),
                MyDate.getStringOffsetDay(0, "yyyyMMdd")
        );
        HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers);
        if (res.isSuccess()) {
            try {
                DomResponse dr = GsonUtils.deserialization(res.getBody(), DomResponse.class);
                if (dr != null && dr.getCode() == 0 && !dr.getData().isEmpty()) {
                    Map<String, ?> item = dr.getData().get(0);
                    Double util = (Double) item.get("max_utilization");
                    return Objects.nonNull(util) ? util : 0.0;
                }
            } catch (JsonSyntaxException e) {
            }
        }
        return 0.0;
    }

    public static Double getAppKeyUtilization(String appKey, String cell, Boolean period) {
        String url;
        if (!period) {
            url = String.format(
                    "%s/api/utilization/appkey?appkey=%s&include_white_list=1&start_date=%s&groupby=cell",
                    Host,
                    appKey,
                    MyDate.getStringOffsetDay(-1)
            );
        } else {
            url = String.format(
                    "%s/api/utilization/appkey?appkey=%s&include_white_list=1&period=week&groupby=cell&end_date=%s",
                    Host,
                    appKey,
                    MyDate.getStringOffsetDay(-7)
            );
        }
        HTTPUtils.HttpResult res;
        try {
            res = HTTPUtils.retryGet(url, 3, headers, 30000);
            if (res.isSuccess()) {
                DomResponse dr = GsonUtils.deserialization(res.getBody(), DomResponse.class);
                if (dr != null && dr.getCode() == 0 && !dr.getData().isEmpty()) {
                    String finalCell = StringUtils.isBlank(cell) ? null : cell;
                    List<Map<String, ?>> list = dr.getData().stream().filter(obj -> obj.get("cell") == finalCell).collect(Collectors.toList());
                    if (list.size() == 1) {
                        Map<String, ?> item = list.get(0);
                        if (Objects.nonNull(item.get("utilization"))) {
                            Double value = (Double) item.get("utilization");
                            logger.info(String.format("[utilization]appkey: %s, %s资源利用率: %s", appKey, (period ? "上周" : "昨日"), value));
                            return Objects.nonNull(value) ? value : 0.0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            return 0.0;
        }
        return 0.0;
    }

    public static Double getWeekAppKeyUtilizationWithoutCell(String appKey) {
        Map<String, String> lastWeekDate = getLastWeekDate();
        String start = lastWeekDate.get("head");
        String end = lastWeekDate.get("tail");
        String url = String.format("%s/api/utilization/appkey?appkey=%s&include_white_list=1&period=week&start_date=%s&end_date=%s", Host, appKey, start, end);
        HTTPUtils.HttpResult res;
        try {
            res = HTTPUtils.retryGet(url, 2, headers, 30000);
            if (res.isSuccess()) {
                DomResponse dr = GsonUtils.deserialization(res.getBody(), DomResponse.class);
                if (dr != null && dr.getCode() == 0 && !dr.getData().isEmpty()) {
                    Map<String, ?> item = dr.getData().get(0);
                    Double util = (Double) item.get("utilization");
                    return Objects.nonNull(util) ? util : 0.0;
                }
            }
        } catch (Exception e) {
            return 0.0;
        }
        return 0.0;
    }

    public static Double getWeekAppKeyUtilizationWithCell(String appKey, String cell) {
        String set = StringUtils.isBlank(cell) ? null : cell;
        Map<String, String> lastWeekDate = getLastWeekDate();
        String start = lastWeekDate.get("head");
        String end = lastWeekDate.get("tail");
        String url = String.format("%s/api/utilization/appkey?appkey=%s&include_white_list=1&period=week&start_date=%s&end_date=%s&groupby=cell", Host, appKey, start, end);
        HTTPUtils.HttpResult res;
        try {
            res = HTTPUtils.retryGet(url, 2, headers, 30000);
            if (res.isSuccess()) {
                DomResponse dr = GsonUtils.deserialization(res.getBody(), DomResponse.class);
                if (dr != null && dr.getCode() == 0 && !dr.getData().isEmpty()) {
                    List<Map<String, ?>> utilization = dr.getData().stream().filter(p -> p.get("cell") == set).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(utilization)) {
                        Double value = (Double) utilization.get(0).get("utilization");
                        return Objects.nonNull(value) ? value : 0.0;
                    }
                }
            }
        } catch (Exception e) {
            return 0.0;
        }
        return 0.0;
    }

    public static Map<String, Double> getWeekAppKeyUtilization(String appKey){
        String end = DateUtils.dateToString(new Date(), "yyyy-MM-dd");
        String start = DateUtils.dateToString(DateUtils.localDateToDate(LocalDate.now().plusDays(-7)), "yyyy-MM-dd");
        String url = String.format("%s/api/utilization/appkey?appkey=%s&include_white_list=1&env=prod&start_date=%s&end_date=%s", Host, appKey, start, end);
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 2, headers, 30000);
            if (res.isSuccess()) {
                DomResponse dr = GsonUtils.deserialization(res.getBody(), DomResponse.class);
                if (Objects.isNull(dr) || CollectionUtils.isEmpty(dr.getData())) {return null;}
                Map<String, Double> utilMap = new HashMap<>();
                for (Map<String, ?> map : dr.getData()) {
                    String time = (String) map.get("time");
                    Double utilization = (Double) map.get("utilization");
                    if (Objects.nonNull(time) && Objects.nonNull(utilization)) {
                        utilMap.put(time, utilization);
                    }
                }
                return utilMap;
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
