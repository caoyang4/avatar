package com.sankuai.avatar.client.ops.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-15 15:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OpsHostAppkeyResponse extends OpsResponse{

    List<String> appkeys;
}
