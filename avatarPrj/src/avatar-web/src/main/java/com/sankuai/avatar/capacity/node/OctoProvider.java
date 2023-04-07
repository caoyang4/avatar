package com.sankuai.avatar.capacity.node;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/

@Data
@Builder
public class OctoProvider {
    String hostName;
    String ip;
    String cell;
    String idc;
    String protocol;
    Integer status;
}
