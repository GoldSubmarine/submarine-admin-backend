package com.htnova.system.tool.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class RedisManageController {
    @Resource
    private SocketIOServer socketIOServer;

    @OnEvent("/redis/list")
    public void onEvent(SocketIOClient client, AckRequest ackRequest, String msg) {
        log.info("client {} msg {} AckRequest {}", client, msg, ackRequest);
        client.sendEvent("/redis/list");
        //        throw new ServiceException(ResultStatus.DELETE_SUCCESS);
    }

    public void pushMsg() {
        socketIOServer.getRoomOperations("default_room").sendEvent("/broadcast", "广播消息");
    }
}
