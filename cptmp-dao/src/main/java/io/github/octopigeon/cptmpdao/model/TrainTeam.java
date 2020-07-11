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
public class TrainTeam {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private BigInteger trainProjectId;
    private String teamName;
    private BigInteger masterUserId;
    private BigInteger pmUserId;
    private BigInteger poUserId;
    private String codeBaseUrl;
    private BigDecimal teamGrade;
}
