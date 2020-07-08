package io.github.octopigeon.cptmpservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in Gh Li
 * @date 2020/7/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EnterpriseAdminInfoDTO extends UserInfoDTO{

    private String name;
    private BigInteger employeeId;
}
