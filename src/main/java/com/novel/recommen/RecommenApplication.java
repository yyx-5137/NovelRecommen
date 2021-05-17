package com.novel.recommen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({com.rongzhiweilai.extension.config.WebListenerConfiguration.class})
public class RecommenApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecommenApplication.class, args);
    }

}
