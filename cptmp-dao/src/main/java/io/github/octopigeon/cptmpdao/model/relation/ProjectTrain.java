package io.github.octopigeon.cptmpdao.model.relation;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/14
 * 实训-项目表,project_train
 * @last-check-in 魏啸冲
 * @date 2020/7/14
 */
@Data
public class ProjectTrain {

    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;

    private BigInteger trainId;
    private BigInteger projectId;

}
