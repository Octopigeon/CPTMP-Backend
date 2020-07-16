package io.github.octopigeon.cptmpservice.dto.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/14
 */
@Data
public class TeamDTO {
    private BigInteger id;
    private BigInteger trainId;
    private BigInteger projectId;
    private String name;
    private String avatar;
    private String repoUrl;
    private Integer teamGrade;
    private String evaluation;
    @JsonIgnore
    private BigInteger projectTrainId;
}
