package io.github.octopigeon.cptmpservice.dto.file;

import lombok.Data;

import java.util.Date;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in 李国豪
 * @date 2020/7/10
 */
@Data
public class FileDTO {
    private Date gmtCreate;
    private String fileName;
    private String filePath;
    private String fileUrl;
    private String originName;
    private Long fileSize;
    private String fileType;
}
