package io.github.octopigeon.cptmpservice.dto.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/15
 * @last-check-in 李国豪
 * @date 2020/7/15
 */
@Data
public class PersonalGradeDTO {
    private BigInteger id;
    private BigInteger teamId;
    private BigInteger userId;
    private Integer personalGrade;
    private String evaluation;
    @JsonIgnore
    private BigInteger teamPersonId;
}
