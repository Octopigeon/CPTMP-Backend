package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;
/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/8
 *
 * @last-check-in 李国鹏
 * @date 2020/7/8
 */
@Data
//活动记录
public class ActivityRecord {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    /**
     * 0表示待完成
     * 1表示已完成
     * 其余表示进行中
     *
     */
    private int state;
    private String event;
    private BigInteger userId;
    private BigInteger teamId;

}
