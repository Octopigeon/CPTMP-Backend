package io.github.octopigeon.cptmpservice.dto.organization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/13
 * @last-check-in 魏啸冲 加上JsonIgnore注释
 * @date 2020/7/13
 */
@Data
public class OrganizationDTO {
    @JsonIgnore
    private BigInteger id;
    private String name;
    private String realName;
    private String description;
    private String websiteUrl;
    @JsonIgnore
    private String invitationCode;
}
