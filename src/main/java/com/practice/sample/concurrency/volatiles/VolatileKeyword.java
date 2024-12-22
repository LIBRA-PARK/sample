package com.practice.sample.concurrency.volatiles;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class VolatileKeyword implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(VolatileKeyword.class);

    @Setter
    private volatile boolean isRunning;

    public VolatileKeyword() {
        this.isRunning = true;
    }

    @Override
    public void run() {
        log.info("THREAD 실행");
        while(this.isRunning) {

        }
        log.info("THREAD 종료");
    }
}
