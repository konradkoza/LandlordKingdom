package pl.lodz.p.it.ssbd2024.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Objects;

@Configuration
@Slf4j
public class ToolConfig {
//    @Value("${scheduler.threadPool.size}")
//    private int threadPoolSize;



//    @Bean
//    public TaskScheduler taskScheduler() {
//        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
//        taskScheduler.setPoolSize(threadPoolSize);
//        return taskScheduler;
//    }

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        String profile = System.getenv("ACTIVE_PROFILE");
//        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
//        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//        try {
//            yaml.setResources(new ClassPathResource("application-" + profile + ".yml"));
//            propertySourcesPlaceholderConfigurer.setProperties(Objects.requireNonNull(yaml.getObject()));
//        } catch (Exception e) {
//            log.error("Failed to load application-" + profile + ".yml");
//            yaml.setResources(new ClassPathResource("application-prod.yml"));
//            propertySourcesPlaceholderConfigurer.setProperties(Objects.requireNonNull(yaml.getObject()));
//        }
//        return propertySourcesPlaceholderConfigurer;
//    }
}
