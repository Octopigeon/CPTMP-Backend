package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.Event;
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
 * @date 2020/7/21
 */
@Repository
@Mapper
public interface EventMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, start_time, end_time, content, person_or_team";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{startTime}, #{endTime}, #{content}, #{personOrTeam}";
    String UPDATE_CONTENT = "gmt_modified = #{gmtModified}, " +
            "gmt_deleted = #{gmtDeleted}, start_time = #{startTime}, end_time = #{endTime}, " +
            "content = #{content}, person_or_team = #{personOrTeam}";
    String SOFT_DELETE_TAIL = "gmt_deleted is null";

    /**
     * 添加事件
     * @param event 事件对象
     */
    @Insert("insert into cptmp_event (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void addEvent(Event event);



    /**
     * 测试用
     * 删除全部
     */
    @Deprecated
    @Delete("delete from cptmp_event")
    void removeAllEvents();

    /**
     * 软删除所有事件
     * @param gmtDeleted 删除日期
     */
    @Update("update cptmp_event set gmt_deleted = #{gmtDeleted} where "  + SOFT_DELETE_TAIL)
    void hideAllEvent(Date gmtDeleted);

    /**
     * 恢复所有事件
     */
    @Update("update cptmp_event set gmt_deleted = null where gmt_deleted is not null")
    void restoreAllEvent();


    /**
     * 通过id隐藏事件
     * @param id id
     * @param gmtDeleted 删除日期
     */
    @Update("update cptmp_event set gmt_deleted = #{gmtDeleted} where id = #{id} and " + SOFT_DELETE_TAIL)
    void hideEventById(BigInteger id, Date gmtDeleted);

    /**
     * 通过id恢复
     * @param id id
     */
    @Update("update cptmp_event set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restoreEventById(BigInteger id);

    /**
     * 通过id更新
     * @param event 事件对象
     */
    @Update("update cptmp_event set " + UPDATE_CONTENT + " where id = #{id} and " + SOFT_DELETE_TAIL)
    void updateEventById(Event event);

    /**
     * 通过id查询
     * @param id id
     * @return 事件对象
     */
    @Select("select id, " + COLUMNS + " from cptmp_event where id = #{id} and " + SOFT_DELETE_TAIL)
    @Results(id = "event", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "start_time", property = "startTime", jdbcType = JdbcType.DATE),
            @Result(column = "end_time", property = "endTime", jdbcType = JdbcType.DATE),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "person_or_team", property = "personOrTeam", jdbcType = JdbcType.TINYINT)
    })
    Event findEventById(BigInteger id);

    /**
     * 查询所有事件
     * @return 事件列表
     */
    @Select("select id, " + COLUMNS + " from cptmp_event where " + SOFT_DELETE_TAIL)
    @ResultMap("event")
    List<Event> findAllEvents();

    // TODO 还有很多种操作，比如根据开始和结束时间，是面向个人还是面向团队的任务等。具体等service层通知

}
