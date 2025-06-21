package pl.lodz.p.it.landlordkingdom.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.*;

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
