package io.github.octopigeon.cptmpdao.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.math.BigInteger;
import java.net.URL;
import java.util.Date;

/**
 * @author 李国鹏
 * @version 1.0
 * @date 2020/7/9
 * <p>
 * last-check-in 李国鹏
 * @date 2020/7/10
 */
@Data
public class TrainProject {
    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private BigInteger trainId;
    private String projectName;
    /**
     * 数字代表难度系数
     */
    private Integer projectLevel;
    private String projectContent;
    /**
     * 项目资源
     */
    private String resourceLibrary;

}
