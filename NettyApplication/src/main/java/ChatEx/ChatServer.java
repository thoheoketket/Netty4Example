package ChatEx;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;

public class ChatServer {
    static final int PORT = 8007; // cổng nghe của server

    public static void main(String[] args) throws Exception {
        //cấu hình server
        //bossgroup tiếp nhận kết nối tới từ client
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        //worker xử lý các giao tiếp tiếp theo qua kết nối
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup,workerGroup) //thiết lập boss và worker
                    .channel(NioServerSocketChannel.class)//tiếp nhận kết nối
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            //vì trong socket và channel liên lạc bằng byte streams
                            //String decoder và endcoder sẽ chuyển đổi giữa byte và string
                            p.addLast(new StringDecoder());
                            p.addLast(new StringEncoder());
                            //handler sẽ xử lý ở server
                            p.addLast(new ChatServerHandler());
                        }
                    });
            //khởi chạy server
            ChannelFuture f = b.bind(PORT).sync();
            System.out.println("Chat server started. Ready to accpect clients");

            //đợi đến khi kết nối đóng
            f.channel().closeFuture().sync();
        }finally {
            //shutdown tất cả eventloop để chấm dứt các thread
            workerGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
        }
    }
}
