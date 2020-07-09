package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.PasswordResetToken;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in anlow
 * @date 2020/7/9
 */
@Repository
@Mapper
public interface PasswordResetTokenMapper {

    String COLUMNS = "gmt_create, gmt_modified, token, idx_email";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{token}, #{email}";

    @Insert("insert password_reset_token (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addPasswordResetToken(PasswordResetToken passwordResetToken);

    /**
     * 用于定时删除token，24小时
     * @param id token的id
     */
    @Delete("delete from password_reset_token where id = #{id}")
    void removePasswordResetTokenById(BigInteger id);

    /**
     * 测试用
     * @return
     */
    @Deprecated
    @Select("select * from password_reset_token")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "token", property = "token", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_email", property = "email", jdbcType = JdbcType.VARCHAR)
    })
    List<PasswordResetToken> findAllPasswordResetTokens();

    @Deprecated
    @Delete("delete from password_reset_token")
    void removeAllPasswordResetToken();

}
