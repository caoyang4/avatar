package com.sankuai.avatar.capacity.dto;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import java.util.Map;
/**
 * @author caoyang
 * @create 2022-08-17 16:59
 */
@Data
public class HulkScaleResponse {
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Map<String, ScaleState> scaleStates;

    @Data
    public static class ScaleState {
        private Boolean state = false;
        private String msg;
    }
}
