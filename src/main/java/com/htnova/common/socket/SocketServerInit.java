package com.htnova.common.socket;

import com.corundumstudio.socketio.SocketIOServer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SocketServerInit {
    @Resource
    private SocketIOServer socketIOServer;

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
}
