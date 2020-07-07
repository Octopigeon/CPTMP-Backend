package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/7
 */
@Repository
@Mapper
public interface UserMapper {

    @Insert("insert into cptmp_user (gmt_create, idx_password, uk_username, introduction, contact_info, gender, avatar) values (#{gmtCreate}, #{password}, #{username}, #{introduction}, #{contactInfo}, #{male}, #{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addUser(User user);

    @Delete("delete from cptmp_user")
    void removeAllUsers();

    /**
     * 通过用户名获取用户，可以用来进行登录验证
     * @param username 用户名，唯一
     */
    @Select("select id, gmt_create, gmt_modified, introduction, contact_info, gender, avatar, uk_username, idx_password from cptmp_user where uk_username = #{username}")
    @Results(id = "user", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.DATE),
            @Result(column = "introduction", property = "introduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "contact_info", property = "contactInfo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "gender", property = "male", jdbcType = JdbcType.TINYINT),
            @Result(column = "avatar", property = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idx_password", property = "password", jdbcType = JdbcType.VARCHAR)
    })
    User findUserByUsername(String username);

    @Select("select id, gmt_create, gmt_modified, introduction, contact_info, gender, avatar, uk_username, idx_password from cptmp_user")
    @ResultMap(value = "user")
    List<User> findAllUsers();

}
