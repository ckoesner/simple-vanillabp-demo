package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OpenAiProperties.class)
public class DemoApplication {

    public static void main(String... args) {
        //new ModuleAndWorkerAwareSpringApplication(DemoApplication.class).run(args);
        new SpringApplication(DemoApplication.class).run(args);
    }

}
