package io.github.octopigeon.cptmpservice.dto.cptmpuser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;

/**
 * 带有必要信息的UserDTO类
 * @author 李国豪
 * @version 1.1
 * @date 2020/7/8
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Data
public class BaseUserInfoDTO{
    /** 邮件地址 */
    private String email;
    /** 权限名称,内容可以参考RoleEnum */
    private String roleName;
    /** 用户名,service层自动生成 */
    private String username;
    /** 真实姓名 */
    private String name;
    /** 用户密码 */
    @JsonIgnore
    private String password;
    /** 头像url */
    private String avatar;
    /** 学号，工号等 */
    private String commonId;
    /** 组织Id */
    private BigInteger organizationId;
    /** 默认为Null */
    private BigDecimal phoneNumber;
    /** 0-female, 1-male 默认为Null */
    private Boolean gender;
    /** 个人介绍 */
    private String introduction;
    /** 返回携带用户账号 */
    @JsonProperty("user_id")
    private BigInteger id;


    @Value("domain.name")
    private String domain;
    /** 默认头像 */
    final String DEFAULT_AVATAR = domain+"/assets/avatar.png";

    /** Gravatar 地址：V2EX镜像站*/
    final String GRAVATAR_ROOT = "https://cdn.v2ex.com/gravatar/";
    // final String GRAVATAR_ROOT = "https://www.gravatar.com/avatar/"; // 原版

    /**
     * Gravatar 支持
     * @return 头像地址
     */
    public String getAvatar(){
        if ((avatar != null) && (avatar.length() > 0)) {
            return avatar;
        } else {
            try {
                if ((email == null) || (email.length() == 0)) {
                    return DEFAULT_AVATAR;
                }

                // 小写的邮箱地址 MD5
                String emailMD5 = DigestUtils.md5DigestAsHex(email.getBytes()).toLowerCase();

                // 默认头像设置
                String defaultAvatar = "".equals(DEFAULT_AVATAR) ? "" : URLEncoder.encode(DEFAULT_AVATAR, "UTF-8");

                // d 默认头像类型
                // s 图像大小
                // r 限制级
                return GRAVATAR_ROOT + emailMD5 + "/?d="+defaultAvatar+"&s=512&r=g";

            } catch (Exception e) {
                return DEFAULT_AVATAR;
            }
        }
    }

}
