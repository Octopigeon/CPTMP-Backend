package io.github.octopigeon;

import io.github.octopigeon.cptmpweb.config.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableConfigurationProperties(FileProperties.class)
@SpringBootApplication
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class CptmpWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CptmpWebApplication.class, args);
    }

}
