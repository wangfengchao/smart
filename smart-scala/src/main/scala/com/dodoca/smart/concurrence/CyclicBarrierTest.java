package com.dodoca.smart.concurrence;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by  fc.w on 2017/1/17.
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        int N = 4;
        CyclicBarrier barrier  = new CyclicBarrier(N);
        for (int i = 0; i < N; i++) {
            new Writer(barrier).start();
        }
    }


    static class Writer extends Thread {
        private CyclicBarrier barrier;

        public Writer(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
                Thread.sleep(6000);
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }
}
