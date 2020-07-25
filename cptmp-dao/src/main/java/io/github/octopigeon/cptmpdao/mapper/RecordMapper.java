package io.github.octopigeon.cptmpdao.mapper;
import io.github.octopigeon.cptmpdao.model.Record;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
/**
 * @author 李国鹏
 * @version 2.0
 * @date 2020/7/8
 *
 * @last-check-in 李国鹏
 * @date 2020/7/25
 */
@Repository
@Mapper
public interface RecordMapper {

    String COLUMNS="gmt_create, gmt_modified, gmt_deleted, idx_train_id, idx_user_id, idx_team_id, idx_process_event_id, assignments_lib";
    String PROPS="#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{trainId}, #{userId}, #{teamId}, #{processEventId}, #{assignmentsLib}";
    String UPDATE_CONTENT=" gmt_create = #{gmtCreate},gmt_modified = #{gmtModified},gmt_deleted = #{gmtDeleted},idx_user_id = #{userId}, " +
            "idx_train_id = #{trainId}, idx_team_id = #{teamId}, idx_process_event_id = #{processEventId}, assignments_lib = #{assignmentsLib}";

    /**
     * 添加记录
     * @param record 记录
     */
    @Insert("insert into record (" + COLUMNS + ") values ( " + PROPS +" )")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addRecord(Record record);


    /**
     *  删除活动信息
     */
    @Deprecated
    @Delete("delete from record")
    void removeAllRecordTest();

    /**
     *  删除活动信息
     * @param gmtDeleted 删除日期
     */
    @Update("update record set gmt_deleted = #{gmtDeleted} where gmt_deleted is null")
    void hideRecordByAll(Date gmtDeleted);

    /**
     * 恢复活动记录
     */
    @Update("update record set gmt_deleted = null where gmt_deleted is not null")
    void restoreRecordByAll();

    /**
     * 根据id删除对应的活动信息
     * @param id：活动记录id
     * @param gmtDeleted 删除日期
     */
    @Update("update record set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void hideRecordById(BigInteger id, Date gmtDeleted);

    /**
     * 通过id恢复
     * @param id id
     */
    @Update("update record set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restoreRecordById(BigInteger id);

    /**
     * 根据id修改
     * @param record 活动记录
     */
    @Update("update record set "+UPDATE_CONTENT+" where id = #{id} and gmt_deleted is null")
    void updateRecordById(Record record);


    /**
     * 查找所有活动记录
     * @return 活动记录列表
     */
    @Select("select id, " + COLUMNS + " from record where gmt_deleted is null")
    @Results(id = "record", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "idx_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_team_id", property = "teamId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_train_id", property = "trainId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_process_event_id", property = "processEventId", jdbcType = JdbcType.BIGINT),
            @Result(column = "assignments_lib", property = "assignmentsLib", jdbcType = JdbcType.BIGINT),


    })
    List<Record> findAllRecord();

    /**
     * 按照活动人id查找活动记录
     * @param trainId 实训id
     * @param userId:活动人id
     * @return 活动记录列表
     */
    @Select("select id, " + COLUMNS + " from record where idx_train_id = #{trainId} and idx_user_id = #{userId} and gmt_deleted is null")
    @ResultMap("record")
    List<Record> findRecordByUserId(BigInteger trainId, BigInteger userId);

    /**
     * 按照团队id查找活动记录
     * @param teamId：团队id
     * @return 活动记录列表
     */
    @Select("select id, " + COLUMNS + " from record where idx_team_id = #{teamId} and gmt_deleted is null")
    @ResultMap("record")
    List<Record> findRecordByTeamId(BigInteger teamId);

    /**
     * 通过id查找
     * @param id id
     * @return 活动记录
     */
    @Select("select id, " + COLUMNS + " from record where id = #{id} and gmt_deleted is null")
    @ResultMap("record")
    Record findRecordById(BigInteger id);

    /**
     * 根据用户Id和processEventId进行记录的查找
     * @param userId 用户Id
     * @param processEventId processEventId
     * @return 记录
     */
    @Select("select id, " + COLUMNS + " from record where idx_user_id = #{userId} and idx_process_event_id = #{processEventId} and gmt_deleted is null")
    @ResultMap("record")
    Record findRecordByUserIdAndProcessEventId(BigInteger userId, BigInteger processEventId);

    /**
     * 根据processEventID进行记录查找
     * @param processEventId 过程事件ID
     * @return 记录列表
     */
    @Select("select id, " + COLUMNS + " from record where idx_process_event_id = #{processEventId} and gmt_deleted is null")
    @ResultMap("record")
    List<Record> findRecordByProcessEventId(BigInteger processEventId);

    /**
     * 根据teamId和processEventId进行记录的查找
     * @param teamId 团队Id
     * @param processEventId 过程事件Id
     * @return 记录
     */
    @Select("select id, " + COLUMNS + " from record where idx_team_id = #{teamId} and idx_process_event_id = #{processEventId} and gmt_deleted is null")
    @ResultMap("record")
    Record findRecordByTeamIdAndProcessEventId(BigInteger teamId, BigInteger processEventId);
}

