package com.sankuai.avatar.capacity.dto;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-12-27 15:13
 */
@Data
public class DayuSetResponse {
    private Integer code;
    private String message;
    private CellBk data;

    @Data
    public static class CellBk {
        private String env;
        private String cell;
        private String az;
        private String backupCell;
    }
}
