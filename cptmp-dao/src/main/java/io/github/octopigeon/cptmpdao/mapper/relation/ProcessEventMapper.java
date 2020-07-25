package io.github.octopigeon.cptmpdao.mapper.relation;

import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
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
 * @last-check-in 李国鹏
 * @date 2020/7/15
 */
@Repository
@Mapper
public interface ProcessEventMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, process_id, event_id";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{processId}, #{eventId}";
    String UPDATE_CONTENT = "gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, " +
            "gmt_deleted = #{gmtDeleted}, process_id = #{processId}, event_id = #{eventId}";

    /**
     * 增加流程事件
     * @param processEvent 流程事件
     */
    @Insert("insert into process_event (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addProcessEvent(ProcessEvent processEvent);

    /**
     * 根据id删除
     * @param id id
     */
    @Delete("delete from process_event where id = #{id}")
    void removeProcessEventById(BigInteger id);

    /**
     * 根据流程id删除
     * @param processId 流程id
     */
    @Delete("delete from process_event where process_id = #{processId}")
    void removeProcessEventsByProcessId(BigInteger processId);

    /**
     * 根据事件id删除
     * @param eventId  事件id
     */
    @Delete("delete from process_event where event_id = #{eventId}")
    void removeProcessEventsByEventId(BigInteger eventId);

    /**
     * 根据流程与事件id进行删除
     * @param processId 流程id
     * @param eventId 事件id
     */
    @Delete("delete from process_event where process_id = #{processId} and event_id = #{eventId}")
    void removeProcessEventByProcessIdAndEventId(BigInteger processId, BigInteger eventId);

    /**
     * 测试用
     * 删除所有此表数据
     */
    @Deprecated
    @Delete("delete from process_event")
    void removeAllProcessEvents();

    /**
     * 通过id更新
     * @param processEvent 流程事件对象
     */
    @Update("update process_event set " + UPDATE_CONTENT + " where id = #{id}")
    void updateProcessEventById(ProcessEvent processEvent);

    /**
     * 根据id查询
     * @param id id
     * @return 流程事件对象
     */
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

    /**
     * 通过流程id查询
     * @param processId 流程id
     * @return 流程事件列表
     */
    @Select("select id, " + COLUMNS + " from process_event where process_id = #{processId}")
    @ResultMap("process_event")
    List<ProcessEvent> findProcessEventsByProcessId(BigInteger processId);

    /**
     * 根据事件id查询
     * @param eventId 事件id
     * @return 流程事件列表
     */
    @Select("select id, " + COLUMNS + " from process_event where event_id = #{eventId}")
    @ResultMap("process_event")
    List<ProcessEvent> findProcessEventsByEventId(BigInteger eventId);

    /**
     * 根据流程和事件id查询
     * @param processId 流程id
     * @param eventId 事件id
     * @return 流程事件列表
     */
    @Select("select id, " + COLUMNS + " from process_event where process_id = #{processId} and event_id = #{eventId}")
    @ResultMap("process_event")
    ProcessEvent findProcessEventByProcessIdAndEventId(BigInteger processId, BigInteger eventId);
//
//    /**
//     * 测试用
//     * @return
//     */
//    @Deprecated
//    @Select("select id, " + COLUMNS + " from process_event")
//    @ResultMap("process_event")
//    List<ProcessEvent> findAllProcessEventsTest();

    /**
     * 查询所有
     * @return 流程事件
     */
    @Select("select id, " + COLUMNS + " from process_event")
    @ResultMap("process_event")
    List<ProcessEvent> findAllProcessEvents();

}
