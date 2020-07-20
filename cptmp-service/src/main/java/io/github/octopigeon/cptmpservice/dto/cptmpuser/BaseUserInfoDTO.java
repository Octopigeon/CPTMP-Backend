package io.github.octopigeon.cptmpservice.dto.cptmpuser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;

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

    /**
     * 默认头像
     */
    final String DEFAULT_AVATAR = "49.235.232.153/assets/avatar.png";

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
                String default_avatar = DEFAULT_AVATAR.equals("") ? "" : URLEncoder.encode(DEFAULT_AVATAR, "UTF-8");

                // d 默认头像类型
                // s 图像大小
                // r 限制级
                return GRAVATAR_ROOT + emailMD5 + "/?d="+default_avatar+"&s=512&r=g";

            } catch (Exception e) {
                return DEFAULT_AVATAR;
            }
        }
    }

}
