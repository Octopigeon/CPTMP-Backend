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
 * last-check-in 魏啸冲
 * @date 2020/7/14
 */
@Data
public class Team {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;

    private String name;
    private String avatar;
    /** 实训-项目表中的关系id */
    private BigInteger projectTrainId;
    private String repoUrl;
    private String evaluation;
    private Integer teamGrade;
    private BigInteger teamMasterId;
    /** github用户名 */
    private String githubUsername;
    /** github token */
    private String githubToken;
}
