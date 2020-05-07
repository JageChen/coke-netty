package io.netty.examples.protobuf.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.examples.protobuf.pojo.UserMsg;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description: NettyClientHandler
 * date: 2020/5/7 22:24
 * author: Jage
 * version: 1.0
 */
@ChannelHandler.Sharable
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    private NettyClient nettyClient;

    //todo 循环次数
    private AtomicInteger fcount = new AtomicInteger(1);

    /**
     *todo 建立连接时
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("建立连接时：" + new Date());
        ctx.fireChannelActive();
    }

    /**
     *todo 关闭连接时
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("关闭连接时：" + new Date());
        final EventLoop eventLoop = ctx.channel().eventLoop();
        nettyClient.doConnect(new Bootstrap(), eventLoop);
        super.channelInactive(ctx);
    }

    /**
     *todo 心跳请求处理 每4秒发送一次心跳请求;
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        log.info("循环请求的时间：" + new Date() + "，次数" + fcount.get());
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            // 如果写通道处于空闲状态,就发送心跳命令
            if (IdleState.WRITER_IDLE.equals(event.state())) {
                UserMsg.User.Builder userState = UserMsg.User.newBuilder().setState(2);
                ctx.channel().writeAndFlush(userState);
                fcount.getAndIncrement();
            }
        }
    }

    /**
     *todo 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //todo 如果不是protobuf类型的数据
        if (!(msg instanceof UserMsg.User)) {
            log.info("未知数据!" + msg);
            return;
        }
        try {
            //todo 得到protobuf的数据
            UserMsg.User userMsg = (UserMsg.User) msg;
            //todo 进行相应的业务处理。。。
            //todo 这里就从简了，只是打印而已
            log.info(
                    "客户端接受到的用户信息。编号:" + userMsg.getId() + ",姓名:" + userMsg.getName() + ",年龄:" + userMsg.getAge());
            //todo 这里返回一个已经接受到数据的状态
            UserMsg.User.Builder userState = UserMsg.User.newBuilder().setState(1);
            ctx.writeAndFlush(userState);
            log.info("成功发送给服务端!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
