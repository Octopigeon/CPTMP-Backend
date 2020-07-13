package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.constantclass.EmailTemplate;
import io.github.octopigeon.cptmpservice.service.otherservice.EmailService;
import io.github.octopigeon.cptmpservice.service.passwordtoken.PasswordResetService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/13
 * 用于验证邮箱
 * @last-check-in anlow
 * @date 2020/7/13
 */
@RestController
public class ValidateController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetService passwordResetService;

    @Value("domain.name")
    private String domain;

    /**
     * 用于激活账户或者修改邮箱时验证是否成功
     * @param json 包含邮箱信息，token
     * @return 返回验证成功信息
     */
    @GetMapping("/api/user/token")
    public RespBean validateToken(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String email = objectMapper.readValue(json, ObjectNode.class).get("email").asText();
        String token = objectMapper.readValue(json, ObjectNode.class).get("token").asText();
        try {
            if (passwordResetService.authToken(token, email)) {
                return RespBean.ok("validate success");
            } else {
                return RespBean.error(CptmpStatusCode.EMAIL_VALIDATE_FAILED, "validate failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.EMAIL_VALIDATE_FAILED, "validate failed");
        }
    }

    /**
     * 创建用户token，并发送一封邮件到用户邮箱
     * @param json 包含用户邮箱
     * @return 返回是否创建token并发送邮件成功
     */
    @PostMapping("/api/user/token")
    public RespBean sendTokenEmail(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String email = objectMapper.readValue(json, ObjectNode.class).get("email").asText();
        try {
            String token = passwordResetService.createPasswordResetTokenForUser(email);
            String text = EmailTemplate.generateLink(domain, token, email);
            emailService.sendSimpleMessage(email, EmailTemplate.ACTIVATE_SUBJECT, text);
            return RespBean.ok("send token email success");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.SEND_TOKEN_EMAIL_FAILED, "send token email failed");
        }
    }


}
