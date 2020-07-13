package io.github.octopigeon.cptmpservice.service.passwordtoken;

import org.springframework.stereotype.Service;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/11
 * @last-check-in 李国豪
 * @date 2020/7/11
 */
@Service
public interface PasswordResetService {
    /**
     * 为用户创建修改密码的token
     * @param userEmail 用户邮箱
     * @return 返回生成的token
     */
    String createPasswordResetTokenForUser(String userEmail);

    /**
     * 根据用户提供的token，查找token，验证前端提交的邮箱
     * @param token 前端提交的token
     * @param email 前端提交的用户email
     * @return 是否验证成功
     */
    boolean authToken(String token, String email);

    /**
     * 检查token是否过期
     * @param token token值
     * @return 是否过期
     */
    boolean checkIsTokenExpired(String token);
}
