package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.CptmpUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author anlow
 * @version 1.1
 * @date 2020/7/7
 * 添加邀请码字段
 * @last-check-in GH Li
 * @date 2020/7/9
 */
@Repository
@Mapper
public interface CptmpUserMapper {

    String COLUMNS = "gmt_create, gmt_modified, introduction, email, phone_number, " +
            "gender, avatar, uk_username, idx_password, idx_role_name, enabled, " +
            "account_non_expired, credentials_non_expired, account_non_locked, invitation_code";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{introduction}, #{email}, #{phoneNumber}, " +
            "#{male}, #{avatar}, #{username}, #{password}, #{roleName}, #{enabled}, " +
            "#{accountNonExpired}, #{credentialsNonExpired}, #{accountNonLocked}, #{invitationCode}";

    @Insert("insert into cptmp_user (" + COLUMNS + ") values (" + PROPS + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addUser(CptmpUser cptmpUser);

    @Delete("delete from cptmp_user")
    void removeAllUsers();

    /**
     * 通过用户名获取用户，可以用来进行登录验证
     * @param username 用户名，唯一
     */
    @Select("select id, " + COLUMNS + " from cptmp_user where uk_username = #{username}")
    @Results(id = "user", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "introduction", property = "introduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone_number", property = "phoneNumber", jdbcType = JdbcType.DECIMAL),
            @Result(column = "gender", property = "male", jdbcType = JdbcType.TINYINT),
            @Result(column = "avatar", property = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_role_name", property = "roleName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "enabled", property = "enabled", jdbcType = JdbcType.TINYINT),
            @Result(column = "account_non_expired", property = "accountNonExpired", jdbcType = JdbcType.TINYINT),
            @Result(column = "credentials_non_expired", property = "credentialsNonExpired", jdbcType = JdbcType.TINYINT),
            @Result(column = "account_non_locked", property = "accountNonLocked", jdbcType = JdbcType.TINYINT),
    })
    CptmpUser findUserByUsername(String username);

    @Select("select id, " + COLUMNS + " from cptmp_user")
    @ResultMap(value = "user")
    List<CptmpUser> findAllUsers();

}
