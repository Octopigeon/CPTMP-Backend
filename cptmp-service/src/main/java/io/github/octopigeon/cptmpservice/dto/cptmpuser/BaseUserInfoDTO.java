package io.github.octopigeon.cptmpservice.dto.cptmpuser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 带有必要信息的UserDTO类
 * @author 李国豪
 * @version 1.1
 * @date 2020/7/8
 * @last-check-in 魏啸冲
 * @date 2020/7/11
 */
@Data
public class BaseUserInfoDTO{
    /**
     * 必须进行导入的属性
     */
    private String email;
    private String roleName;
    /**
     * 可产生默认值，也可导入的属性
     */
    private String username;
    /** 真实姓名 */
    private String name;

    @JsonIgnore
    private String password;

    /** 头像url */
    private String avatar;
    /** 学号，工号等 */
    private String commonId;
    /** 人脸数据url */
    //private String faceInfo;
    /** 组织Id */
    private BigInteger organizationId;
    /** 默认为Null */
    private BigDecimal phoneNumber;
    /** 0-female, 1-male 默认为Null */
    private Boolean gender;
    private String introduction;

    /** 返回携带用户账号 */
    @JsonProperty("user_id")
    private BigInteger id;
}
