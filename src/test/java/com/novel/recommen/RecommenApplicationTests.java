package com.novel.recommen;

import com.novel.recommen.oss.OssClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;


class RecommenApplicationTests {

    @Test
    void contextLoads() throws IOException {
        System.out.println(1);
        OssClient s = new OssClient();
        s.GetRandomBook();
    }

}
