package com.sankuai.avatar.dao.es.request.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 审核决策
 *
 * @author zhaozhifan02
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Decision {

    @SerializedName("run_decision")
    private List<String> runDecision;


}
