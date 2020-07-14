package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.TeamMapper;
import io.github.octopigeon.cptmpdao.mapper.TokenMapper;
import io.github.octopigeon.cptmpdao.model.Token;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/14
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/14
 */
public class TokenMapperTest extends BaseTest {

    @Autowired
    private TokenMapper tokenMapper;
    @Test
    public void test(){
        Token token1 = new Token();
        token1.setGmtCreate(new Date());
        token1.setToken("test1");
        token1.setEmail("test1");

        Token token2 = new Token();
        token2.setGmtCreate(new Date());
        token2.setToken("test2");
        token2.setEmail("test2");

        tokenMapper.removeAllTokenTest();
        tokenMapper.addToken(token1);
        tokenMapper.addToken(token2);
        Assertions.assertEquals(2,tokenMapper.findAllToken().size());

        tokenMapper.hideTokenById(tokenMapper.findAllToken().get(0).getId(),new Date());
        Assertions.assertEquals(1,tokenMapper.findAllToken().size());

        Token token3 = token2;
        token3.setEmail("test3");
        tokenMapper.updateTokenById(token3);
        Assertions.assertEquals("test3",tokenMapper.findAllToken().get(0).getEmail());

        tokenMapper.restoreAllToken();
        Assertions.assertEquals(2,tokenMapper.findAllToken().size());

    }
}
