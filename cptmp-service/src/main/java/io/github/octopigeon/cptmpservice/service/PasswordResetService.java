package io.github.octopigeon.cptmpservice.service;

import io.github.octopigeon.cptmpdao.mapper.PasswordResetTokenMapper;
import io.github.octopigeon.cptmpdao.model.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in anlow
 * @date 2020/7/9
 */
@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenMapper passwordResetTokenMapper;

    public void createPasswordResetTokenForUser(String userEmail) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setGmtCreate(new Date());
        passwordResetToken.setToken(token);
        passwordResetToken.setEmail(userEmail);
        passwordResetTokenMapper.addPasswordResetToken(passwordResetToken);
    }

    /**
     * 根据用户提供的token，查找token，验证前端提交的邮箱
     * @param token 前端提交的token
     * @param email 前端提交的用户email
     * @return 是否验证成功
     */
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

    public boolean checkIsTokenExpired(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenMapper
                .findPasswordResetTokenByToken(token);
        if (passwordResetToken == null) {
            return false;
        }
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
