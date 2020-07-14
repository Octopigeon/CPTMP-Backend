package io.github.octopigeon.cptmpdao.mapper;

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
 * @date 2020/7/14
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/14
 */
@Repository
@Mapper
public class TokenMapper {
    String COLUMNS = "gmt_create, gmt_modified, gmt_deleted,token,email";
    String PROPS = "#{gmtCreate}, #{gmtModified}, #{gmtDeleted},token,email";
    String UPDATE_CONTENT = "gmt_create = #{gmtCreate},  gmt_modified = #{gmtModified}, gmt_deleted = #{gmtDeleted},token = #{token}, email = #{email}";


}
