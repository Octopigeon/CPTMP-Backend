package io.github.octopigeon.cptmpservice.dto.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/15
 * @last-check-in 李国豪
 * @date 2020/7/20
 */
@Data
public class PersonalGradeDTO {
    private BigInteger id;
    @JsonProperty("team_id")
    private BigInteger teamId;
    @JsonProperty("user_id")
    private BigInteger userId;
    @JsonProperty("evaluation")
    private String evaluation;
    @JsonProperty("manage_point")
    private Integer managePt;
    @JsonProperty("code_point")
    private Integer codePt;
    @JsonProperty("tech_point")
    private Integer techPt;
    @JsonProperty("framework_point")
    private Integer frameworkPt;
    @JsonProperty("communication_point")
    private Integer communicationPt;
    @JsonIgnore
    private BigInteger teamPersonId;
}
