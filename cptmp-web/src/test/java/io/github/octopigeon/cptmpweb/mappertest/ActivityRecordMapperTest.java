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
 * @version 1.2
 * @date 2020/7/8
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/10
 */

class ActivityRecordMapperTest extends BaseTest {
    @Autowired
    private ActivityRecordMapper activityRecordMapper;
    @Test
    public void test(){
        ActivityRecord activityRecord=new ActivityRecord();
        activityRecord.setGmtCreate(new Date());
        activityRecord.setUserId(BigInteger.valueOf(1));
        activityRecord.setTeamId(BigInteger.valueOf(1));
        activityRecord.setState(1);
        activityRecord.setEvent("test1");

        ActivityRecord activityRecord2=new ActivityRecord();
        activityRecord2.setGmtCreate(new Date());
        activityRecord2.setUserId(BigInteger.valueOf(2));
        activityRecord2.setTeamId(BigInteger.valueOf(2));
        activityRecord2.setState(2);
        activityRecord2.setEvent("test2");

        activityRecordMapper.removeActivityRecordByAll(new Date());
        activityRecordMapper.addActivityRecord(activityRecord);
        activityRecordMapper.addActivityRecord(activityRecord2);
        Assertions.assertEquals(2,activityRecordMapper.findAllActivityRecord().size());

        Assertions.assertEquals("test1",activityRecordMapper.findActivityRecordByUserId(BigInteger.valueOf(1)).get(0).getEvent());

//        activityRecordMapper.removeActivityRecordById(activityRecordMapper.findAllActivityRecord().get(0).getId(),new Date());
//        Assertions.assertEquals(1,activityRecordMapper.findAllActivityRecord().size());
//
//        activityRecordMapper.updateActivityRecordByUserId(BigInteger.valueOf(2),BigInteger.valueOf(2),BigInteger.valueOf(2),new Date(),BigInteger.valueOf(2),"test2");
//        Assertions.assertEquals("test2",activityRecordMapper.findActivityRecordByUserId(BigInteger.valueOf(2)).get(0).getEvent());
    }
}