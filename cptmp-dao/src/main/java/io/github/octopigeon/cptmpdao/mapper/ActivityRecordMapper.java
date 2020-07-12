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
 * @version 2.1
 * @date 2020/7/8
 *
 * @last-check-in 李国鹏
 * @date 2020/7/12
 */
@Repository
@Mapper
public interface ActivityRecordMapper {
    /**
     * 向activity record中插入一条数据
     * @param activityRecord：类
     */
    String COLUMNS="gmt_create, gmt_modified, gmt_deleted, idx_user_id, idx_team_id, state_record, event_record";
    String PROPS="#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{userId}, #{teamId}, #{state}, #{event}";
    String UPDATE_CONTENT=" idx_user_id = #{userId}, idx_team_id = #{teamId},gmt_modified = #{gmtModified}, state_record = #{state}, event_record = #{event}";
    @Insert("insert into activity_record (" + COLUMNS + ") values ( " + PROPS +" )")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addActivityRecord(ActivityRecord activityRecord);


    /**
     *  删除活动信息
     * @param gmtDeleted 删除日期
     */
    @Deprecated
    @Delete("update activity_record set gmt_deleted = #{gmtDeleted}")
    void removeActivityRecordByAll(Date gmtDeleted);
//
//    /**
//     * 根据id删除对应的活动信息
//     * @param id：活动记录id
//     * @param gmtDeleted 删除日期
//     */
//    @Delete("update activity_record set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
//    void removeActivityRecordById(BigInteger id,Date gmtDeleted);
//
//
//    /**
//     * 根据id修改
//     * @param id id
//     * @param userId 活动人id
//     * @param teamId 团队id
//     * @param gmtModified 修改时间
//     * @param state 活动状态
//     * @param event 活动事件
//     */
//    @Update("update activity_record set "+UPDATE_CONTENT+" where id = #{id} and gmt_deleted is null")
//    void updateActivityRecordByUserId( BigInteger id, Date gmtModified, BigInteger userId,BigInteger teamId,  BigInteger state, String event);


    /**
     * 查找所有活动记录
     * @return 活动记录列表
     */
    @Select("select id, " + COLUMNS + " from activity_record where gmt_deleted is null")
    @Results(id = "activityRecord", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "idx_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_team_id", property = "teamId", jdbcType = JdbcType.BIGINT),
            @Result(column = "state_record", property = "state", jdbcType = JdbcType.BIGINT),
            @Result(column = "event_record", property = "event", jdbcType = JdbcType.VARCHAR)
    })
    List<ActivityRecord> findAllActivityRecord();

    /**
     * 按照活动人id查找活动记录
     * @param userId:活动人id
     * @return 活动记录列表
     */
    @Select("select id, " + COLUMNS + " from activity_record where idx_user_id = #{userId} and gmt_deleted is null")
    @ResultMap("activityRecord")
    List<ActivityRecord> findActivityRecordByUserId(BigInteger userId);

    /**
     * 按照团队id查找活动记录
     * @param teamId：团队id
     * @return 活动记录列表
     */
    @Select("select id, " + COLUMNS + " from activity_record where idx_team_id = #{teamId} and gmt_deleted is null")
    @ResultMap("activityRecord")
    List<ActivityRecord> findActivityRecordByTeamId(BigInteger teamId);

}

