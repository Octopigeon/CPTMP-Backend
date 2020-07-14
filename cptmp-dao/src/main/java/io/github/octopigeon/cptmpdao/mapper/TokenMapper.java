package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.Token;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/14
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/14
 */
@Repository
@Mapper
public interface TokenMapper {
    String COLUMNS="gmt_create, gmt_modified, gmt_deleted,token,email";
    String PROPS="#{gmtCreate}, #{gmtModified}, #{gmtDeleted}, #{token},#{email}";
    String UPDATE_CONTENT="gmt_create = #{gmtCreate},gmt_modified = #{gmtModified}, gmt_deleted = #{gmtDeleted}, token = #{token}, email = #{email}";

    /**
     * 插入
     * @param token
     */
    @Insert("insert into token (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addToken(Token token);

    /**
     * 测试用
     */
    @Deprecated
    @Delete("delete from token")
    void removeAllTokenTest();

    /**
     * @param gmtDeleted 删除时间
     */
    @Update("update token set gmt_deleted = #{gmtDeleted} where gmt_deleted is null")
    void hideAllToken(Date gmtDeleted);

    /**
     * 恢复所有的token
     */
    @Update("update token set gmt_deleted = null where gmt_deleted is not null")
    void restoreAllToken();

    /**
     * 根据id删除
     * @param id：id
     * @param gmtDeleted 删除日期
     */
    @Update("update token set gmt_deleted = #{gmtDeleted} where id = #{id} and gmt_deleted is not null")
    void hideTokenById(BigInteger id,Date gmtDeleted);

    /**
     * 根据id恢复
     * @param id：id
     */
    @Update("update token set gmt_deleted = null where id = #{id} and gmt_deleted is null")
    void restoreTokenById(BigInteger id);



    /**
     * 根据id修改
     */
    @Update("update token set "+ UPDATE_CONTENT+"  where id = #{id} and gmt_deleted is null")
    void updateTokenById(Token token);



    /**
     * 查询所有token
     * @return token列表
     */
    @Select("select id, " + COLUMNS + " from token where gmt_deleted is null")
    @Results(id = "token", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_deleted", property = "gmtDeleted", jdbcType = JdbcType.DATE),
            @Result(column = "token", property = "token", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),

    })
    List<Token> findAllToken();

    /**
     * 根据id查找
     * @param id：id
     * @return token
     */
    @Select("select id, " + COLUMNS + " from token where id = #{id} and gmt_deleted is null")
    @ResultMap("token")
    Token findTokenById(BigInteger id);


}
