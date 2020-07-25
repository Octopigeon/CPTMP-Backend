package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.Recruitment;
import io.github.octopigeon.cptmpdao.model.Team;
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
 * @date 2020/7/20
 */
@Repository
@Mapper
public interface RecruitmentMapper {
    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted, start_time, end_time, photo, title, website_url";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{startTime},#{endTime}, #{photo}, #{title}, #{websiteUrl}";
    String UPDATE_CONTENT = "gmt_modified = #{gmtModified}, start_time = #{startTime}, end_time = #{endTime}, photo = #{photo}, title = #{title}, website_url = #{websiteUrl}";
    /**
     * 插入招聘信息
     * @param recruitment 招聘表
     */
    @Insert("insert into recruitment (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addRecruitment(Recruitment recruitment);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from recruitment")
    void removeAllRecruitmentTest();

    /**
     * 删除全部

     * @param gmtDeleted 删除日期
     */
    @Update("update recruitment set gmt_deleted = #{gmtDeleted} where  gmt_deleted is null")
    void hideRecruitmentByAll(Date gmtDeleted);

    /**
     * 恢复全部
     */
    @Update("update recruitment set gmt_deleted = null where  gmt_deleted is not null")
    void restoreRecruitmentByAll();
    /**
     * 根据id删除
     *
     * @param id：id
     * @param gmtDeleted 删除日期
     */
    @Update("update recruitment set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is null")
    void hideRecruitmentById(BigInteger id, Date gmtDeleted);

    /**
     * 根据id恢复
     * @param id id
     */
    @Update("update recruitment set gmt_deleted = null where id = #{id} and gmt_deleted is not null")
    void restoreRecruitmentById(BigInteger id);


    /**
     * 根据id修改
     * @param recruitment 招聘表
     */
    @Update("update recruitment set " + UPDATE_CONTENT + "  where id = #{id} and gmt_deleted is null")
    void updateRecruitmentById(Recruitment recruitment);


    /**
     * 查询所有
     * @return 招聘列表
     */
    @Select("select id, " + COLUMNS + " from recruitment where gmt_deleted is null")
    @Results(id = "recruitment", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "start_time", property = "startTime", jdbcType = JdbcType.DATE),
            @Result(column = "end_time", property = "endTime", jdbcType = JdbcType.DATE),
            @Result(column = "photo", property = "photo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "website_url", property = "websiteUrl", jdbcType = JdbcType.VARCHAR)

    })
    List<Recruitment> findAllRecruitment();

    /**
     * 根据id查找
     *
     * @param id：id
     * @return 招聘列表
     */
    @Select("select id, " + COLUMNS + " from recruitment where id = #{id} and gmt_deleted is null")
    @ResultMap("recruitment")
    Recruitment findRecruitmentById(BigInteger id);

}
