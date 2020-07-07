package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.UserRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/8
 */
@Repository
@Mapper
public interface UserRoleMapper {
    /**
     * 添加一条数据
     * @param userRole：用户角色对应类
     */
    @Insert("insert into user_role (gmt_create, uk_user_id, idx_role_name, authority_ids) values (#{gmtCreate}, #{userId}, #{roleName}, #{authorityIds})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addUserRole(UserRole userRole);

    /**
     * 根据用户账号删除与角色对应关系
     * @param userId:用户账号
     */
    @Delete("delete from user_role where uk_user_id = #{userId}")
    void removeUserRoleByUserId(BigInteger userId);

    /**
     * 根据用户账号更新数据
     * @param userId：用户账号
     * @param gmtModified：更新时间
     * @param roleName：角色名称
     * @param authorityIds：权限序列
     */
    @Update("update user_role set gmt_modified = #{gmtModified}, idx_role_name = #{roleName}, authority_ids = #{authorityIds} where uk_user_id = #{userId}")
    void updateUserRoleByUserId(BigInteger userId, Date gmtModified, String roleName, String authorityIds);

    /**
     * 查找所有用户与角色对应关系
     * @return UserRole列表
     */
    @Select("select uk_user_id, idx_role_name, authority_ids from user_role group by idx_role_name")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_role_name", property = "roleName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "authority_ids", property = "authorityIds", jdbcType = JdbcType.VARCHAR)
    })
    List<UserRole> findAllUserRoles();

    /**
     * 根据roleName查找Userrole关系
     * @return UserRole列表
     */
    @Select("select uk_user_id, idx_role_name, authority_ids from user_role where idx_role_name = #{roleName}")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_role_name", property = "roleName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "authority_ids", property = "authorityIds", jdbcType = JdbcType.VARCHAR)
    })
    List<UserRole> findUserRolesByRoleName(String roleName);

    /**
     * 根据UserId查找Userrole关系
     * @return UserRole
     */
    @Select("select uk_user_id, idx_role_name, authority_ids from user_role where uk_user_id = #{userId}")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_role_name", property = "roleName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "authority_ids", property = "authorityIds", jdbcType = JdbcType.VARCHAR)
    })
    UserRole findUserRolesByUserId(BigInteger userId);
}
