package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

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
public class Notice {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;

    private BigInteger senderId;
    private BigInteger receiverId;
    private BigInteger teamId;

    private String noticeType;
    private String content;

    private Boolean isRead;

}
