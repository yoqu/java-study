package org.yoqu.study;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @author yoqu
 * @date 2017年08月14日
 * @time 下午5:36
 * @email wcjiang2@iflytek.com
 */
public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
//        ((ByteBuf) msg).release();
        try {
            ByteBuf buf= (ByteBuf) msg;
            byte[] data=new byte[buf.readableBytes()];
            buf.readBytes(data);
            String request=new String(data,"utf-8");
            System.out.println(request);
            //返回数据给客户端
            String response ="response";
            ctx.write(Unpooled.copiedBuffer(response.getBytes()));
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
