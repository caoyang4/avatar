package com.sankuai.avatar.capacity.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

public class MafkaApiAuthUtil {
    /**
     * 生成签名
     *
     * @param secretKey 秘钥，由MQ团队提供
     * @param uri       请求的url，不包括域名。例如：/api/queryTopic,注意url最前面需要加/
     * @return 签名信息
     * @throws Exception
     */
    public static String getSign(String secretKey, String uri){
        String stringToSign = secretKey + uri;
        String sign = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(stringToSign.getBytes("UTF-8"));
            sign = Base64.encodeBase64String(messageDigest.digest());
        } catch (Exception ignored) {

        }
        return sign;
    }
}
