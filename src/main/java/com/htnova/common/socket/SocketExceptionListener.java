package com.htnova.common.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListener;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketExceptionListener implements ExceptionListener {

    /**
     * 前端事件响应中的异常
     *
     * @param e 统一为 ServiceException
     * @param args 前端传递过来的参数
     * @param client SocketIOClient
     */
    @Override
    public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
        log.error("{} {} {}", e, args, client);
    }

    @Override
    public void onDisconnectException(Exception e, SocketIOClient client) {}

    @Override
    public void onConnectException(Exception e, SocketIOClient client) {}

    @Override
    public void onPingException(Exception e, SocketIOClient client) {}

    @Override
    public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        return false;
    }
}
