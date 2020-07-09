package io.github.octopigeon.cptmpweb.mappertest;

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
 * @version 1.0
 * @date 2020/7/8
 *
 * last-check-in 李国鹏
 * @date 2020/7/8
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
        dailyRecord1.setRecordType(0);
        dailyRecord1.setDocumentPath("E://");
        dailyRecord1.setTitle("lgp");
        dailyRecord1.setContent("sah");

        DailyRecord dailyRecord2 = new DailyRecord();
        dailyRecord2.setGmtCreate(new Date());
        dailyRecord2.setUserId(BigInteger.valueOf(2));
        dailyRecord2.setTeamId(BigInteger.valueOf(2));
        dailyRecord2.setRecordType(1);
        dailyRecord2.setDocumentPath("E://");
        dailyRecord2.setTitle("wxc");
        dailyRecord2.setContent("lhr");

        dailyRecordMapper.addDailyRecord(dailyRecord1);
        dailyRecordMapper.addDailyRecord(dailyRecord2);

        List<DailyRecord> dailyRecords;
        dailyRecords=dailyRecordMapper.findAllDailyRecord();
        Assertions.assertEquals(2,dailyRecords.size());

        dailyRecords=dailyRecordMapper.findDailyRecordByUserId(BigInteger.valueOf(1));
        Assertions.assertEquals(1,dailyRecords.size());
//
//        dailyRecordMapper.removeDailyRecordByUserId(BigInteger.valueOf(1));
//        dailyRecords=dailyRecordMapper.findAllDailyRecord();
//        Assertions.assertEquals(1,dailyRecords.size());
//
//        dailyRecordMapper.updateDailyRecordByUserId(new Date(),"test","lgp",BigInteger.valueOf(2), BigInteger.valueOf(2), "C://",0);
//        dailyRecords=dailyRecordMapper.findDailyRecordByUserId(BigInteger.valueOf(2));
//        Assertions.assertEquals("lgp",dailyRecords.get(0).getContent());








    }
}