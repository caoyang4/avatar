package com.sankuai.avatar.sdk.entity.servicecatalog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Org {
    private Integer id;

    private String name;

    /**
     * 上层归属节点
     */
    private List<Org> ancestors;

    /**
     * 下层子节点
     */
    private List<Org> children;

    private String displayName;

    private Boolean hasChild;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Org orgEntity = (Org) o;
        return Objects.equals(id, orgEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
