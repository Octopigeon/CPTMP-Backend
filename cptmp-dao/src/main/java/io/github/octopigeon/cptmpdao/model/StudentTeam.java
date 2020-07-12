package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author 陈若琳
 * @version 1.0
 * @date 2020/07/12
 * @last-check-in 陈若琳
 * @date 2020/07/12
 */
@Data
public class StudentTeam {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;
    private BigInteger userId;
    private BigInteger teamId;
    private BigDecimal studentGrade;
}
