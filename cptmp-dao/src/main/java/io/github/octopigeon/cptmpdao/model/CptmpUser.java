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

    /**
     * 默认头像
     */
    final String DEFAULT_AVATAR = ""; //TODO 填入默认头像地址

    /**
     * Gravatar 地址
     */
    // final String GRAVATAR_ROOT = "https://www.gravatar.com/avatar/"; // 原版
    final String GRAVATAR_ROOT = "https://cdn.v2ex.com/gravatar/"; // V2EX 镜像站


    /**
     * Gravatar 支持
     *
     * @return 头像地址
     */
    public String getAvatar(){
        if ((avatar == null) || (avatar.length() > 0)) {
            return avatar;
        } else {
            try {
                if ((email == null) || (email.length() == 0)) {
                    return DEFAULT_AVATAR;
                }

                // 小写的邮箱地址 MD5
                String emailMD5 = DigestUtils.md5DigestAsHex(email.getBytes()).toLowerCase();

                // 默认头像设置
                String default_avatar = DEFAULT_AVATAR.equals("") ? "" : URLEncoder.encode(DEFAULT_AVATAR, "UTF-8");

                // d 默认头像类型
                // s 图像大小
                // r 限制级
                return GRAVATAR_ROOT + emailMD5 + "/?d="+default_avatar+"&s=128&r=g";

            } catch (Exception e) {
                return DEFAULT_AVATAR;
            }
        }
    }
}
