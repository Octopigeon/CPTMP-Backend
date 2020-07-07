package io.github.octopigeon.cptmpdao.model;

import lombok.Data;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/7
 */
@Data
public class Authority {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private BigInteger name;
    private String operation;
    private String tableName;
}
