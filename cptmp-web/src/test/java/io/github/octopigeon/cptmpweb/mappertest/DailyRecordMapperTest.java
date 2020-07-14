package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.DailyRecordMapper;
import io.github.octopigeon.cptmpdao.model.DailyRecord;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
/**
 * @author 李国鹏
 * @version 1.2
 * @date 2020/7/8
 *
 * last-check-in 李国鹏
 * @date 2020/7/12
 */
public class DailyRecordMapperTest extends BaseTest {
    @Autowired
    private DailyRecordMapper dailyRecordMapper;

    @Test
    public void test()
    {
        /**
         * 设置数据
         */
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


        /**
         * 添加
         */
        dailyRecordMapper.removeAllDailyRecordTest();
        dailyRecordMapper.addDailyRecord(dailyRecord1);
        dailyRecordMapper.addDailyRecord(dailyRecord2);
        Assertions.assertEquals(2,dailyRecordMapper.findAllDailyRecord().size());

        /**
         * 删除
         */
        dailyRecordMapper.removeDailyRecordByUserId(BigInteger.valueOf(1),new Date());
        Assertions.assertEquals(1,dailyRecordMapper.findAllDailyRecord().size());

        dailyRecordMapper.addDailyRecord(dailyRecord1);
        dailyRecordMapper.removeDailyRecordByTeamId(BigInteger.valueOf(1),new Date());
        Assertions.assertEquals(1,dailyRecordMapper.findAllDailyRecord().size());

        /**
         * 更新
         */
        DailyRecord dailyRecord3=dailyRecordMapper.findAllDailyRecord().get(0);
        dailyRecord3.setContent("test3");
        dailyRecordMapper.updateDailyRecordByUserId(dailyRecord3);
        Assertions.assertEquals("test3",dailyRecordMapper.findDailyRecordByUserId(BigInteger.valueOf(2)).get(0).getContent());

        dailyRecordMapper.updateDailyRecordDocumentByUserId(BigInteger.valueOf(2),new Date(),"test4");
        Assertions.assertEquals("test4",dailyRecordMapper.findDailyRecordByTeamId(BigInteger.valueOf(2)).get(0).getDocumentPath());

        dailyRecordMapper.removeAllDailyRecord(new Date());
        Assertions.assertEquals(0,dailyRecordMapper.findAllDailyRecord().size());


    }
}
