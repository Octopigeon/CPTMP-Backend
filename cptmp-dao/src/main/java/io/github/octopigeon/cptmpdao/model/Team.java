package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/9
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/9
 */
@Data
public class Team {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;

    private String name;
    private String avatar;
    private BigInteger projectId;
    private String repoUrl;
    private String evaluation;
    private Integer teamGrade;
}
