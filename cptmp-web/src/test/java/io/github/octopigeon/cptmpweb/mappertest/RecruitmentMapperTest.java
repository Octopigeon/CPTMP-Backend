package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.RecruitmentMapper;
import io.github.octopigeon.cptmpdao.model.Recruitment;
import io.github.octopigeon.cptmpservice.utils.Utils;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/20
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/20
 */
public class RecruitmentMapperTest extends BaseTest {
    @Autowired
    private RecruitmentMapper recruitmentMapper;

    @Test
    public void test(){
        Recruitment recruitment1 = new Recruitment();
        recruitment1.setGmtCreate(new Date());
        recruitment1.setStartTime(new Date());
        recruitment1.setEndTime(new Date());
        recruitment1.setTitle("test1");
        recruitment1.setWebsiteUrl("test1");

        //增加
        recruitmentMapper.removeAllRecruitmentTest();
        recruitmentMapper.addRecruitment(recruitment1);
        recruitmentMapper.addRecruitment(recruitment1);
        Assertions.assertEquals(2,recruitmentMapper.findAllRecruitment().size());
        Assertions.assertEquals(3, Utils.getNullPropertyNames(recruitmentMapper.findAllRecruitment().get(0)).length);

        //删除与恢复
        BigInteger restoreTestId = recruitmentMapper.findAllRecruitment().get(0).getId();
        recruitmentMapper.hideRecruitmentById(recruitmentMapper.findAllRecruitment().get(0).getId(),new Date());
        Assertions.assertEquals(1,recruitmentMapper.findAllRecruitment().size());
        recruitmentMapper.restoreRecruitmentById(restoreTestId);
        Assertions.assertEquals(2,recruitmentMapper.findAllRecruitment().size());

        //修改
        recruitment1 = recruitmentMapper.findAllRecruitment().get(1);
        recruitment1.setTitle("test2");
        recruitmentMapper.updateRecruitmentById(recruitment1);
        Assertions.assertEquals("test2", recruitmentMapper.findAllRecruitment().get(1).getTitle());

        //整体测试
        recruitmentMapper.hideRecruitmentByAll(new Date());
        Assertions.assertEquals(0,recruitmentMapper.findAllRecruitment().size());
        recruitmentMapper.restoreRecruitmentByAll();
        Assertions.assertEquals(2,recruitmentMapper.findAllRecruitment().size());

        Assertions.assertEquals("test1",recruitmentMapper.findRecruitmentById(recruitmentMapper.findAllRecruitment().get(0).getId()).getTitle());
    }
}
