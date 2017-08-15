package org.yoqu.study;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author yoqu
 * @date 2017年08月15日
 * @time 上午11:02
 * @email wcjiang2@iflytek.com
 */
public class Client {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("实例化ClientHandler");
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
        ChannelFuture f = null;
        try {
            f = b.connect("127.0.0.1",8083).sync();
            //buf
            f.channel().write(Unpooled.copiedBuffer("asjdq".getBytes()));
            f.channel().write(Unpooled.copiedBuffer("777".getBytes()));
            f.channel().flush();
            f.channel().closeFuture().sync();

            group.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
