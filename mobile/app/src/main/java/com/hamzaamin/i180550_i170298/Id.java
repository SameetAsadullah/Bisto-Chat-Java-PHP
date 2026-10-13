package com.hamzaamin.i180550_i170298;

import android.app.Application;

public class Id extends Application {
    private static String id;
    private static String path;
    private static String ip;
    private static String name;
    private static String dp;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Id.name = name;
    }

    public static String getDp() {
        return dp;
    }

    public static void setDp(String dp) {
        Id.dp = dp;
    }

    public static String getId() {
        return id;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        Id.ip = ip;
    }

    public static void setId(String id) {
        Id.id = id;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        Id.path = path;
    }
}
