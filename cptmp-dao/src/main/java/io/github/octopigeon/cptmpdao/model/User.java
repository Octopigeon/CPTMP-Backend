package io.github.octopigeon.cptmpdao.model;


import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/7
 */
@Data
public class User {

    private BigInteger id;
    private Date gmtCreate;
    /** nullable */
    private Date gmtModified;
    private String username;
    private String password;
    /** nullable */
    private String introduction;
    /** nullable */
    private String contactInfo;
    /**
     * 0-female, 1-male
     * nullable
     */
    private Boolean male;
    /** nullable */
    private String avatar;

}
