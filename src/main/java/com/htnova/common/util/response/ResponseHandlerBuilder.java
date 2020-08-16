package com.htnova.common.util.response;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

public class ResponseHandlerBuilder {
    private final HashMap<RequestMatcher, ResponseHandler> handlers = new LinkedHashMap<>();

    private ResponseHandler defaultHandler;

    public ResponseHandlerBuilder handlerFor(ResponseHandler handler, RequestMatcher preferredMatcher) {
        Assert.notNull(handler, "handler cannot be null");
        Assert.notNull(preferredMatcher, "preferredMatcher cannot be null");
        this.handlers.put(preferredMatcher, handler);
        return this;
    }

    public ResponseHandlerBuilder defaultHandler(ResponseHandler handler) {
        Assert.notNull(handler, "handler cannot be null");
        this.defaultHandler = handler;
        return this;
    }

    public ResponseHandler build() {
        if (handlers.isEmpty()) {
            return defaultHandler;
        } else {
            return new DelegatingResponseHandler(handlers, defaultHandler);
        }
    }
}
