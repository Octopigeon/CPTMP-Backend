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
 * @date 2020/7/8
 */

class ActivityRecordMapperTest extends BaseTest {
    @Autowired
    private ActivityRecordMapper activityRecordMapper=new ActivityRecordMapper() {
        @Override
        public void addActivityRecord(ActivityRecord activityRecord) {

        }

        @Override
        public List<ActivityRecord> findAllActivityRecord() {
            return null;
        }

        @Override
        public ActivityRecord findActivityRecordByPeopleId(BigInteger peopleId) {
            return null;
        }

        @Override
        public ActivityRecord findActivityRecordByTeamId(BigInteger teamId) {
            return null;
        }
    };
    @Test
    public void test(){
        ActivityRecord activityRecord=new ActivityRecord();
        activityRecord.setGmtCreate(new Date());
        activityRecord.setId(BigInteger.valueOf(1));
        activityRecord.setActivityPeopleId(BigInteger.valueOf(1));
        activityRecord.setActivityTeamId(BigInteger.valueOf(1));
        activityRecord.setActivityState(BigInteger.valueOf(0));
        activityRecord.setActivityEvent("test1");

        ActivityRecord activityRecord2=new ActivityRecord();
        activityRecord2.setGmtCreate(new Date());
        activityRecord2.setId(BigInteger.valueOf(2));
        activityRecord2.setActivityPeopleId(BigInteger.valueOf(2));
        activityRecord2.setActivityTeamId(BigInteger.valueOf(2));
        activityRecord2.setActivityState(BigInteger.valueOf(0));
        activityRecord2.setActivityEvent("test2");

        activityRecordMapper.addActivityRecord(activityRecord);
        activityRecordMapper.addActivityRecord(activityRecord2);
        List<ActivityRecord> activityRecords=new ArrayList<>();
        activityRecords.add(activityRecordMapper.findActivityRecordByPeopleId(BigInteger.valueOf(1)));
        activityRecords.add((activityRecordMapper.findActivityRecordByTeamId(BigInteger.valueOf(2))));
        Assertions.assertEquals(2,activityRecords.size());
//        activityRecordMapper.updateActivityRecordByPeopleId(BigInteger.valueOf(1),BigInteger.valueOf(1),new Date(),BigInteger.valueOf(1),"更新完成");
//        activityRecordMapper.removeActivityRecordByPeopleId(BigInteger.valueOf(1));
//        int n=1;
//        if(activityRecordMapper.findActivityRecordByPeopleId(BigInteger.valueOf(1))==null){
//            n=0;
//        }
//        Assertions.assertEquals(0,n);
//        activityRecordMapper.removeActivityRecordByAll();
//        int m=1;
//        if(activityRecordMapper.findActivityRecordByPeopleId(BigInteger.valueOf(1))==null){
//            m=0;
//        }
//        Assertions.assertEquals(0,m);
    }
}