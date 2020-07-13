package io.github.octopigeon.cptmpservice.service.passwordtoken;

import io.github.octopigeon.cptmpdao.mapper.PasswordResetTokenMapper;
import io.github.octopigeon.cptmpdao.model.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in 李国豪
 * @date 2020/7/9
 */
@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private PasswordResetTokenMapper passwordResetTokenMapper;

    /**
     * 为用户创建修改密码的token
     * @param userEmail 用户邮箱
     */
    @Override
    public String createPasswordResetTokenForUser(String userEmail) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        // 设置邮箱基本信息，创建时间（用于比对是否过期），token，对应的用户邮箱
        passwordResetToken.setGmtCreate(new Date());
        passwordResetToken.setToken(token);
        passwordResetToken.setEmail(userEmail);
        passwordResetTokenMapper.addPasswordResetToken(passwordResetToken);
        return token;
    }

    /**
     * 根据用户提供的token，查找token，验证前端提交的邮箱
     * @param token 前端提交的token
     * @param email 前端提交的用户email
     * @return 是否验证成功
     */
    @Override
    public boolean authToken(String token, String email) {
        PasswordResetToken passwordResetToken = passwordResetTokenMapper
                .findPasswordResetTokenByToken(token);
        if (passwordResetToken == null) {
            return false;
        }
        if (passwordResetToken.getEmail().equals(email) && checkIsTokenExpired(token)) {
            // 验证成功，删除该邮箱所有的token
            passwordResetTokenMapper.removePasswordResetTokensByEmail(email);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查token是否过期
     * @param token token值
     * @return 是否过期
     */
    @Override
    public boolean checkIsTokenExpired(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenMapper
                .findPasswordResetTokenByToken(token);
        if (passwordResetToken == null) {
            return false;
        }
        // 注意，这里测试的时候有时区问题
        long delay = System.currentTimeMillis() - passwordResetToken.getGmtCreate().getTime();
        long maxTokenLiveTime = 5 * 60 * 1000L;
        if (delay < maxTokenLiveTime) {
            return true;
        } else {
            passwordResetTokenMapper.removePasswordResetTokenByToken(token);
            return false;
        }
    }

}
