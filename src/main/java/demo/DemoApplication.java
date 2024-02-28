package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String... args) {
        //new ModuleAndWorkerAwareSpringApplication(DemoApplication.class).run(args);
        new SpringApplication(DemoApplication.class).run(args);
    }

}
