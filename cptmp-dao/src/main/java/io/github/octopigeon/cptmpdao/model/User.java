package io.github.octopigeon.cptmpdao.model;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.util.Date;

/**
 * 用户模型
 *
 * @author anlow, Eric_Lian
 * @version 1.1
 * @date 2020/7/7
 */
@Data
public class User {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

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

    public User setPassword(String password) {
        this.password = ENCODER.encode(password);
        return this;
    }

    public boolean validatePassword(String submittedPassword) {
        boolean ret = ENCODER.matches(submittedPassword, this.password);
        if (ret) {
            this.setPassword(submittedPassword);
        }
        return ret;
    }

}
