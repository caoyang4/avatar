package com.sankuai.avatar.sdk.entity.servicecatalog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Data;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-03-01
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregatedUsers {
    private Org org;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    List<User> users;
}

