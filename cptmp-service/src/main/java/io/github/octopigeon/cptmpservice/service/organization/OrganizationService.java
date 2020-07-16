package io.github.octopigeon.cptmpservice.service.organization;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.service.basenormalservice.BaseNormalService;
import org.springframework.stereotype.Service;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/13
 * @last-check-in 李国豪
 * @date 2020/7/13
 */
@Service
public interface OrganizationService extends BaseNormalService<OrganizationDTO> {

    /**
     * 分页查询所有组织
     * @param page 页号
     * @param offset 页偏移
     * @return
     */
    PageInfo<OrganizationDTO> findAll(int page, int offset);

    /**
     * 使用组织名进行模糊查询
     * @param name 组织的名称
     * @return 组织相关信息
     */
    OrganizationDTO findByName(String name);

    /**
     * 根据组织全名进行模糊查询
     * @param realName 组织全名
     * @return
     */
    OrganizationDTO findByRealName(String realName);

    /**
     * 根据邀请码进行查询
     * @param code 邀请码
     * @return
     */
    OrganizationDTO findByInvitationCode(String code);
}
