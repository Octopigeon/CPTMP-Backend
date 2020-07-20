package io.github.octopigeon.cptmpdao.model;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/20
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/20
 */
@Data
public class Recruitment {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;
    private Date startTime;
    private Date endTime;
    private String photo;
    private String title;
    private String websiteUrl;

}
