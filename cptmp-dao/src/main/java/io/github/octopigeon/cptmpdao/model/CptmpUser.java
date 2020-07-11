package io.github.octopigeon.cptmpdao.model;


import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author anlow
 * @version 1.2
 * @date 2020/7/7
 *
 * Gravatar 支持
 * @last-check-in Eric_Lian
 * @date 2020/7/11
 */
@Data
public class CptmpUser {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private BigInteger id;
    private Date gmtCreate;
    private String username;
    private String nickname;

    @Setter(AccessLevel.PRIVATE)
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

    /** nullable */
    private String invitationCode;

    public CptmpUser updatePassword(String password) {
        this.password = ENCODER.encode(password);
        return this;
    }

    /**
     * Gravatar 支持
     *
     * @return 头像地址
     */
    public String getAvatar(){
        if (avatar.length() > 0) {
            return avatar;
        } else {
            // 小写的邮箱地址 MD5
            String emailMD5 = DigestUtils.md5DigestAsHex(email.getBytes()).toLowerCase();

            // d 默认头像类型
            // s 图像大小
            // r 限制级
            String format = "https://www.gravatar.com/avatar/" + emailMD5 + "/?d=mp&s=60&r=g";

            return format;
        }
    }
}
