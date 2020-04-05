package DiscardServerEx;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

import javax.net.ssl.SSLException;

public class DiscardClient {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host","127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port","8009"));
    static final int SIZE = Integer.parseInt(System.getProperty("size","256"));

    public static void main(String[] args) throws Exception {
        final SslContext sslCtx;
        if (SSL){
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else{
            sslCtx = null;
        }
        EventLoopGroup group = new NioEventLoopGroup();
    }
}
