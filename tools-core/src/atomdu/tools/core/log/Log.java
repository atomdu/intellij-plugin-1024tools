package atomdu.tools.core.log;

import atomdu.tools.core.config.Config;
import org.apache.log4j.Logger;

public class Log {
    public static final boolean DEBUG = Config.DEBUG;

    private Class tag;

    private Log(Class tag) {
        this.tag = tag;
    }

    public static Log get(Class clazz) {
        return new Log(clazz);
    }

    public void i(Object msg) {
        if (!Config.DEBUG) return;
        System.out.println(msg);
    }

    public void d(Object msg) {
        if (!Config.DEBUG) return;
        System.out.println(msg);
    }

    public void e(Object msg) {
        if (!Config.DEBUG) return;
        System.err.println(msg);
    }

    public static void i(Class clazz, String msg) {
        if (!Config.DEBUG) return;
        System.out.println(clazz.getName() + ":" + msg);
    }

    public static void d(Class clazz, String msg) {
        if (!Config.DEBUG) return;
        System.out.println(clazz.getName() + ":" + msg);
    }
    public static void d(String name, String msg) {
        if (!Config.DEBUG) return;
        System.out.println(name + ":" + msg);
    }

    /**
     * debug模式，设置线程等待后执行
     *
     * @param millis 秒
     */
    public static void debugThreadSleep(Class clazz, String method, long millis) {
        if (Log.DEBUG) {//debug模式
            try {
                Log.d(clazz, method + "方法线程等待：" + millis + "毫秒,所在线程名称：" + Thread.currentThread().getName() + ";开始...");
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                Log.d(clazz, method + "方法线程等待：" + millis + "毫秒,所在线程名称：" + Thread.currentThread().getName() + ";异常");
                e.printStackTrace();
            }
            Log.d(clazz, method + "方法线程等待：" + millis + "毫秒,所在线程名称：" + Thread.currentThread().getName() + ";结束");
        }
    }

    public static void e(Class clazz, String msg) {
        if (!Config.DEBUG) return;
        System.err.println(clazz.getName() + ":" + msg);
    }

    public static class Logr {
        private static boolean OUT = true;
        private Logger logger = null;

        private Logr(Class tag) {
            logger = Logger.getLogger(tag);
        }

        public static atomdu.tools.core.log.Log get(Class clazz) {
            return new atomdu.tools.core.log.Log(clazz);
        }

        public void i(Object msg) {
            if (!OUT) return;
            logger.info(msg);
        }

        public void d(Object msg) {
            if (!OUT) return;
            logger.debug(msg);
        }

        public void e(Object msg) {
            if (!OUT) return;
            logger.error(msg);
        }

        public static void i(Class clazz, String msg) {
            if (!OUT) return;
            Logger.getLogger(clazz).info(msg);
        }

        public static void d(Class clazz, String msg) {
            if (!OUT) return;
            Logger.getLogger(clazz).debug(msg);
        }

        public static void e(Class clazz, String msg) {
            if (!OUT) return;
            Logger.getLogger(clazz).error(msg);
        }
    }
}
