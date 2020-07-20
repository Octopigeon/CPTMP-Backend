package io.github.octopigeon.cptmpdao.model;


import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author 魏啸冲
 * @version 2.0
 * @date 2020/7/7
 *
 * Gravatar 支持
 * @last-check-in 李国鹏
 * @date 2020/7/14
 */
@Data
public class CptmpUser {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;
    private String name;
    private String username;
    private String commonId;
    private BigInteger organizationId;


    @Setter(AccessLevel.PRIVATE)
    private String password;

    private String email;
    /**
     * ROLE_SYSTEM_ADMIN, 系统管理员
     * ROLE_ENTERPRISE_ADMIN, 企业管理员
     * ROLE_SCHOOL_ADMIN, 学校管理员老师
     * ROLE_SCHOOL_TEACHER, 学校普通老师
     * ROLE_STUDENT_MEMBER, 普通学生
     */
    private String roleName;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;
    /** nullable */
    /** nullable */
    /** nullable */
    private BigDecimal phoneNumber;
    private String introduction;
    /**
     * 0-female, 1-male null-保密
     * nullable
     */
    private Boolean gender;

    /** nullable */
    private String avatar;

    public CptmpUser updatePassword(String password) {
        this.password = ENCODER.encode(password);
        return this;
    }

}
