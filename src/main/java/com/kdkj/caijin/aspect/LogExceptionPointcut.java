package com.kdkj.caijin.aspect;

import org.springframework.stereotype.Component;

@Component
public class LogExceptionPointcut {
    public void logException(String log) {
        System.out.println("获取到信息");
    }
}
