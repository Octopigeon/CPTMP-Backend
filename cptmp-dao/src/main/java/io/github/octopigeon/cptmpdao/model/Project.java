package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.1
 * @date 2020/7/9
 * 对应train_project表
 * last-check-in 李国豪
 * @date 2020/7/13
 */
@Data
public class Project {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private Date gmtDeleted;
    private String name;
    /**
     * 数字代表难度系数
     */
    private Integer level;
    private String content;
    /**
     * 项目资源
     */
    private String resourceLibrary;

}
