package io.github.octopigeon.cptmpservice.service;

import io.github.octopigeon.cptmpservice.dto.BaseUserInfoDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 单独注册与批量注册服务接口
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in Gh Li
 * @date 2020/7/8
 */
@Repository
public interface RegistrationService {

    /**
     * 验证邀请码
     * @param registrationRoleName：待注册的权限
     * @param invitationCode：邀请码
     * @return 验证码是否有效
     */
    Boolean validateInvitationCode(String registrationRoleName, String invitationCode);

    /**
     * 个体注册
     * @param userInfo：用户
     * @return 注册之后的用户信息
     */
    BaseUserInfoDTO personalRegistration(BaseUserInfoDTO userInfo) throws Exception;

    /**
     * 批量注册
     * @param userInfos：待注册列表
     * @return 注册好的列表
     */
    List<BaseUserInfoDTO> bulkRegistration(List<BaseUserInfoDTO> userInfos) throws Exception;
}
