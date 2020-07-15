package io.github.octopigeon.cptmpservice.dto.team;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in Gh Li
 * @date 2020/7/14
 */
@Data
public class TeamDTO {
    private BigInteger id;
    private BigInteger trainId;
    private BigInteger projectId;
    private BigInteger name;
    private String avatar;
    private String codeUrl;
    private BigDecimal teamGrade;
    private String evaluation;
}
