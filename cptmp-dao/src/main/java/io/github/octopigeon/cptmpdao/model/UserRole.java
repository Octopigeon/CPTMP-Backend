package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/7
 */
@Data
public class UserRole {

    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private BigInteger userId;
    private String roleName;
    /**
     * 角色拥有的权限种类，用json表示
     */
    private String authorityIds;

}
