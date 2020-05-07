package com.termos.utility;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public  class TimeUtils {
    public static Timestamp NowTimeStamp() {
        LocalDateTime ldt = LocalDateTime.now();
        return Timestamp.valueOf(ldt);
    }
}