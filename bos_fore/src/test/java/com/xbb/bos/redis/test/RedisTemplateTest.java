package com.xbb.bos.redis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * redis模板测试
 * Created by xbb on 2018/1/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void testRedis(){
        //保存key value
        //设置30秒失效
        redisTemplate.opsForValue().set("city","西安",30, TimeUnit.SECONDS);

        System.out.println(redisTemplate.opsForValue().get("city"));
    }

}
