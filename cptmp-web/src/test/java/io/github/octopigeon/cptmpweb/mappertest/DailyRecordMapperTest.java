package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.ActivityRecordMapper;
import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.DailyRecordMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.DailyRecord;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
/**
 * @author 李国鹏
 * @version 1.2
 * @date 2020/7/8
 *
 * last-check-in 李国鹏
 * @date 2020/7/10
 */
public class DailyRecordMapperTest extends BaseTest {
    @Autowired
    private DailyRecordMapper dailyRecordMapper;

    @Test
    public void test()
    {
        DailyRecord dailyRecord1 = new DailyRecord();
        dailyRecord1.setGmtCreate(new Date());
        dailyRecord1.setUserId(BigInteger.valueOf(1));
        dailyRecord1.setTeamId(BigInteger.valueOf(1));
        dailyRecord1.setRecordType(1);
        dailyRecord1.setDocumentPath("test1");
        dailyRecord1.setTitle("test1");
        dailyRecord1.setContent("test1");

        DailyRecord dailyRecord2 = new DailyRecord();
        dailyRecord2.setGmtCreate(new Date());
        dailyRecord2.setUserId(BigInteger.valueOf(2));
        dailyRecord2.setTeamId(BigInteger.valueOf(2));
        dailyRecord2.setRecordType(2);
        dailyRecord2.setDocumentPath("test2");
        dailyRecord2.setTitle("test2");
        dailyRecord2.setContent("test2");


        dailyRecordMapper.removeAllDailyRecord(new Date());
        dailyRecordMapper.addDailyRecord(dailyRecord1);
        dailyRecordMapper.addDailyRecord(dailyRecord2);
        Assertions.assertEquals(2,dailyRecordMapper.findAllDailyRecord().size());

        dailyRecordMapper.removeDailyRecordByUserId(BigInteger.valueOf(1),new Date());
        Assertions.assertEquals(1,dailyRecordMapper.findAllDailyRecord().size());

        dailyRecordMapper.addDailyRecord(dailyRecord1);
        dailyRecordMapper.removeDailyRecordByTeamId(BigInteger.valueOf(1),new Date());
        Assertions.assertEquals(1,dailyRecordMapper.findAllDailyRecord().size());

        dailyRecordMapper.updateDailyRecordByUserId(BigInteger.valueOf(2),new Date(),BigInteger.valueOf(2),"test3","test3",2);
        Assertions.assertEquals("test3",dailyRecordMapper.findDailyRecordByTeamId(BigInteger.valueOf(2)).get(0).getContent());

        dailyRecordMapper.updateDailyRecordByTeamId(BigInteger.valueOf(2),new Date(),BigInteger.valueOf(2),"test4","test4",2);
        Assertions.assertEquals("test4",dailyRecordMapper.findDailyRecordByTeamId(BigInteger.valueOf(2)).get(0).getContent());







    }
}
