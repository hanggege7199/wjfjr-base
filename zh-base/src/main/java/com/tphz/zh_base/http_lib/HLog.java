package com.tphz.zh_base.http_lib;

import android.util.Log;

public class HLog {
    static Tree tree = new Tree() {
        @Override
        public void log(String s) {
            Log.d("OkHttpClient", s);
        }

        @Override
        public void error(Throwable t) {
            Log.e("OkHttpClient", t.getMessage());
        }
    };

    public static void imp(Tree tree) {
        HLog.tree = tree;
    }

    public static void log(String s) {
        HLog.tree.log(s);
    }

    public static void error(Throwable t) {
        HLog.tree.error(t);
    }


    public interface Tree {
        void log(String s);

        void error(Throwable t);
    }
}
