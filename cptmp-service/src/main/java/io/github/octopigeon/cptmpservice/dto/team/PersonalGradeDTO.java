package io.github.octopigeon.cptmpservice.dto.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/15
 * @last-check-in Gh Li
 * @date 2020/7/15
 */
@Data
public class PersonalGradeDTO {
    private BigInteger id;
    @JsonProperty("team_id")
    private BigInteger teamId;
    @JsonProperty("user_id")
    private BigInteger userId;
    @JsonProperty("personal_grade")
    private Integer personalGrade;
    @JsonProperty("evaluation")
    private String evaluation;
    @JsonIgnore
    private BigInteger teamPersonId;
}
