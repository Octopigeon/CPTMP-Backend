package io.github.octopigeon.cptmpservice.dto.cptmpuser;

import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in Gh Li
 * @date 2020/7/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EnterpriseAdminInfoDTO extends BaseUserInfoDTO {

    private String name;
    private String employeeId;
}
