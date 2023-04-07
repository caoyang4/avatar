package com.sankuai.avatar.dao.workflow.repository.utils;

import com.sankuai.avatar.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import net.razorvine.pickle.Pickler;
import net.razorvine.pickle.Unpickler;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * python pickle序列化的解析
 * @author zhaozhifan02
 */
@Slf4j
public class PicklerDataUtils {

    public static <T> T unPicklerData(byte[] data, Class<T> cls) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        Unpickler unpickler = new Unpickler();
        try {
            Object unpicklerLoadData = unpickler.load(inputStream);
            return JsonUtil.getMapper().convertValue(unpicklerLoadData, cls);
        } catch (IOException e) {
            log.error("Unpickler {} catch exception: {}", cls.getName(), e);
        }
        return null;
    }

    public static byte[] picklerData(Object obj) {
        try {
            Pickler p = new Pickler();
            byte[] result = p.dumps(obj);
            p.close();
            return result;
        } catch (IOException e) {
            log.error("Pickler {} catch exception: {}", obj, e);
        }
        return new byte[0];
    }
}
