package top.archer.instagdm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class InstagdmApplication {
    public static void main(String[] args) {
        SpringApplication.run(InstagdmApplication.class, args);
    }
}
