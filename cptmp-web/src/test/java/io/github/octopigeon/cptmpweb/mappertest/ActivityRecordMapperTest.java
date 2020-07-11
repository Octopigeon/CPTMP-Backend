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

        activityRecordMapper.removeAllActivityRecord();
        List<ActivityRecord> activityRecords;
        activityRecordMapper.addActivityRecord(activityRecord);
        activityRecordMapper.addActivityRecord(activityRecord2);
        activityRecords=activityRecordMapper.findAllActivityRecord();
        Assertions.assertEquals(2,activityRecords.size());

        activityRecords=activityRecordMapper.findActivityRecordByUserId(BigInteger.valueOf(1));
        Assertions.assertEquals("test1",activityRecords.get(0).getEvent());
        Assertions.assertEquals(1,activityRecords.size());

//        activityRecordMapper.updateActivityRecordByUserId(BigInteger.valueOf(1),BigInteger.valueOf(1),new Date(),BigInteger.valueOf(1),"更新完成");
//        activityRecordMapper.removeActivityRecordByUserId(BigInteger.valueOf(1));
//        int n=1;
//        if(activityRecordMapper.findActivityRecordByUserId(BigInteger.valueOf(1))==null){
//            n=0;
//        }
//        Assertions.assertEquals(0,n);
//        activityRecordMapper.removeActivityRecordByAll();
//        int m=1;
//        if(activityRecordMapper.findActivityRecordByUserId(BigInteger.valueOf(1))==null){
//            m=0;
//        }
//        Assertions.assertEquals(0,m);
    }
}