package com.sankuai.avatar.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Keeps json value as json, does not try to deserialize it
 * @author Jie.li.sh
 * @create 2023-03-01
 **/
public class KeepAsJsonDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {

        TreeNode tree = jp.getCodec().readTree(jp);
        return tree.toString();
    }
}
