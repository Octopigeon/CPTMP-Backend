package io.github.octopigeon.cptmpdao.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/7
 */
@Data
public class SchoolInstructor {

    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private String name;
    private String schoolName;
    private String userId;
    private String employeeId;

}
