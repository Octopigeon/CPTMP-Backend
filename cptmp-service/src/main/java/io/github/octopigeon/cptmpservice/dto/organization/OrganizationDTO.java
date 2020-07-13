package io.github.octopigeon.cptmpservice.dto.organization;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/13
 * @last-check-in 李国豪
 * @date 2020/7/13
 */
@Data
public class OrganizationDTO {
    private BigInteger id;
    private String name;
    private String description;
    private String websiteUrl;
    private String invitationCode;
}
