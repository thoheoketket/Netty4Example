package HTTPServerEx;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class AppServer {
    private static final int HTTP_PORT = 8034;

    public void run() throws Exception {
        //tạo các vòng lặp sự kiện đa luồng cho server
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //class hỗ trợ đơn giản hóa việc cấu hình server
            ServerBootstrap httpBootstrap = new ServerBootstrap();
            //cấu hình server
            httpBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer()) //<-- handler được tạo ở đây
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // liên kết và khởi chạy để chấp nhận kết nối tới
            ChannelFuture httpChannel = httpBootstrap.bind(HTTP_PORT).sync();

            // đợi đến khi kết nối đóng
            httpChannel.channel().closeFuture().sync();
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new AppServer().run();
    }
}
