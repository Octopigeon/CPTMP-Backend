package io.github.octopigeon;

import io.github.octopigeon.cptmpservice.config.ApplicationContextUtil;
import io.github.octopigeon.cptmpservice.config.FaceProperties;
import io.github.octopigeon.cptmpservice.config.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableConfigurationProperties({FileProperties.class, FaceProperties.class})
@SpringBootApplication
@EnableScheduling
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class CptmpWebApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CptmpWebApplication.class, args);
        ApplicationContextUtil.setApplicationContext(applicationContext);
    }

}
