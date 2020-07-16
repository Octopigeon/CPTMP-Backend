package io.github.octopigeon.cptmpdao.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国豪
 * @version 2.0
 * @date 2020/7/10
 * @last-check-in 李国鹏
 * @date 2020/7/15
 */
@Data
public class AttachmentFile {
    private BigInteger id;
    private Date gmtCreate;
    /** nullable */
    private Date gmtModified;
    private Date gmtDeleted;
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
    /**Url**/
    private String fileUrl;

}
