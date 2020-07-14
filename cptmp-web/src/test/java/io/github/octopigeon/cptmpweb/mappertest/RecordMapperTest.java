package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.RecordMapper;
import io.github.octopigeon.cptmpdao.mapper.RecordMapper;
import io.github.octopigeon.cptmpdao.model.Record;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/8
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/12
 */

class RecordMapperTest extends BaseTest {
    @Autowired
    private RecordMapper recordMapper;
    @Test
    public void test(){
        /**
         * 设置两条数据
         */
        Record record1 =new Record();
        record1.setGmtCreate(new Date());
        record1.setUserId(BigInteger.valueOf(1));
        record1.setTeamId(BigInteger.valueOf(1));
        record1.setState(1);
        record1.setEvent("test1");

        Record record2 =new Record();
        record2.setGmtCreate(new Date());
        record2.setUserId(BigInteger.valueOf(2));
        record2.setTeamId(BigInteger.valueOf(2));
        record2.setState(2);
        record2.setEvent("test2");

        /**
         * 添加
         */
        recordMapper.removeAllRecordTest();
        recordMapper.addRecord(record1);
        recordMapper.addRecord(record2);
        Assertions.assertEquals(2,recordMapper.findAllRecord().size());

        /**
         * 查询
         */
        Assertions.assertEquals("test1",recordMapper.findAllRecord().get(0).getEvent());

        recordMapper.removeRecordById(recordMapper.findAllRecord().get(0).getId(),new Date());
        Assertions.assertEquals(1,recordMapper.findAllRecord().size());

//        Record activityRecord3=recordMapper.findAllRecord().get(0);
//        activityRecord3.setEvent("test3");
//        recordMapper.updateRecordById(activityRecord3);
//        Assertions.assertEquals("test3",recordMapper.findRecordByTeamId(BigInteger.valueOf(2)).get(0).getEvent());
    }
}