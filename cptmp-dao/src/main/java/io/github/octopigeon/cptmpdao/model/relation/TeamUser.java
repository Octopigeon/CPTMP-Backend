package io.github.octopigeon.cptmpdao.model.relation;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 魏啸冲
 * @date 2020/7/14
 */
@Data
public class TeamUser {

    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;

    private BigInteger teamId;
    private BigInteger userId;
    private BigDecimal personalGrade;
    private String evaluation;

}
