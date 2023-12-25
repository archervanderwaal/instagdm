package top.archer.instagdm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@MapperScan("top.archer.instagdm.mapper")
@EnableScheduling
public class InstagdmApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstagdmApplication.class, args);
    }
}
