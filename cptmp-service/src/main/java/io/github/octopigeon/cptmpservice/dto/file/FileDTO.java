package io.github.octopigeon.cptmpservice.dto.file;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * 文件基本信息类
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in 李国豪
 * @date 2020/7/10
 */
@Data
public class FileDTO {
    /** 创建时间 */
    private Date gmtCreate;
    /** 服务器存储的文件名 */
    private String fileName;
    /** 服务器存储文件的路径 */
    private String filePath;
    /** 访问文件的url */
    private String fileUrl;
    /** 文件上传时的原始文件名 */
    private String originName;
    /** 文件大小 */
    private BigInteger fileSize;
    /** 文件类型 */
    private String fileType;
}
