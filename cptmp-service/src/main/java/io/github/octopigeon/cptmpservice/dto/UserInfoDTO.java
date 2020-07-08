package io.github.octopigeon.cptmpservice.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 带有必要信息的UserDTO
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in Gh Li
 * @date 2020/7/8
 */
@Data
public class UserInfoDTO{
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
    private BigDecimal phoneNum;
    /**
     * 0-female, 1-male
     */
    private Boolean gender;
}
