package io.github.octopigeon;

import io.github.octopigeon.cptmpweb.config.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(FileProperties.class)
@SpringBootApplication
public class CptmpWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CptmpWebApplication.class, args);
    }

}
