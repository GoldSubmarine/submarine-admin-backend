package com.htnova.common.socket;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


/**
 * netty 和 tomcat属于不同的容器，所以不能共用一个端口，参见：https://github.com/mrniko/netty-socketio/issues/267
 * socket 连接时进行认证有两种方案
 * 1. 在连接的url上带上token参数
 * 2. 通过http中的cookie校验，注意如果采用这中方式，需要注意以下几点
 *      - 前端的连接方式必须为polling，而非websocket，直接websocket连接是不会带上cookie的，必须先用http尝试连接
 *      - 注意spring security设置的cookie的path是否和 socket连接是的path一致
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix="socket")
public class SocketConfig {

    private int port;

    private int pingInterval;

    private int pingTimeout;

    private String authorizationHeader;

    @Resource
    private ServerProperties serverProperties;

    @Bean
    public SocketIOServer socketIOServer() {
        /*
         * 创建Socket，并设置监听端口
         */
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();

        config.getSocketConfig().setReuseAddress(true);
        // 设置主机名，默认是0.0.0.0
         config.setHostname("0.0.0.0");
        // 设置监听端口，不能和tomcat使用同一个端口
        config.setPort(port);
        // 协议升级超时时间（毫秒），默认10000。HTTP握手升级为ws协议超时时间
        config.setUpgradeTimeout(10000);
        // Ping消息间隔（毫秒），默认25000。客户端向服务器发送一条心跳消息间隔
        config.setPingInterval(pingInterval);
        // Ping消息超时时间（毫秒），默认60000，这个时间间隔内没有接收到心跳消息就会发送超时事件
        config.setPingTimeout(pingTimeout);
        // 根路径，设置为和spring security设置的cookie的path一致
        config.setContext(serverProperties.getServlet().getContextPath());

        config.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData data) {
                // https://github.com/mrniko/netty-socketio/issues/110
                // 如果采用cookie机制
                String cookies = data.getHttpHeaders().get("cookie");
                // 如果采用url参数机制
                String authToken = data.getSingleUrlParam(authorizationHeader);
                log.info("连接参数：{} = {}", authorizationHeader, authToken);
                return true;
            }
        });
        return new SocketIOServer(config);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}