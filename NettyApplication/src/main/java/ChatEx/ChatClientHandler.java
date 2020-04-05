package ChatEx;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    //in lời nhắn nhận được từ server
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println("Message: "+ msg);
    }
}
