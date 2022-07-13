package xyz.panyi.novel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogUtil {
    public static final String TAG = "logger";
    private static Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static Logger buildLogger(final String TAG){
        return LoggerFactory.getLogger(TAG);
    }

    public static void log(final String msg){
        i(msg);
    }

    public static void i(final String msg){
        logger.info(msg);
    }

    public static void e(final String msg){
        logger.error(msg);
    }
}
