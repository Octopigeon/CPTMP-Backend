package io.github.octopigeon.cptmpservice.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/17
 * @last-check-in 魏啸冲
 * @date 2020/7/17
 */
@Data
public class WeekContributionsDTO {

    @JsonProperty("w")
    private Date weekStartTime;
    @JsonProperty("a")
    private Long additions;
    @JsonProperty("d")
    private Long deletions;
    @JsonProperty("c")
    private Long commits;

}
