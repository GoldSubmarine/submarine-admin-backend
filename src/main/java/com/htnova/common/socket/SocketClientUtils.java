package com.htnova.common.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.htnova.common.dto.Result;
import com.htnova.common.exception.ExceptionTranslate;
import java.util.Objects;
import java.util.concurrent.Callable;

public class SocketClientUtils {

    private SocketClientUtils() {}

    public static void run(SocketIOClient client, String errorPath, Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            if (Objects.nonNull(client)) {
                Result<?> translate = ExceptionTranslate.translate(e);
                client.sendEvent(errorPath, translate);
            }
            throw e;
        }
    }

    public static <T> T call(SocketIOClient client, String errorPath, Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            if (Objects.nonNull(client)) {
                Result<?> translate = ExceptionTranslate.translate(e);
                client.sendEvent(errorPath, translate);
            }
            throw new SocketIORuntimeException(e);
        }
    }
}
