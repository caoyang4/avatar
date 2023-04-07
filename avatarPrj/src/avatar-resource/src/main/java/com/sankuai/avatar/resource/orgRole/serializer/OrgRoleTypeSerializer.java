package com.sankuai.avatar.resource.orgRole.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;

import java.io.IOException;

/**
 * OrgRoleType 序列化配置
 * @author caoyang
 * @create 2022-11-13 15:24
 */
public class OrgRoleTypeSerializer extends JsonSerializer<OrgRoleType> {
    @Override
    public void serialize(OrgRoleType roleType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(roleType.getRoleType());
    }
}
