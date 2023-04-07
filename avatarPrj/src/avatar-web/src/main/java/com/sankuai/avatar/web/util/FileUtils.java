package com.sankuai.avatar.web.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * File工具类
 * @author qinwei05
 */
@Slf4j
public class FileUtils {

    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 设置下载时的响应,字段名可自定义
     * @param response HttpServletResponse
     * @param filename 文件名
     * @param headerFields csv headerFields，需和 data 中对象的属性名一样
     * @param headerNames csv中第一行的字段名，支持中文
     * @param data 要写入 csv 的对象列表
     */
    @SneakyThrows
    public static <T> void downloadCsv(HttpServletResponse response, String filename,
                                   String[] headerFields, String[] headerNames, List<T> data) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/csv");
        String encodeFilename = URLEncoder.encode(filename, "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + encodeFilename);
        try (Writer writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8)) {
            // 写入字节流，让文档以 UTF-8 编码
            writer.write('\uFEFF');
            try (ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE)) {
                csvWriter.writeHeader(headerNames);
                for (T item : data) {
                    csvWriter.write(item, headerFields);
                }
            }
        }
    }
}
