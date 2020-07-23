package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.Notice;
import io.github.octopigeon.cptmpdao.model.Train;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/20
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/21
 */
@Repository
@Mapper
public interface NoticeMapper {

    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, idx_sender_id, idx_receiver_id, idx_team_id, " +
            "notice_type, content, is_read";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{senderId}, #{receiverId}, #{teamId}, " +
            "#{noticeType}, #{content}, #{isRead}";
    String UPDATE_CONTENT = "gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, gmt_deleted = #{gmtDeleted}, " +
            "idx_sender_id = #{senderId}, idx_receiver_id = #{receiverId}, idx_team_id = #{teamId}, notice_type = #{noticeType}, " +
            "content = #{content}, is_read = #{isRead}";
    String SOFT_DELETE_TAIL = "gmt_deleted is null";

    /**
     * 插入信息
     *
     * @param notice：信息表
     */
    @Insert("insert into notice (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addNotice(Notice notice);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from notice")
    void removeAllNoticeTest();

    /**
     * 对30天以上的已读消息进行硬删除
     * @last-check-in 李国豪
     */
    @Delete("delete from notice where datediff(NOW(), gmt_create) > 30 and is_read = 1")
    void removeExpiredNotices();

    /**
     * 删除所有

     * @param gmtDeleted 删除日期
     */
    @Update("update notice set gmt_deleted = #{gmtDeleted} where " + SOFT_DELETE_TAIL)
    void hideNoticeByAll( Date gmtDeleted);

    /**
     * 恢复所有信息
     */
    @Update("update notice set gmt_deleted = null where gmt_deleted is not null")
    void restoreNoticeByAll();

    /**
     * 根据实训id删除
     *
     * @param id：实训id
     * @param gmtDeleted 删除日期
     */
    @Update("update notice set gmt_deleted = #{gmtDeleted} where id = #{id} and " + SOFT_DELETE_TAIL)
    void hideNoticeById(BigInteger id, Date gmtDeleted);

    /**
     * 根据实训id恢复
     * @param id id
     */
    @Update("update notice set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restoreNoticeById(BigInteger id);


    /**
     * 按id更新notice的信息
     * @param notice 信息
     */
    @Update("update notice set " + UPDATE_CONTENT + " where id = #{id} and " + SOFT_DELETE_TAIL)
    void updateNoticeById(Notice notice);

    /**
     * 查询所有信息
     * @return 信息列表
     */
    @Select("select id, " + COLUMNS + " from notice where " + SOFT_DELETE_TAIL)
    @Results(id = "notice", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "idx_sender_id", property = "senderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_receiver_id", property = "receiverId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_team_id", property = "teamId", jdbcType = JdbcType.BIGINT),
            @Result(column = "notice_type", property = "noticeType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "is_read", property = "isRead", jdbcType = JdbcType.TINYINT)
    })
    List<Notice> findAllNotice();

    /**
     * 根据id查找
     * @param id：id
     * @return 信息
     */
    @Select("select id, " + COLUMNS + " from notice where id = #{id} and " + SOFT_DELETE_TAIL)
    @ResultMap("notice")
    Notice findNoticeById(BigInteger id);

    /**
     * 根据接受者Id查找Notice
     * @param receiverId 接受者Id
     * @return 通知列表
     */
    @Select("select id, " + COLUMNS + " from notice where idx_receiver_id = #{receiverId} and " + SOFT_DELETE_TAIL)
    @ResultMap("notice")
    List<Notice> findNoticeByReceiverId(BigInteger receiverId);

    /**
     * 根据接收团队Id查找notice
     * @param teamId 团队Id
     * @return 通知列表
     */
    @Select("select id, " + COLUMNS + " from notice where idx_team_id = #{teamId} and " + SOFT_DELETE_TAIL)
    @ResultMap("notice")
    List<Notice> findNoticeByTeamId(BigInteger teamId);

    /**
     * 查找内容一致的notice
     * @last-check-in 李国豪
     * @param notice 通知
     * @return 通知信息
     */
    @Select("select id, " + COLUMNS + " from notice where idx_team_id = #{teamId} and idx_receiver_id = #{receiverId} and notice_type = #{noticeType} and content = #{content} and " + SOFT_DELETE_TAIL)
    @ResultMap("notice")
    Notice findNoticeByNotice(Notice notice);

    /**
     * 根据发送者Id查找Notice
     * @param senderId 发送者Id
     * @return 通知列表
     */
    @Select("select id, " + COLUMNS + " from notice where idx_sender_id = #{senderId} and " + SOFT_DELETE_TAIL)
    @ResultMap("notice")
    List<Notice> findNoticeBySenderId(BigInteger senderId);
}
