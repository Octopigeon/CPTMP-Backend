package io.github.octopigeon.cptmpweb.servicetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import io.github.octopigeon.cptmpweb.BaseTest;
import io.github.octopigeon.cptmpweb.bean.RespBean;
import okhttp3.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in anlow
 * @date 2020/7/9
 */
public class LoginServiceTest extends BaseTest {

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Test
    public void loginTest() {
        // 创建用户基本信息
        String username = "test_user";
        String password = "123";
        // 正常用户
        CptmpUser cptmpUser = new CptmpUser();
        cptmpUser.setGmtCreate(new Date());
        cptmpUser.setUsername(username);
        cptmpUser.updatePassword(password);
        cptmpUser.setEmail("111@11.com");
        cptmpUser.setRoleName("ROLE_STUDENT_MEMBER");
        cptmpUser.setEnabled(true);
        cptmpUser.setAccountNonExpired(true);
        cptmpUser.setCredentialsNonExpired(true);
        cptmpUser.setAccountNonLocked(true);
        // 加入数据库
        cptmpUserMapper.addUser(cptmpUser);
        // 验证登录
        attemptLogin(username, password, CptmpStatusCode.OK);
        attemptLogout();
        // 测试Enabled
        cptmpUserMapper.updateEnabledByUsername(username, false);
        attemptLogin(username, password, CptmpStatusCode.AUTH_FAILED_ACCOUNT_STATUS_ERROR);
        attemptLogout();
        cptmpUserMapper.updateEnabledByUsername(username, true);
        // 测试AccountNonExpired
        cptmpUserMapper.updateAccountNonExpiredByUsername(username, false);
        attemptLogin(username, password, CptmpStatusCode.AUTH_FAILED_ACCOUNT_EXPIRED);
        attemptLogout();
        cptmpUserMapper.updateAccountNonExpiredByUsername(username, true);
        // 测试CredentialsNonExpired
        cptmpUserMapper.updateCredentialsNonExpiredByUsername(username, false);
        attemptLogin(username, password, CptmpStatusCode.AUTH_FAILED_CREDENTIALS_EXPIRED);
        attemptLogout();
        cptmpUserMapper.updateCredentialsNonExpiredByUsername(username, true);
        // 测试AccountNonLocked
        cptmpUserMapper.updateAccountNonLockedByUsername(username, false);
        attemptLogin(username, password, CptmpStatusCode.AUTH_FAILED_ACCOUNT_STATUS_ERROR);
        attemptLogout();
        cptmpUserMapper.updateAccountNonLockedByUsername(username, true);
    }

    private void attemptLogin(String username, String password, Integer expectedStatus) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"username\": \"" + username + "\",\r\n    \"password\": \"" + password + "\"\r\n}");
            Request request = new Request.Builder()
                    .url("http://localhost:12345/login")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Cookie", "JSESSIONID=EEA840E20A642AA5AC239B78FC3FFF58")
                    .build();
            Response response = client.newCall(request).execute();
            RespBean respBean = new ObjectMapper().readerFor(RespBean.class).readValue(Objects.requireNonNull(response.body()).byteStream());
            Assertions.assertEquals(expectedStatus, respBean.getStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void attemptLogout() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("http://localhost:12345/logout")
                    .method("POST", body)
                    .addHeader("Cookie", "JSESSIONID=EEA840E20A642AA5AC239B78FC3FFF58")
                    .build();
            Response response = client.newCall(request).execute();
            RespBean respBean = new ObjectMapper().readerFor(RespBean.class).readValue(Objects.requireNonNull(response.body()).byteStream());
            Assertions.assertEquals(CptmpStatusCode.ACCESS_DENY_NOT_LOGIN, respBean.getStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
