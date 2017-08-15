package org.yoqu.study;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author yoqu
 * @date 2017年08月14日
 * @time 下午5:40
 * @email wcjiang2@iflytek.com
 */
public class Server {
    public static void main(String[] args) {
        //第一个线程组接收client端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //第二个线程组是处理具体业务的
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //创建bootstrap，对server进行一系列的配置
        ServerBootstrap b = new ServerBootstrap();
        //绑定线程组
        b.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)//指定使用NIOServerSocketChannel通道
                .childHandler(new ChannelInitializer<SocketChannel>() {//处理器
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("实例化ServerHandler");
                        socketChannel.pipeline().addLast(new ServerHandler());
                    }
                })
                /**
                 * 服务器端TCP内核模块维护2个队列，我们称之为A,B。
                 * 客户端向服务器端connect的时候，会发送带有SYN标志的包（第一次握手）
                 * 服务器收到客户端发来的SYN时，向客户端发送SYN ACK确认（第二次握手）
                 * 此时TCP内核模块把客户端连接加入到A队列中，然后服务器收到客户端发来的ACK时（第三次握手），
                 * TCP内核模块把客户端连接从A队列移动到B队列，连接完成，应用程序的accept会返回。
                 * 也就是说accept从B队列中取出完成三次握手的连接。
                 * A队列和B队列的长度之和是backlog，当A、B队列的长度之和大于backlog时，新连接会被TCP内核拒绝，
                 * 所以，如果backlog过小，可能会出现accept速度跟不上，A、B队列满了，导致新的客户端无法连接。
                 * 需要注意的时：backlog对程序支持的连接并无影响，backlog影响只是还没有被accept取出的连接。
                 */
                //tcp连接缓冲区
                .option(ChannelOption.SO_BACKLOG, 128)
                //保持连接
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            //绑定指定端口监听
            ChannelFuture future = b.bind(8083).sync();
            Thread.sleep(10000000);
//            future.channel().close().sync();
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
