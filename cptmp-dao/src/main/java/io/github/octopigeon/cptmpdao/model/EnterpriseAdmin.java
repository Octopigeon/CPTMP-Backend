package io.github.octopigeon.cptmpdao.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author anlow
 * @version 1.1
 * @date 2020/7/7
 *
 * @last-check-in 李国豪
 * @date 2020/7/8
 */
@Data
public class EnterpriseAdmin {

    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private String name;
    private BigInteger userId;
    private String employeeId;

}
