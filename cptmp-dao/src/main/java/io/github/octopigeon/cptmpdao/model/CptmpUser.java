package io.github.octopigeon.cptmpdao.model;


import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/7
 *
 * @last-check-in anlow
 * @date 2020/7/8
 */
@Data
public class CptmpUser {

    private BigInteger id;
    private Date gmtCreate;
    private String username;
    private String password;
    private String email;
    private String roleName;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;
    /** nullable */
    private Date gmtModified;
    /** nullable */
    private String introduction;
    /** nullable */
    private BigDecimal phoneNumber;
    /**
     * 0-female, 1-male
     * nullable
     */
    private Boolean male;
    /** nullable */
    private String avatar;

}
