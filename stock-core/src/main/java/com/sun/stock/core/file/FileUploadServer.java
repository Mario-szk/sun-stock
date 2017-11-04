package com.sun.stock.core.file;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * Created by zksun on 2017/8/18.
 */
public class FileUploadServer {

    private final int MAX_FRAME_LENGTH = 1024 * 1024;
    private final int LENGTH_FILE_LENGTH = 4;
    private final int LENGTH_FILE_OFFSET = 13;
    private final int LENGTH_ADJUSTMENT = 0;
    private final int INITIAL_BYTES_TO_STRIP = 0;

    public void bind(int port, String path) {
        final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(128 * 1024, 256 * 1024))
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")))
                                .addLast(new FileDecoder(MAX_FRAME_LENGTH, LENGTH_FILE_OFFSET, LENGTH_FILE_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP, false))
                                .addLast(new FileUploadHandler(path));
                    }
                });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new FileUploadServer().bind(Integer.valueOf(args[0]), args[1]);
    }
}
