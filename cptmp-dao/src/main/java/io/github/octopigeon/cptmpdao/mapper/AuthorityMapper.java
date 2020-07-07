package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.Authority;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Gh Li
 */
@Repository
@Mapper
public interface AuthorityMapper {
    @Select("select id, uk_name from authority group by idx_table_name")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "uk_name", property = "name", jdbcType = JdbcType.VARCHAR)
    })
    List<Authority> findAllAuthorities();
}
