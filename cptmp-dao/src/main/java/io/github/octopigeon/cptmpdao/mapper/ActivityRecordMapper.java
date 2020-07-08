package io.github.octopigeon.cptmpdao.mapper;
import io.github.octopigeon.cptmpdao.model.ActivityRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/8
 *
 * @last-check-in 李国鹏
 * @date 2020/7/8
 */
@Repository
@Mapper
public interface ActivityRecordMapper {
    /**
     * 向activity record中插入一条数据
     * @param activityRecord：类
     */
    @Insert("insert into activity_record (gmt_create, state_record, event_record, uk_user_id, uk_team_id) values (#{gmtCreate}, #{state}, #{event}, #{userId}, #{teamId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addActivityRecord(ActivityRecord activityRecord);

//    /**
//     *  删除活动信息
//     */
//    @Delete("delete * from activity_record")
//    void removeActivityRecordByAll();
//    /**
//     * 根据活动人id删除对应的活动信息
//     * @param userId：活动人id
//     */
//    @Delete("delete from activity_record where uk_user_id = #{userId}")
//    void removeActivityRecordByUserId(BigInteger userId);
//
//    /**
//     * 根据团队伍id删除对应的活动信息
//     * @param teamId: 团队id
//     */
//    @Delete("delete from activity_record where uk_team_id = #{teamId}")
//    void removeActivityRecordByTeamId(BigInteger teamId);
//
//
//    /**
//     * 根据活动人id修改活动记录
//     * @param userId：活动人id
//     * @param teamId：团队id
//     * @param gmtModified：修改时间
//     * @param state：活动状态
//     * @param event：活动内容事件
//     */
//    @Update("update activity_record set gmt_modified = #{gmtModified}, state_record = #{state}, event_record = #{event}, uk_user_id = #{userId}, uk_team_id = #{teamId} where uk_user_id = #{userId}")
//    void updateActivityRecordByUserId(BigInteger userId, BigInteger teamId, Date gmtModified, BigInteger state, String event);
//
//    /**
//     *根据团队id修改活动记录
//     * @param userId：活动人id
//     * @param teamId：团队id
//     * @param gmtModified：修改时间
//     * @param state：活动状态
//     * @param event：活动内容事件
//     */
//    @Update("update activity_record set gmt_modified = #{gmtModified}, state_record = #{state}, event_record = #{event}, uk_user_id = #{userId}, uk_team_id = #{teamId} where uk_team_id = #{teamId}")
//    void updateActivityRecordByTeamId(BigInteger userId, BigInteger teamId, Date gmtModified, BigInteger state, String event);

    /**
     * 查找所有活动记录
     * @return 活动记录列表
     */
    @Select("select * from activity_record")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "uk_team_id", property = "teamId", jdbcType = JdbcType.BIGINT),
            @Result(column = "event_record", property = "event", jdbcType = JdbcType.VARCHAR),
            @Result(column = "state_record", property = "state", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE)
    })
    List<ActivityRecord> findAllActivityRecord();

    /**
     * 按照活动人id查找活动记录
     * @param userId:活动人id
     * @return 活动记录列表
     */
    @Select("select * from activity_record where uk_user_id = #{userId}")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "uk_team_id", property = "teamId", jdbcType = JdbcType.BIGINT),
            @Result(column = "event_record", property = "event", jdbcType = JdbcType.VARCHAR),
            @Result(column = "state_record", property = "state", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE)
    })
    List<ActivityRecord> findActivityRecordByUserId(BigInteger userId);

    /**
     * 按照团队id查找活动记录
     * @param teamId：团队id
     * @return 活动记录列表
     */
    @Select("select * from activity_record where uk_team_id = #{teamId}")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "uk_team_id", property = "teamId", jdbcType = JdbcType.BIGINT),
            @Result(column = "event_record", property = "event", jdbcType = JdbcType.VARCHAR),
            @Result(column = "state_record", property = "state", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE)
    })
    List<ActivityRecord> findActivityRecordByTeamId(BigInteger teamId);

}

