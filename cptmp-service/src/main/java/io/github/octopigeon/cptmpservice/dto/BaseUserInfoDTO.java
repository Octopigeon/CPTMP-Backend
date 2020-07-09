package io.github.octopigeon.cptmpservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 带有必要信息的UserDTO
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in Gh Li
 * @date 2020/7/8
 */
@Data
public abstract class BaseUserInfoDTO{
    /**
     * 必须进行导入的属性
     */
    private String email;
    private String roleName;
    /**
     * 可产生默认值，也可导入的属性
     */
    private String userName;
    private String password;

    /** 默认为Null */
    private BigDecimal phoneNum;
    /** 0-female, 1-male 默认为Null */
    private Boolean gender;

    /** 返回携带用户账号 */
    private BigInteger userId;
    /** 返回携带邀请码 */
    private String invitationCode;
}
