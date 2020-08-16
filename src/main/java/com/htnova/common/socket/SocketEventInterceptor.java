package com.htnova.common.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.listener.EventInterceptor;
import com.corundumstudio.socketio.transport.NamespaceClient;
import com.htnova.common.util.SocketUtil;
import com.htnova.common.util.SpringContextUtil;
import com.htnova.common.util.UserUtil;
import com.htnova.security.entity.AuthUser;
import java.util.List;

public class SocketEventInterceptor implements EventInterceptor {

    @Override
    public void onEvent(NamespaceClient client, String eventName, List<Object> args, AckRequest ackRequest) {
        // 清一下上次可能遗留的 threadLocal
        UserUtil.clearAuthUser();
        String httpSessionId = SocketUtil.getHttpSessionId(client.getHandshakeData());
        if (SocketUtil.isExpired(httpSessionId)) {
            client.disconnect();
            return;
        }
        AuthUser authUser = SpringContextUtil.getAuthUser(httpSessionId);
        UserUtil.setAuthUser(authUser);
    }
}
