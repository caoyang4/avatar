package com.sankuai.avatar.capacity.node;

import lombok.Builder;
import lombok.Data;

/**
 * @author chenxinli
 */
@Builder
@Data
public class AccessComponent {

    private String name;

    private String cName;

    private Boolean access;
}
