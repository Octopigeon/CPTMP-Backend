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
 * @date 2020/7/23
 */
@Data
public class TrainDTO {
    /** 实训的唯一标识符 */
    private BigInteger id;
    /** 实训合作组织的唯一标识Id */
    @JsonProperty("organization_id")
    private BigInteger organizationId;
    /** 实训的名称 */
    private String name;
    /** 实训开始时间 */
    @JsonProperty("start_time")
    private Date startTime;
    /** 实训结束时间 */
    @JsonProperty("end_time")
    private Date endTime;
    /** 实训内容简介 */
    private String content;
    /** 实训的评价标准 */
    @JsonProperty("accept_standard")
    private String acceptStandard;
    /** 实训的资源库 */
    @JsonProperty("resource_library")
    private String resourceLibrary;
    /** 实训位置的gps信息，主要包括经度和纬度 */
    @JsonProperty("gps_info")
    private String gpsInfo;
    /** 签到范围的定义 */
    private Double limits;
}
