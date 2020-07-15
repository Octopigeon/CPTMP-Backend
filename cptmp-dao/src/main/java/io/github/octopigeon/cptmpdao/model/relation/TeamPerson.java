package io.github.octopigeon.cptmpdao.model.relation;

import lombok.Data;
import org.apache.ibatis.annotations.Insert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author 陈若琳
 * @version 1.0
 * @date 2020/07/12
 * @last-check-in 魏啸冲
 * @date 2020/07/14
 */
@Data
public class TeamPerson {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;
    private BigInteger userId;
    private BigInteger teamId;
    private Integer personalGrade;
    /** 老师对个人的评价 */
    private String evaluation;
}
