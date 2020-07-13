package io.github.octopigeon.cptmpdao.model;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 陈若琳
 * @version 2.0
 * @date 2020/07/12
 * @last-check-in 李国豪
 * @date 2020/07/13
 */

@Data
public class Organization {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;
    private String name;
    private String realName;
    private String description;
    private  String websiteUrl;
    private String invitationCode;
}
