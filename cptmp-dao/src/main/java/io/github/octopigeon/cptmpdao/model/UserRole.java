package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author anlow
 */
@Data
public class UserRole {

    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private BigInteger userId;
    private String roleName;
    private String authorityIds;

}
