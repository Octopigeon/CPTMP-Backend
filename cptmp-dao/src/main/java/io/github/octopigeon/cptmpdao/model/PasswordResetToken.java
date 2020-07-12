package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in 魏啸冲
 * @date 2020/7/12
 */
@Data
public class PasswordResetToken {

    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    /** 密码重置token，24h有效 */
    private String token;
    /** 用户邮箱，与用户一一对应 */
    private String email;

}
