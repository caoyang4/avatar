package com.sankuai.avatar.client.ops.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author caoyang
 * @create 2023-02-01 10:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpsUser {

    @JsonProperty("username")
    private String mis;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("roles")
    private Map<String, Boolean> roles;

    @JsonProperty("org_path")
    private String orgPath;

    @JsonProperty("resigned")
    private Boolean resigned;

    @JsonProperty("org_id")
    private Integer orgId;

    @JsonProperty("leader")
    private String leader;

    @JsonProperty("email")
    private String email;

}
