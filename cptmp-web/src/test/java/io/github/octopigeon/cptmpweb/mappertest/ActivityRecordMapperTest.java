package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.ActivityRecordMapper;
import io.github.octopigeon.cptmpdao.model.ActivityRecord;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/8
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/12
 */

class ActivityRecordMapperTest extends BaseTest {
    @Autowired
    private ActivityRecordMapper activityRecordMapper;
    @Test
    public void test(){
        /**
         * 设置两条数据
         */
        ActivityRecord activityRecord1=new ActivityRecord();
        activityRecord1.setGmtCreate(new Date());
        activityRecord1.setUserId(BigInteger.valueOf(1));
        activityRecord1.setTeamId(BigInteger.valueOf(1));
        activityRecord1.setState(1);
        activityRecord1.setEvent("test1");

        ActivityRecord activityRecord2=new ActivityRecord();
        activityRecord2.setGmtCreate(new Date());
        activityRecord2.setUserId(BigInteger.valueOf(2));
        activityRecord2.setTeamId(BigInteger.valueOf(2));
        activityRecord2.setState(2);
        activityRecord2.setEvent("test2");

        /**
         * 添加
         */
        activityRecordMapper.removeAllActivityRecordTest(new Date());
        activityRecordMapper.addActivityRecord(activityRecord1);
        activityRecordMapper.addActivityRecord(activityRecord2);
        Assertions.assertEquals(2,activityRecordMapper.findAllActivityRecord().size());

        /**
         * 查询
         */
        Assertions.assertEquals("test1",activityRecordMapper.findActivityRecordByUserId(BigInteger.valueOf(1)).get(0).getEvent());

        activityRecordMapper.removeActivityRecordById(activityRecordMapper.findAllActivityRecord().get(0).getId(),new Date());
        Assertions.assertEquals(1,activityRecordMapper.findAllActivityRecord().size());

//        ActivityRecord activityRecord3=activityRecordMapper.findAllActivityRecord().get(0);
//        activityRecord3.setEvent("test3");
//        activityRecordMapper.updateActivityRecordById(activityRecord3);
//        Assertions.assertEquals("test3",activityRecordMapper.findActivityRecordByUserId(BigInteger.valueOf(2)).get(0).getEvent());
    }
}