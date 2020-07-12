package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;
/**
 * @author 李国鹏
 * @version 1.3
 * @date 2020/7/8
 *
 * @last-check-in 李国鹏
 * @date 2020/7/12
 */
@Data
//活动记录
public class ActivityRecord {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;
    private BigInteger userId;
    private BigInteger teamId;
    /**
     * 0表示待完成
     * 1表示已完成
     * 其余表示进行中
     *
     */
    private int state;
    private String event;


}
