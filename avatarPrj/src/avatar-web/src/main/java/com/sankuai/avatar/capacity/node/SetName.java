package com.sankuai.avatar.capacity.node;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jie.li.sh
 * @create 2020-04-17
 **/
@Getter
public class SetName {
    private final String GRAY_RELEASE_SET_TAG = "gray-release";
    private String setName;

    public SetName(String setName) {
        this.setName = setName;
    }

    public Boolean isDefault() {
        return StringUtils.isEmpty(setName);
    }

    public Boolean isLiteSet() {
        return !isDefault() && setName.startsWith(GRAY_RELEASE_SET_TAG);
    }

    public Boolean isSet() {
        return !isDefault() && !setName.startsWith(GRAY_RELEASE_SET_TAG);
    }
}
