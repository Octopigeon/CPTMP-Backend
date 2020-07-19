package io.github.octopigeon.cptmpservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/19
 * @last-check-in Gh Li
 * @date 2020/7/19
 */
@ConfigurationProperties(prefix = "face")
@Data
public class FaceProperties {
    private String endpoint;
    private String secretId;
    private String secretKey;
    private String region;
    private String groupId;
}
