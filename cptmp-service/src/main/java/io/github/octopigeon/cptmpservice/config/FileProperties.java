package io.github.octopigeon.cptmpservice.config;

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
    private String uploadDir;
    private String domain;
}
