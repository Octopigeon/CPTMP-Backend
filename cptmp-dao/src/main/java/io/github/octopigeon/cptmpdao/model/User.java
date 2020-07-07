package io.github.octopigeon.cptmpdao.model;


import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author anlow
 */
@Data
public class User {

    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private String roleName;
    private String nickname;
    private String password;
    private String introduction;
    private String contactInfo;
    /**
     * 0-female, 1-male
     */
    private Boolean male;
    private String avatar;
    /**
     * use Json store authority id info
     */
    private String authorityIds;

}
