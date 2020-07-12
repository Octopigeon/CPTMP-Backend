package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.DailyRecord;
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
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/12
 */
@Repository
@Mapper
public interface DailyRecordMapper {
    /**
     * 添加日志
     * @param dailyRecord：日志类
     */
    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, idx_user_id, idx_team_id, title, content, record_type, document_path";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{userId}, #{teamId}, #{title}, #{content}, #{recordType}, #{documentPath}";
    String UPDATE_CONTENT = "idx_user_id = #{userId}, idx_team_id = #{teamId}, gmt_modified = #{gmtModified}, title = #{title}, content = #{content},  record_type = #{recordType}";
    @Insert("insert into daily_record (" +COLUMNS+ ") values (" +PROPS+ ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addDailyRecord(DailyRecord dailyRecord);
    /**
     * 测试用
     * @param gmtDeleted 删除日期
     */
    @Deprecated
    @Update("update daily_record set gmt_deleted = #{gmtDeleted} where gmt_deleted is null")
    void removeAllDailyRecord(Date gmtDeleted);

    /**
     * 根据日志id删除对应的日志信息
     * @param id：日志id
     * @param gmtDeleted 删除日期
     */
    @Update("update daily_record set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void removeDailyRecordById(BigInteger id,Date gmtDeleted);

    /**
     * 根据用户id删除日志
     * @param userId 用户id
     * @param gmtDeleted 删除日期
     */
    @Update("update daily_record set gmt_deleted = #{gmtDeleted} where idx_user_id = #{userId} and gmt_deleted is null")
    void removeDailyRecordByUserId(BigInteger userId,Date gmtDeleted);

    /**
     * 根据团队id删除
     * @param teamId 团队id
     * @param gmtDeleted 删除日期
     */
    @Update("update daily_record set gmt_deleted = #{gmtDeleted} where idx_team_id = #{teamId} and gmt_deleted is null")
    void removeDailyRecordByTeamId(BigInteger teamId,Date gmtDeleted);

    /**
     * 根据日志id修改日志
     * @param id 日志id
     * @param gmtModified 修改时间
     * @param title 标题
     * @param content 内容
     * @param userId 用户id
     * @param teamId 团队id
     * @param recordType 文件类型
     */
    @Update("update daily_record set "+ UPDATE_CONTENT+" where id = #{id} and gmt_deleted is null")
    void updateDailyRecordById(BigInteger id, Date gmtModified, BigInteger userId, BigInteger teamId,  String title, String content, int recordType);

    /**
     * 根据id改文件路径
     * @param id 日志id
     * @param gmtModified 修改时间
     * @param documentPath 文件路径
     * @return
     */
    @Update("update daily_record set gmt_modified = #{gmtModified}, document_path = #{documentPath} where id = #{id} and gmt_deleted is null")
    Void updateDailyRecordDocumentById(BigInteger id,Date gmtModified,String documentPath);


    /**
     * 根据用户id修改日志
     * @param gmtModified 修改时间
     * @param title 标题
     * @param content 内容
     * @param userId 用户id
     * @param teamId 团队id
     * @param recordType 文件类型
     */
    @Update("update daily_record set "+UPDATE_CONTENT+" where idx_user_id = #{userId} and gmt_deleted is null")
    void updateDailyRecordByUserId(BigInteger userId, Date gmtModified, BigInteger teamId,  String title, String content, int recordType);

    /**
     * 根据id改文件路径
     * @param userId 用户id
     * @param gmtModified 修改时间
     * @param documentPath 文件路径
     * @return
     */
    @Update("update daily_record set gmt_modified = #{gmtModified}, document_path = #{documentPath} where idx_user_id = #{userId} and gmt_deleted is null")
    Void updateDailyRecordDocumentByUserId(BigInteger userId,Date gmtModified, String documentPath);


    /**
     * 根据用户id修改日志
     * @param gmtModified 修改时间
     * @param title 标题
     * @param content 内容
     * @param userId 用户id
     * @param teamId 团队id
     * @param recordType 文件类型
     */
    @Update("update daily_record set "+UPDATE_CONTENT+" where idx_team_id = #{teamId} and gmt_deleted is null")
    void updateDailyRecordByTeamId( BigInteger teamId, Date gmtModified, BigInteger userId,  String title, String content, int recordType);

    /**
     * 根据id改文件路径
     * @param teamId 团队id
     * @param gmtModified 修改时间
     * @param documentPath 文件路径
     *
     */
    @Update("update daily_record set gmt_modified = #{gmtModified}, document_path = #{documentPath} where idx_team_id = #{teamId} and gmt_deleted is null")
    void updateDailyRecordDocumentByTeamId(BigInteger teamId,Date gmtModified,String documentPath);


    /**
     * 查询所有日志
     * @return 日志列表
     */
    @Select("select id, " + COLUMNS + " from daily_record where gmt_deleted is null")
    @Results(id = "dailyRecord", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "idx_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_team_id", property = "teamId", jdbcType = JdbcType.BIGINT),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "record_type", property = "recordType", jdbcType = JdbcType.INTEGER),
            @Result(column = "document_path", property = "documentPath", jdbcType = JdbcType.VARCHAR)

    })
    List<DailyRecord> findAllDailyRecord();

    /**
     * 根据用户id查找日志
     * @param userId：用户id
     * @return 日志
     */
    @Select("select id, " + COLUMNS + " from daily_record where idx_user_id = #{userId} and gmt_deleted is null")
    @ResultMap("dailyRecord")
    List<DailyRecord> findDailyRecordByUserId(BigInteger userId);

    /**
     * 根据用户id查找日志
     * @param teamId：用户id
     * @return 日志
     */
    @Select("select id, " + COLUMNS + " from daily_record where idx_team_id = #{teamId} and gmt_deleted is null")
    @ResultMap("dailyRecord")
    List<DailyRecord> findDailyRecordByTeamId(BigInteger teamId);


}
