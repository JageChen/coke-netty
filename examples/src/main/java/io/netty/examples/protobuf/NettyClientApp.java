package io.netty.examples.protobuf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * description: NettyServerApp
 * date: 2020/5/7 21:47
 * author: Jage
 * version: 1.0
 */
@SpringBootApplication
@ComponentScan({"io.netty.examples.protobuf.client"})
public class NettyClientApp {
    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(NettyClientApp.class);
    }
}