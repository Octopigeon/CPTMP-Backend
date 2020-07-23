package io.github.octopigeon.cptmpservice.dto.organization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * 组织类，在本系统中主要对应学校
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/13
 * @last-check-in 李国豪
 * @date 2020/7/23
 */
@Data
public class OrganizationDTO {
    /** 组织唯一标识符 */
    private BigInteger id;
    /** 组织的创建时间 */
    @JsonProperty("gmt_create")
    private Date gmtCreate;
    /** 组织的简称 如：WHU */
    private String name;
    /** 组织的全称 如：武汉大学 */
    @JsonProperty("real_name")
    private String realName;
    /** 组织的相关描述 */
    private String description;
    /** 组织的官网 */
    @JsonProperty("website_url")
    private String websiteUrl;
    /** 组织的邀请码 由service层自动生成 */
    @JsonIgnore
    private String invitationCode;
}
