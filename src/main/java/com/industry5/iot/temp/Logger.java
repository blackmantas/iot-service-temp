package com.industry5.iot.temp;

public class Logger {

    public static enum Level {
        DEBUG, INFO, WARN, ERROR, FATAL, OFF
    }

    private static Level level = Level.WARN;
    private final String group;

    public Logger(String group) {
        this.group = group == null ? "" : group;
    }

    public Logger(Class klass) {
        this.group = klass == null ? "" : klass.getSimpleName();
    }

    public static Level getLevel() {
        return level;
    }

    public static void setLevel(Level level) {
        Logger.level = level;
    }

    public void log(Level level, Object message) {
        if (level == null) return;
        if (level.ordinal() >= Logger.level.ordinal()) {
            String threadName = Thread.currentThread().getName();
            System.out.printf("[%-5s] [%s] [%s] %s %n", level, threadName, group, message);
        }
    }

    public void debug(Object message) {
        log(Level.DEBUG, message);
    }

    public void info(Object message) {
        log(Level.INFO, message);
    }

    public void warn(Object message) {
        log(Level.WARN, message);
    }

    public void error(Object message) {
        log(Level.ERROR, message);
    }

    public void fatal(Object message) {
        log(Level.FATAL, message);
    }
}
