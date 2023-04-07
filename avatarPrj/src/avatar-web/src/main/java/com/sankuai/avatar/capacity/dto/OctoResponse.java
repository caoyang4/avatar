package com.sankuai.avatar.capacity.dto;

import java.util.List;

/**
 * Created by shujian on 2020/2/12.
 */

public class OctoResponse {
    public List<OctoDataResponse> getData() {
        return data;
    }

    public void setData(List<OctoDataResponse> data) {
        this.data = data;
    }

    private List<OctoDataResponse> data;
}



