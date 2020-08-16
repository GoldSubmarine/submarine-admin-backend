package com.htnova.common.util;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.htnova.security.entity.AuthUser;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

public class SocketUtil {
    public static final String SOCKET_USER_KEY = "SOCKET_USER";

    private SocketUtil() {}

    /** 缓存所有的 socketClient，一个用户可能同时多台电脑登录，所以子结构也要为 Map key 为 userId */
    private static Map<Long, List<SocketIOClient>> clients = new ConcurrentHashMap<>();

    /** 存入本地缓存 */
    public static void saveClient(long userId, SocketIOClient socketIOClient) {
        List<SocketIOClient> clientList = clients.get(userId);
        if (Objects.isNull(clientList)) {
            clientList = new ArrayList<>();
        }
        clientList.add(socketIOClient);
        clients.put(userId, clientList);
    }

    /** 获取所有通道信息 */
    public static List<SocketIOClient> getClientList(long userId) {
        return clients.get(userId);
    }

    /** 删除一个用户通道 */
    public static void deleteClient(long userId) {
        clients.remove(userId);
    }

    public static void deleteClient(SocketIOClient client) {
        AuthUser authUser = client.get(SOCKET_USER_KEY);
        clients.get(authUser.getId()).removeIf(item -> item.getSessionId().equals(client.getSessionId()));
    }

    /** 获取spring的session */
    public static String getHttpSessionId(SocketIOClient client) {
        return getHttpSessionId(client.getHandshakeData().getHttpHeaders().get(HttpHeaders.COOKIE));
    }

    public static String getHttpSessionId(HandshakeData handshakeData) {
        return getHttpSessionId(handshakeData.getHttpHeaders().get(HttpHeaders.COOKIE));
    }

    public static boolean isExpired(String httpSessionId) {
        return SpringContextUtil.getAuthUser(httpSessionId) == null;
    }

    /**
     * @see
     *     org.springframework.session.web.http.DefaultCookieSerializer#readCookieValues(HttpServletRequest)
     */
    private static String getHttpSessionId(String cookieStr) {
        final String SESSION_NAME = "SESSION";
        if (StringUtils.isBlank(cookieStr)) return null;
        String[] cookies = cookieStr.split(";");
        for (String cookie : cookies) {
            if (StringUtils.startsWith(cookie, SESSION_NAME)) {
                String sessionId = new String(Base64.getDecoder().decode(cookie.substring(SESSION_NAME.length() + 1)));
                if (StringUtils.isBlank(sessionId)) {
                    continue;
                }
                return sessionId;
            }
        }
        return null;
    }
}
