package io.github.octopigeon.cptmpservice.dto.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author 陈若琳
 * @version 1.0
 * @date 2020/07/20
 * @last-check-in 李国豪
 * @date 2020/07/23
 */
@Data
public class TeamDTO {
    /** 团队唯一标识Id */
    private BigInteger id;
    /** 团队名称 */
    private String name;
    /** 团队头像 */
    private String avatar;
    /** 团队的github代码仓库 */
    @JsonProperty("repo_url")
    private String repoUrl;
    /** 团队成绩 */
    @JsonProperty("team_grade")
    private Integer teamGrade;
    /** 团队评价 */
    private String evaluation;
    /** 所属实训的唯一标识Id */
    @JsonProperty("train_id")
    private BigInteger trainId;
    /** 所属实训的实训名称 */
    @JsonProperty("train_name")
    private String trainName;
    /** 实训项目的唯一标识符 */
    @JsonProperty("project_id")
    private BigInteger projectId;
    /** 实训项目的名称 */
    @JsonProperty("project_name")
    private String projectName;
    /** 团队队长的唯一标识Id */
    @JsonProperty("team_master_id")
    private BigInteger teamMasterId;
    /** 团队队长的名字 */
    @JsonProperty("team_master_name")
    private String teamMasterName;
    /** 团队人员数量 包括老师和学生 */
    private Integer size;
    /** 团队所选实训项目关联Id */
    @JsonIgnore
    private BigInteger projectTrainId;
}
