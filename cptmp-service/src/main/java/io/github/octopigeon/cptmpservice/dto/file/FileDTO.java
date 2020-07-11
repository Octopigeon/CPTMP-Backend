package io.github.octopigeon.cptmpservice.dto.file;

import lombok.Data;

import java.util.Date;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in Gh Li
 * @date 2020/7/10
 */
@Data
public class FileDTO {
    private Date gmtCreate;
    private String fileName;
    private String filePath;
    private String originalName;
    private Long fileSize;
    private String fileType;
}
