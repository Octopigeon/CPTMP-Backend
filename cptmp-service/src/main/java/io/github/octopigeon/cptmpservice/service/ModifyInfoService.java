package io.github.octopigeon.cptmpservice.service;


import io.github.octopigeon.cptmpservice.dto.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.EnterpriseAdminInfoDTO;
import io.github.octopigeon.cptmpservice.dto.StudentInfoDTO;
import io.github.octopigeon.cptmpservice.dto.TeacherInfoDTO;
import org.springframework.stereotype.Repository;

/**
 * 更改个人信息服务接口
 * @author Ruby
 * @version 1.0
 * @date 2020/07/10
 * @last-check-in Ruby
 * @date 2020/07/10
 */


public interface ModifyInfoService {

    /**
     * 修改用户信息
     * @param baseUserInfo 修改后的用户信息
     * @return
     */
    int modifyUserInfo(BaseUserInfoDTO baseUserInfo) throws Exception;


}
