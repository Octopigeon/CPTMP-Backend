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
 */
@Repository
@Mapper
public interface UserAuthorityMapper {
    @Insert("insert into user_authority (gmt_create, uk_user_id, authority_ids) values (#{gmtCreate}, #{userId}, #{authorityIds})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addAuthorityToUser(UserAuthority userAuthority);

    @Delete("delete from user_authority where uk_user_id = #{userId}")
    void removeUserAuthorityByUserId(BigInteger userId);

    @Update("update user_authority set authority_ids = #{authorityIds}, gmt_modified = #{gmtModified} where uk_user_id = #{userId}")
    void updateAuthorityIdsByUserId(BigInteger userId, String authorityIds, Date gmtModified);

    @Select("select gmt_create, uk_user_id, authority_ids from user_authority")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.DATE),
            @Result(column = "authority_ids", property = "authorityIds", jdbcType = JdbcType.VARCHAR)
    })
    List<UserAuthority> findAllUserAuthority();
}
