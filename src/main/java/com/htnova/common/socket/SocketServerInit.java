package com.htnova.common.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Slf4j
@Service
public class SocketServerInit {

    @Resource
    private SocketIOServer socketIOServer;

    @Resource
    private SocketConfig socketConfig;

    @PostConstruct
    public void start() {
        log.info("SocketIO Server starting...");
        socketIOServer.start();
    }

    @PreDestroy
    public void stop() {
        log.info("SocketIO Server stopping...");
        socketIOServer.stop();
        log.info("SocketIO Server stopped.");
    }

    /**
     * 客户端建立连接
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String uuid = client.getSessionId().toString();
        String ip = client.getRemoteAddress().toString();
        String authToken = client.getHandshakeData().getSingleUrlParam(socketConfig.getAuthorizationHeader());
        //加入仓库的房间
        client.joinRoom("default_room");
        log.info("设备建立连接 IP: {} UUID: {} token: {}", ip, uuid, authToken);
    }

    /**
     * 客户端断开连接
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String uuid = client.getSessionId().toString();
        String ip = client.getRemoteAddress().toString();
        log.info("设备断开连接 IP: {} UUID: {}", ip, uuid);
    }

    // 客户端广播消息接收，如果实体与前端穿的不对应，也不会走当前方法
//    @OnEvent(value = "broadcast")
//    public void onBroadcast(SocketIOClient client, AckRequest ackRequest, Object data) {
//        log.info("接收到广播消息");
//        String room = client.getHandshakeData().getSingleUrlParam("appid");
//        server.getRoomOperations(room).sendEvent("broadcast", "广播啦啦啦啦");
//    }
}
