package DiscardServerEx;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DiscardClientHandler {
    private ByteBuf content;
    private ChannelHandlerContext ctx;

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        this.ctx = ctx;
//    }
}
