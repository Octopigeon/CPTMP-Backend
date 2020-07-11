package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in Gh Li
 * @date 2020/7/10
 */
@Data
public class AttachmentFile {
    private BigInteger id;
    private Date gmtCreate;
    /** nullable */
    private Date gmtModified;
    /** 后端存储使用唯一文件名 */
    private String fileName;
    /** 唯一文件路径 */
    private String filePath;
    /** 上传时文件名 */
    private String originName;
    /** 文件大小 */
    private BigInteger fileSize;
    /** 文件类型 */
    private String fileType;
    /** 用户id */
    private BigInteger userId;
    /** 团队id nullable */
    private BigInteger teamId;
}
