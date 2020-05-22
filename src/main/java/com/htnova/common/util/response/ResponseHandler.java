package com.htnova.common.util.response;

import com.htnova.common.dto.Result;
import com.htnova.common.util.JsonMapper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

@FunctionalInterface
public interface ResponseHandler {
    void handle(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;

    static ResponseHandlerBuilder builder() {
        return new ResponseHandlerBuilder();
    }

    static ResponseHandler create(Result<?> result) {
        return (request, response) -> {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JsonMapper.toJsonString(result));
        };
    }

    static ResponseHandler create(HttpStatus httpStatus, Result<?> result) {
        return (request, response) -> {
            response.setStatus(httpStatus.value());
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JsonMapper.toJsonString(result));
        };
    }
}
