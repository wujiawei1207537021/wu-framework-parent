package com.wu.freamwork;

import com.wu.freamwork.controller.RedisController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
class RedisControllerTest {

    @Autowired
    RedisController redisController;

    @Test
    void publish() {
        final Long publish = redisController.publish("channel", LocalDateTime.now().toString());
        System.out.println(publish);
    }

    @Test
    void publishThread() {
        while (true) {
            final Long publish = redisController.publish("channel", LocalDateTime.now().toString());
            System.out.println(publish);
        }
    }

    @Test
    void subscribe() {
        redisController.subscribe("channel");
    }
}