package io.github.octopigeon.cptmpservice.dto.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author 陈若琳
 * @version 1.0
 * @date 2020/07/20
 * @last-check-in 陈若琳
 * @date 2020/07/20
 */
@Data
public class TeamInfoDTO {
    private BigInteger id;

    @JsonProperty("train_id")
    private BigInteger trainId;
    @JsonProperty("train_name")
    private String trainName;

    @JsonProperty("project_id")
    private BigInteger projectId;
    @JsonProperty("project_name")
    private String projectName;
    private String name;
    private String avatar;

    @JsonProperty("repo_url")
    private String repoUrl;

    @JsonProperty("team_grade")
    private Integer teamGrade;
    private String evaluation;
    @JsonIgnore
    private BigInteger projectTrainId;

    @JsonProperty("team_master_id")
    private BigInteger teamMasterId;
    @JsonProperty("team_master")
    private String teamMaster;
    private int size;
}
