package io.github.octopigeon.cptmpweb.servicetest;

import io.github.octopigeon.cptmpservice.service.otherservice.EmailService;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in 魏啸冲
 * @date 2020/7/9
 */
public class EmailServiceTest extends BaseTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void test() {
        // 测试人员需要测试时请填写自己邮箱
        //emailService.sendSimpleMessage("xxx@xx.xx", "test", "hello");
    }

}
