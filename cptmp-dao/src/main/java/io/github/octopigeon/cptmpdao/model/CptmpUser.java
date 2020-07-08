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
    /**
     * ROLE_ENTERPRISE_ADMIN, 企业管理员
     * ROLE_SCHOOL_ADMIN, 学校管理员老师
     * ROLE_SCHOOL_TEACHER, 学校普通老师
     * ROLE_STUDENT_MEMBER, 普通学生
     * ROLE_STUDENT_MASTER, 项目经理学生
     * ROLE_STUDENT_PM, 技术主管学生
     * ROLE_STUDENT_PO, 产品经理学生
     */
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
