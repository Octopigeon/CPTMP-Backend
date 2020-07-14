package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.3
 * @date 2020/7/9
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/12
 */
@Data
public class Train {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;
    private String name;
    private BigInteger organizationId;
    private Date startDate;
    private Date finishDate;
    private String content;
    /**
     * 验收标准
     */
    private String standard;
    /**
     * 实训资源
     */
    private String resourceLibrary;
    private String gpsInfo;
}
