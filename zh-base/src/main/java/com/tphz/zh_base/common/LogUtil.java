package com.tphz.zh_base.common;

import android.util.Log;

public class LogUtil {

    public static Tree defaultTree = new Tree() {
        @Override
        public void v(String tag, String info) {
            Log.v(tag, info);
        }

        @Override
        public void d(String tag, String info) {
            Log.d(tag, info);
        }

        @Override
        public void i(String tag, String info) {
            Log.i(tag, info);
        }

        @Override
        public void w(String tag, String info) {
            Log.w(tag, info);
        }

        @Override
        public void e(String tag, String info) {
            Log.e(tag, info);
        }

        @Override
        public void e(String tag, String info, Throwable tr) {
            Log.e(tag, info, tr);
        }
    };

    public static Tree tree = defaultTree;

    public interface Tree {
        void v(String tag, String info);

        void d(String tag, String info);

        void i(String tag, String info);

        void w(String tag, String info);

        void e(String tag, String info);

        void e(String tag, String info, Throwable tr);

    }

    public static void imp(Tree t) {
        tree = t;
    }

    /**
     * INFO级别日志
     *
     * @param tag  TAG
     * @param info 消息
     */
    public static void v(String tag, String info) {
        tree.v(tag, info);
    }

    /**
     * DEBUG级别日志
     *
     * @param tag  TAG
     * @param info 消息
     */
    public static void d(String tag, String info) {
        tree.d(tag, info);
    }

    /**
     * INFO级别日志
     *
     * @param tag  TAG
     * @param info 消息
     */
    public static void i(String tag, String info) {
        tree.i(tag, info);
    }

    /**
     * WARN级别日志
     *
     * @param tag  TAG
     * @param info 消息
     */
    public static void w(String tag, String info) {
        tree.w(tag, info);
    }

    /**
     * ERROR级别日志
     *
     * @param tag  TAG
     * @param info 消息
     */
    public static void e(String tag, String info) {
        tree.e(tag, info);
    }

    /**
     * ERROR级别日志
     *
     * @param tag  TAG
     * @param info 消息
     */
    public static void e(String tag, String info, Throwable tr) {
        tree.e(tag, info, tr);
    }

}