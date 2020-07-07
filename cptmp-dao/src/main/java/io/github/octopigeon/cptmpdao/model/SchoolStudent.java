package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author anlow,Gh Li
 * @version 1.1
 * @date 2020/7/7
 */
@Data
public class SchoolStudent {

    private BigInteger id;
    private Date gmtCreate;
    private Date gmtModified;
    private String name;
    private String schoolName;
    private BigInteger userId;
    private BigInteger studentId;
    /**
     * 学生证件照的URL，用于人脸比对从而实现人脸识别
     */
    private String studentFace;

}
