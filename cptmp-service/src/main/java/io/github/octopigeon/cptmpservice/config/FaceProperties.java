package io.github.octopigeon.cptmpservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置类：用于读取配置文件中，与人脸识别相关配置
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/19
 * @last-check-in 李国豪
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
