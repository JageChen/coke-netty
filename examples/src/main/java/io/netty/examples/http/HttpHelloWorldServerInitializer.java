package io.netty.examples.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;

/**
 * description: HttpHelloWorldServerInitializer <br>
 * date: 2020/5/7 17:51 <br>
 * author: EDZ <br>
 * version: 1.0 <br>
 */
public class HttpHelloWorldServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        /**
         * 或者使用HttpRequestDecoder & HttpResponseEncoder
         */
        p.addLast(new HttpServerCodec());
        /**
         * 在处理POST消息体时需要加上
         */
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpHelloWorldServerHandler());
    }
}