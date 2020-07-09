package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.DailyRecord;
import io.github.octopigeon.cptmpdao.model.EnterpriseAdmin;
import lombok.Data;
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
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/8
 */
@Repository
@Mapper
public interface DailyRecordMapper {
    /**
     * 添加日志
     * @param dailyRecord：日志类
     */
    @Insert("insert into daily_record (gmt_create, title, content, idx_user_id, document_path, record_type) values (#{gmtCreate}, #{title}, #{content}, #{userId}, #{documentPath}, #{recordType})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addDailyRecord(DailyRecord dailyRecord);

    /**
     * 根据日志id删除对应的日志信息
     * @param id：日志id
     */
    @Delete("delete from daily_record where id = #{id}")
    void removeDailyRecordById(BigInteger id);

    /**
     * 根据用户id删除日志
     * @param userId 用户id
     */
    @Delete("delete from daily_record where idx_user_id = #{userId}")
    void removeDailyRecordByUserId(BigInteger userId);

    /**
     * 根据日志id修改日志
     * @param id 日志id
     * @param gmtModified 修改时间
     * @param title 标题
     * @param content 内容
     * @param userId 用户id
     * @param documentPath 文件路径
     * @param recordType 文件类型
     */
    @Update("update daily_record set gmt_modified = #{gmtModified}, title = #{title}, content = #{content}, idx_user_id = #{userId}, document_path = #{documentPath}, record_type = #{recordType} where id = #{id}")
    void updateDailyRecordById(BigInteger id, Date gmtModified, String title, String content, BigInteger userId, String documentPath, int recordType);

    /**
     * 根据用户id修改日志
     * @param gmtModified 修改时间
     * @param title 标题
     * @param content 内容
     * @param userId 用户id
     * @param documentPath 文件路径
     * @param recordType 文件类型
     */
    @Update("update daily_record set gmt_modified = #{gmtModified}, title = #{title}, content = #{content}, idx_user_id = #{userId}, document_path = #{documentPath}, record_type = #{recordType} where idx_user_id = #{userId}")
    void updateDailyRecordByUserId(Date gmtModified, String title, String content, BigInteger userId, String documentPath, int recordType);

    /**
     * 查询所有日志
     * @return 日志列表
     */
    @Select("select * from daily_record")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "idx_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "document_path", property = "documentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "record_type", property = "recordType", jdbcType = JdbcType.INTEGER),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR)

    })
    List<DailyRecord> findAllDailyRecord();

    /**
     * 根据用户id查找日志
     * @param userId：用户id
     * @return 日志
     */
    @Select("select * from daily_record where idx_user_id = #{userId}")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modify", property = "gmtModify", jdbcType = JdbcType.DATE),
            @Result(column = "idx_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "document_path", property = "documentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "record_type", property = "recordType", jdbcType = JdbcType.INTEGER),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR)
    })
    List<DailyRecord> findDailyRecordByUserId(BigInteger userId);


}
