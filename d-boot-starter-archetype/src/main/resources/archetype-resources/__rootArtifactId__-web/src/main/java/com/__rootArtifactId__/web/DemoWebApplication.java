package ${package}.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "${package}.web")
public class DemoWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoWebApplication.class, args);
    }

}
