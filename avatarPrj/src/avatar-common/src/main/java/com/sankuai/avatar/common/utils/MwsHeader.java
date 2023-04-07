package com.sankuai.avatar.common.utils;

import com.google.common.collect.Maps;
import com.sankuai.avatar.common.dto.MwsAuthUser;
import org.springframework.http.HttpHeaders;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * @author xk
 */
public class MwsHeader {
    private static final String DATE_FORMAT = "EEE d MMM yyyy HH:mm:ss 'GMT'";

    private static String generateSign(String encryptStr) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKey secretKey = new SecretKeySpec("client_secret".getBytes(), "HmacSHA1");
        mac.init(secretKey);
        return Base64.getEncoder().encodeToString(mac.doFinal(encryptStr.getBytes()));
    }

    public static Map<String, String> getMwsHeaders(String method, String uri, Date date, MwsAuthUser user) throws Exception {
        String userEncoded = Base64.getEncoder().encodeToString(GsonUtils.serialization(user).getBytes());
        String dateStr = new SimpleDateFormat(DATE_FORMAT).format(date);
        String encryptStr = method
                + " " + uri
                + " " + dateStr
                + " " + userEncoded;
        String sign = generateSign(encryptStr);

        Map<String, String> headers = Maps.newHashMap();
        headers.put("PORTAL-PROXY-USER", userEncoded);
        headers.put("PORTAL-PROXY-DATE", dateStr);
        headers.put("PORTAL-PROXY-SIGN", sign);

        return headers;
    }

    public static HttpHeaders getMwsHttpHeaders(String method, String uri, Date date, MwsAuthUser user) throws Exception {
        Map<String, String> headers = getMwsHeaders(method, uri, date, user);
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);

        return httpHeaders;
    }

}
