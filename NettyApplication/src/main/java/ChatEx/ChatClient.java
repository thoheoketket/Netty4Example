package ChatEx;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class ChatClient {
    static final String HOST = "127.0.0.1";
    static final int PORT = 8007;
    static String clientName;

    public static void main(String[] args) throws Exception {
        Scanner  scanner = new Scanner(System.in);
        System.out.println("Please enter your name: ");
        if (scanner.hasNext()){
            clientName = scanner.nextLine();
            System.out.println("Welcome "+ clientName);
        }

        //cấu hình client
        //chỉ cần một EventLoopGroup vì là phía client
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group) //thiết lập EventLoopGroup để xử lý sự kiện cho client
                    .channel(NioSocketChannel.class) //sử dụng nio để chấp nhận kết nối
                    .handler(new ChannelInitializer<SocketChannel>() {
                                 @Override
                                 protected void initChannel(SocketChannel ch) throws Exception {
                                     ChannelPipeline p = ch.pipeline();
                                     p.addLast(new StringDecoder());
                                     p.addLast(new StringEncoder());
                                     //Handler sẽ xử lý chat
                                     p.addLast(new ChatClientHandler());
                                 }
                             });
            //khởi chạy client
            ChannelFuture f = b.connect(HOST,PORT).sync();

            //lặp lại và nhận tin nhắn từ user và gửi tới server
            while (scanner.hasNext()){
                String input = scanner.nextLine();
                Channel channel = f.sync().channel();
                channel.writeAndFlush("[" + clientName + "]: " + input);
                channel.flush();
            }

            //đợi tới khi kết nối đóng
            f.channel().closeFuture().sync();
        }finally {
            //đóng các enventloop để
            group.shutdownGracefully();
        }

    }
}
