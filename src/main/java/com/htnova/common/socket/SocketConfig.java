package com.htnova.common.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.htnova.common.util.SocketUtil;
import com.htnova.common.util.SpringContextUtil;
import com.htnova.security.entity.AuthUser;
import javax.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "socket")
public class SocketConfig {
    private int port;

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
        config.setPingInterval(25000);
        // Ping消息超时时间（毫秒），默认60000，这个时间间隔内没有接收到心跳消息就会发送超时事件
        config.setPingTimeout(60000);
        // 根路径，设置为和spring security设置的cookie的path一致
        config.setContext(StringUtils.trimToEmpty(serverProperties.getServlet().getContextPath()) + "/socket.io");

        config.setExceptionListener(new SocketExceptionListener());

        config.setAuthorizationListener(
            data -> {
                // https://github.com/mrniko/netty-socketio/issues/110
                // 如果采用cookie机制
                String sessionId = SocketUtil.getHttpSessionId(data);
                return SpringContextUtil.getAuthUser(sessionId) != null;
            }
        );
        SocketIOServer socketIOServer = new SocketIOServer(config);
        socketIOServer.addEventInterceptor(new SocketEventInterceptor());
        return socketIOServer;
    }

    /** 客户端建立连接 */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String httpSessionId = SocketUtil.getHttpSessionId(client);
        // 如果 httpSession 过期 client 再断开连接，那缓存的 client 对象将无法释放，所以将 authuser 放在SocketIOClient里
        AuthUser authUser = SpringContextUtil.getAuthUser(httpSessionId);
        client.set(SocketUtil.SOCKET_USER_KEY, authUser);
        SocketUtil.saveClient(authUser.getId(), client);
    }

    /** 客户端断开连接 */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        SocketUtil.deleteClient(client);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}
