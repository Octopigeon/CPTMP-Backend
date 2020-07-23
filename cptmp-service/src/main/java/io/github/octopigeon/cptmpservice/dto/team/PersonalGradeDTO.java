package io.github.octopigeon.cptmpservice.dto.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * 个人成绩相关信息的dto
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/15
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Data
public class PersonalGradeDTO {
    /** 个人成绩唯一标识符 */
    private BigInteger id;
    /** 所属团队的唯一标识Id */
    @JsonProperty("team_id")
    private BigInteger teamId;
    /** 所属用户的唯一标识Id */
    @JsonProperty("user_id")
    private BigInteger userId;
    /** 对学生的评价 */
    private String evaluation;
    /** 管理成绩 */
    @JsonProperty("manage_point")
    private Integer managePt;
    /** 代码成绩 */
    @JsonProperty("code_point")
    private Integer codePt;
    /** 技术成绩 */
    @JsonProperty("tech_point")
    private Integer techPt;
    /** 架构成绩 */
    @JsonProperty("framework_point")
    private Integer frameworkPt;
    /** 沟通成绩 */
    @JsonProperty("communication_point")
    private Integer communicationPt;
    /** 团队个人关联ID */
    @JsonIgnore
    private BigInteger teamPersonId;
}
