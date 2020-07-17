package io.github.octopigeon.cptmpservice.dto.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 陈若琳
 * @date 2020/7/14
 */
@Data
public class TeamDTO {
    private BigInteger id;

    @JsonProperty("train_id")
    private BigInteger trainId;

    @JsonProperty("project_id")
    private BigInteger projectId;
    private String name;
    private String avatar;

    @JsonProperty("repo_url")
    private String repoUrl;

    @JsonProperty("team_grade")
    private Integer teamGrade;
    private String evaluation;
    @JsonIgnore
    private BigInteger projectTrainId;
}
