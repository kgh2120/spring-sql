package com.kk.jpaqueryperformance;


import org.slf4j.Logger;

public class PerformanceLogger {

    public static void logPerf(Logger logger, String queryName, long start, long end){
        logger.info("[{}] cost {}ms", queryName, end-start);
    }
}
