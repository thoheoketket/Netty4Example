package ChatEx;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.ArrayList;
import java.util.List;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    //danh sách các channel của client kết nối
    static final List<Channel> channels = new ArrayList<Channel>();

    //mỗi khi có một client kết nối tới server,
    //thì add channel của client đó vào danh sách channel
    @Override
    public void channelActive(final ChannelHandlerContext ctx) { //(1)
        System.out.printf("Client joined - "+ctx);
        channels.add(ctx.channel());
    }

    //khi nhận được tin nhắn từ một client, gửi tin nhắn tới tất cả các kênh
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception { //(2)
        System.out.println("Server received - "+msg);
        for (Channel c: channels){
            c.writeAndFlush("->" + msg +'\n');
        }
    }

    //gặp phải ngoại lệ thì đóng kênh
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("closing connection for client - " + ctx);
        ctx.close();
    }
}
