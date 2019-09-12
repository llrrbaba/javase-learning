package cn.rocker.javaselearningjuc.thread.coop;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chengzc
 * @date 2019-09-12 10:47
 * @since
 */
public class InterruptDemoUsingLock2 {

    Object object = new Object();
    int flag = 0;
    Lock lock = new ReentrantLock();

    @Test
    public void test() {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread2.start();
        if (flag == 1) {
            thread2.interrupt();
            System.out.println("thread2被中断");
        }
        if (flag == 2) {
            thread1.interrupt();
            System.out.println("thread1被中断");
        }
        /** 这里的输出，可以看到，thread1是RUNNABLE状态，并且还在运行中
         *  但是thread2是WAITING状态，可见{@Link Lock#lock}是也无法中断一个处于等待锁的线程 */
        System.out.println(thread1.getState());
        System.out.println(thread2.getState());
    }

    class Thread1 extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                flag = 1;
                /** 这里用这个死循环的意图是保证线程或者，要不然执行完就成了TERMINATED状态了 */
                while (true) {}
            } finally {
                lock.unlock();
            }
        }
    }

    class Thread2 extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                flag = 2;
                /** 这里用这个死循环的意图是保证线程或者，要不然执行完就成了TERMINATED状态了 */
                while (true) {}
            } finally {
                lock.unlock();
            }
        }
    }

}
