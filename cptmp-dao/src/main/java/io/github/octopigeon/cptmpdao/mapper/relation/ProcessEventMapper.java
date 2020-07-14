package io.github.octopigeon.cptmpdao.mapper.relation;

import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
import kotlin.internal.DynamicExtension;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 魏啸冲
 * @date 2020/7/14
 */
@Repository
@Mapper
public interface ProcessEventMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, process_id, event_id";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{processId}, #{eventId}";
    String UPDATE_CONTENT = "gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, " +
            "gmt_deleted = #{gmtDeleted}, process_id = #{processId}, event_id = #{eventId}";

    @Insert("insert into process_event (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addProcessEvent(ProcessEvent processEvent);

    @Delete("delete from process_event where id = #{id}")
    void removeProcessEventById(BigInteger id);

    @Delete("delete from process_event where process_id = #{processId}")
    void removeProcessEventsByProcessId(BigInteger processId);

    @Delete("delete from process_event where event_id = #{eventId}")
    void removeProcessEventsByEventId(BigInteger eventId);

    @Delete("delete from process_event where process_id = #{processId} and event_id = #{eventId}")
    void removeProcessEventByProcessIdAndEventId(BigInteger processId, BigInteger eventId);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from process_event")
    void removeAllProcessEvents();

    @Update("update process_event set " + UPDATE_CONTENT + " where id = #{id}")
    void updateProcessEventById(ProcessEvent processEvent);

    @Select("select id, " + COLUMNS + " from process_event where id = #{id}")
    @Results(id = "process_event", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "process_id", property = "processId", jdbcType = JdbcType.BIGINT),
            @Result(column = "event_id", property = "eventId", jdbcType = JdbcType.BIGINT)
    })
    ProcessEvent findProcessEventById(BigInteger id);

    @Select("select id, " + COLUMNS + " from process_event where process_id = #{processId}")
    @ResultMap("process_event")
    List<ProcessEvent> findProcessEventsByProcessId(BigInteger processId);

    @Select("select id, " + COLUMNS + " from process_event where event_id = #{eventId}")
    @ResultMap("process_event")
    List<ProcessEvent> findProcessEventsByEventId(BigInteger eventId);

    @Select("select id, " + COLUMNS + " from process_event where process_id = #{processId} and event_id = #{eventId}")
    @ResultMap("process_event")
    ProcessEvent findProcessEventByProcessIdAndEventId(BigInteger processId, BigInteger eventId);

    /**
     * 测试用
     * @return
     */
    @Deprecated
    @Select("select id, " + COLUMNS + " from process_event")
    @ResultMap("process_event")
    List<ProcessEvent> findAllProcessEvents();

}
