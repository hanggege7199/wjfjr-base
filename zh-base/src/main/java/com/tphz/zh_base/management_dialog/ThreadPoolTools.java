package com.tphz.zh_base.management_dialog;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPoolTools {
    private static ThreadPoolTools instance;

    /** 线程池维护线程的最少数量 */
    private int minPoolSize = 10;

    /** 线程池维护线程的最大数量 */
    private int maxPoolSize = 500;

    /** 线程池维护线程所允许的空闲时间 */
    private int idleSeconds = 1800;

    /** 线程池所使用的缓冲队列 */
    private int queueBlockSize = 100;

    private ThreadPoolExecutor executor;

    private ThreadPoolTools() {
        this.executor = new ThreadPoolExecutor(minPoolSize, maxPoolSize, idleSeconds,
                TimeUnit.SECONDS, /* 时间单位,秒 */
                new ArrayBlockingQueue<Runnable>(queueBlockSize),
                new ThreadPoolExecutor.CallerRunsPolicy()); /* 重试添加当前加入失败的任务 */
    }

    public static ThreadPoolTools getInstance(){
        if (instance==null){
            synchronized (ThreadPoolTools.class){
                if (instance==null){
                    instance=new ThreadPoolTools();
                }
            }
        }
        return instance;
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }

    public void stopThreadPool(){
        if (executor!=null){
            executor.shutdown();
        }
        instance=null;
    }

    public <T> Future<T> submit(Callable<T> task){
        return executor.submit(task);
    }
}