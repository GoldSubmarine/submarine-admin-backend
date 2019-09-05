package org.javahub.submarine.common.constant;

public interface ResultCode {
    int OK = 200;
    int CREATED = 201;
    int MOVED_PERMANENTLY = 301;
    int FOUND = 302;
    int BAD_REQUEST = 400;

    /**
     * 接口未授权
     */
    int UNAUTHORIZED = 4001;

    /**
     * 重新登录
     */
    int TOLOGIN = 50014;

}
