package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/8
 *
 * last-check-in 李国鹏
 * @date 2020/7/8
 */
@Data

/**
 * 日志
 */
public class DailyRecord {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private String title;
    private String content;
    private BigInteger userId;
    private String documentPath;

    /**
     * 0代表是纯文本
     * 1代表是XX
     * 2XXX
     *
     * 待定
     */
    private int recordType;
}
