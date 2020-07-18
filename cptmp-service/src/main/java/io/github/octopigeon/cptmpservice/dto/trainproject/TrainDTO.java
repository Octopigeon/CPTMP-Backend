package io.github.octopigeon.cptmpservice.dto.trainproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/18
 */
@Data
public class TrainDTO {
    private BigInteger id;

    @JsonProperty("organization_id")
    private BigInteger organizationId;
    private String name;

    @JsonProperty("start_time")
    private Date startTime;

    @JsonProperty("end_time")
    private Date endTime;
    private String content;

    @JsonProperty("accept_standard")
    private String acceptStandard;

    @JsonProperty("resource_library")
    private String resourceLibrary;

    @JsonProperty("gps_info")
    private String gpsInfo;

    private Double limits;
}
