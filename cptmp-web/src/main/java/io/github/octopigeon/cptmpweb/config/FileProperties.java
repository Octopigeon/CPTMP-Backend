package io.github.octopigeon.cptmpweb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in Gh Li
 * @date 2020/7/10
 */
@ConfigurationProperties(prefix = "file")
@Data
public class FileProperties {
    private String uploadBaseDir;
    private String uploadFile;
    private String uploadUserAvatar;
    private String uploadUserFace;
}
