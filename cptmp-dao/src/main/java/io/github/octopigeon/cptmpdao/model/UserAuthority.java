package io.github.octopigeon.cptmpdao.model;

import lombok.Data;
import java.math.BigInteger;
import java.util.Date;

@Data
public class UserAuthority {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private BigInteger userId;
    private String authorityIds;
}
