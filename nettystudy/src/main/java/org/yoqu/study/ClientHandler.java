package org.yoqu.study;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author yoqu
 * @date 2017年08月15日
 * @time 上午11:08
 * @email wcjiang2@iflytek.com
 */
public class ClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf b= (ByteBuf) msg;
        byte[] bytes = new byte[b.readableBytes()];
        b.readBytes(bytes);
        System.out.println(new String(bytes));
//        ((ByteBuf)msg).release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
