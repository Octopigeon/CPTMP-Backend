package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/15
 * @last-check-in 魏啸冲
 * @date 2020/7/15
 */
@Data
public class PersonalGrade {

    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;

    private BigInteger teamPersonId;
    private String evaluation;
    private Integer personalGrade;

}
