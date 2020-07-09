package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author anlow,Gh Li
 * @version 1.2
 * @date 2020/7/7
 */
@Data
public class SchoolInstructor {

    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private String name;
    private String schoolName;
    private BigInteger userId;
    private String employeeId;

}
