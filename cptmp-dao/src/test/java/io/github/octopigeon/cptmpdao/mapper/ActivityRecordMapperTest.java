package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.ActivityRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
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

class ActivityRecordMapperTest{
    @Autowired
    private ActivityRecordMapper activityRecordMapper=new ActivityRecordMapper() {
        @Override
        public void addActivityRecord(ActivityRecord activityRecord) {

        }

        @Override
        public void removeActivityRecordByAll() {

        }

        @Override
        public void removeActivityRecordByPeopleId(BigInteger peopleId) {

        }

        @Override
        public void removeActivityRecordByTeamId(BigInteger teamId) {

        }

        @Override
        public void updateActivityRecordByPeopleId(BigInteger peopleId, BigInteger teamId, Date gmtModified, BigInteger state, String event) {

        }

        @Override
        public void updateActivityRecordByTeamId(BigInteger peopleId, BigInteger teamId, Date gmtModified, BigInteger state, String event) {

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
        activityRecordMapper.findAllActivityRecord();
        activityRecordMapper.findActivityRecordByPeopleId(BigInteger.valueOf(1));
        activityRecordMapper.findActivityRecordByTeamId(BigInteger.valueOf(2));
        activityRecordMapper.updateActivityRecordByPeopleId(BigInteger.valueOf(1),BigInteger.valueOf(1),new Date(),BigInteger.valueOf(1),"更新完成");
        activityRecordMapper.removeActivityRecordByPeopleId(BigInteger.valueOf(1));
        activityRecordMapper.removeActivityRecordByAll();
    }
}