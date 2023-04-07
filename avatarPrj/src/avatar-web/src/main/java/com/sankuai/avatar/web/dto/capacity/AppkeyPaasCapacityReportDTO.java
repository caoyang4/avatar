package com.sankuai.avatar.web.dto.capacity;

import lombok.Data;

import java.util.List;

/**
 * paas上报的 paas dto
 * @author caoyang
 * @create 2022-10-13 15:34
 */

@Data
public class AppkeyPaasCapacityReportDTO {
    /**
     * PaaS 名称: Eagle, S3, Mafka, Squirrel, ZK, RDS, Oceanus...
     */
    private String paasName;

    /**
     * paas 容灾的 dto
     */
    private List<AppkeyPaasCapacityDTO> appkeyPaasCapacityDTOList;

    /**
     * appkey 应用的 paas 客户端容灾的 dto
     */
    private List<AppkeyPaasClientDTO> appkeyPaasClientDTOList;

    /**
     * paas 容灾达标客户端
     */
    private List<AppkeyPaasStandardClientDTO> appkeyPaasStandardClientDTOList;
}
