package io.github.octopigeon.cptmpdao.model;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 陈若琳
 * @version 1.0
 * @date 2020/07/12
 * @last-check-in 陈若琳
 * @date 2020/07/12
 */

@Data
public class Organization {
    private BigInteger id;
    private String name;
    private String description;
    private  String websiteUrl;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDelete;
    private String invitationCode;
}
