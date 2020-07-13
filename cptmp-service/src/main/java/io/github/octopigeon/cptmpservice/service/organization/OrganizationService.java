package io.github.octopigeon.cptmpservice.service.organization;

import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/13
 * @last-check-in Gh Li
 * @date 2020/7/13
 */
@Service
public interface OrganizationService extends BaseNormalService<OrganizationDTO> {
    /**
     * 使用组织名进行查询
     * @param name 组织的名称
     * @return 组织相关信息
     * @throws Exception
     */
    OrganizationDTO findByName(String name) throws Exception;
}
