package com.htnova.common.constant;

import java.time.ZoneId;

public interface GlobalConst {
    String PACKAGE = "com.htnova";

    // 东八区
    String TIME_ZONE_ID = "+8";
    ZoneId ZONE_ID = ZoneId.of(TIME_ZONE_ID);

    /** socket默认错误路径 */
    String SOCKET_ERROR_PATH = "/socket/error";

    int PAGE_NUM = 1;

    int PAGE_SIZE = 10;

    /** 默认头像 */
    String DEFAULT_AVATAR = "http://img.molaka.cn/submarine.jpg";

    /** 未删除标志 */
    Integer UNDEL_FLAG = 0;

    /** 删除标志 */
    Integer DEL_FLAG = 1;
}
