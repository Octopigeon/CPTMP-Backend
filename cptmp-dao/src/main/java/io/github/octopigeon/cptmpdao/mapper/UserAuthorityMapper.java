package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.UserAuthority;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author Gh Li
 * @version 1.1
 * @date 2020/7/8
 */
@Repository
@Mapper
public interface UserAuthorityMapper {
    /**
     * 插入一条数据
     * @param userAuthority：类
     */
    @Insert("insert into user_authority (gmt_create, uk_user_id, authority_ids) values (#{gmtCreate}, #{userId}, #{authorityIds})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addAuthorityToUser(UserAuthority userAuthority);

    /**
     * 根据userId删除一条数据
     * @param userId：用户账号
     */
    @Delete("delete from user_authority where uk_user_id = #{userId}")
    void removeUserAuthorityByUserId(BigInteger userId);

    /**
     * 根据UserId更新一条数据
     * @param userId：用户账号
     * @param authorityIds：权限序列
     * @param gmtModified：修改时间
     */
    @Update("update user_authority set authority_ids = #{authorityIds}, gmt_modified = #{gmtModified} where uk_user_id = #{userId}")
    void updateAuthorityIdsByUserId(BigInteger userId, String authorityIds, Date gmtModified);

    /**
     * 查找所有用户与权限对应关系
     * @return
     */
    @Select("select gmt_create, uk_user_id, authority_ids from user_authority")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "authority_ids", property = "authorityIds", jdbcType = JdbcType.VARCHAR)
    })
    List<UserAuthority> findAllUserAuthority();
}