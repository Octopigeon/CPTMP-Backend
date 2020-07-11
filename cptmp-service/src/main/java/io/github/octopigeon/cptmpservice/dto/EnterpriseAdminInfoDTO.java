package io.github.octopigeon.cptmpservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in anlow
 * @date 2020/7/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EnterpriseAdminInfoDTO extends BaseUserInfoDTO{

    private String name;
    @JsonProperty("common_id")
    private String employeeId;
}
