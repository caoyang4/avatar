package com.sankuai.avatar.capacity.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;

/**
 * Created by shujian on 2020/2/27.
 *
 */
@Data
public class OceanusAppkeyResultResponse {
    @SerializedName("site")
    private ArrayList<?> site;
}
