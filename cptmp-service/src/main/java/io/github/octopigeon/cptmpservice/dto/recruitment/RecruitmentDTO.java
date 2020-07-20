package io.github.octopigeon.cptmpservice.dto.recruitment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/20
 * @last-check-in Gh Li
 * @date 2020/7/20
 */
@Data
public class RecruitmentDTO {
    private BigInteger id;
    @JsonProperty("start_time")
    private Date startTime;
    @JsonProperty("end_time")
    private Date endTime;
    private String photo;
    private String title;
    @JsonProperty("website_url")
    private String websiteUrl;
}
