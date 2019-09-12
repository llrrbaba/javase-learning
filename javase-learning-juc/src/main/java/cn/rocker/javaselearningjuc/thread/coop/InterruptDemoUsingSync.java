package cn.rocker.javaselearningjuc.thread.coop;

import org.junit.Test;

/**
 *
 *
 * @author chengzc
 * @date 2019-09-12 10:47
 * @since
 */
public class InterruptDemoUsingSync {

    Object object = new Object();
    int flag = 0;

    @Test
    public void test(){
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread2.start();
        if (flag == 1){
            thread2.interrupt();
            System.out.println("thread2被中断");
        }
        if (flag == 2){
            thread1.interrupt();
            System.out.println("thread1被中断");
        }
        /** 这里的输出，可以看到，thread1是RUNNABLE状态，并且还在运行中
         *  但是thread2是BLOCKED状态，可见对于synchronized锁，一个处于等待状态的线程会一直等下去，无法中断 */
        System.out.println(thread1.getState());
        System.out.println(thread2.getState());
    }

    class Thread1 extends Thread{
        @Override
        public void run() {
            synchronized (object){
                flag = 1;
                /** 这里用这个死循环的意图是保证线程或者，要不然执行完就成了TERMINATED状态了 */
                while (true){}
            }
        }
    }

    class Thread2 extends Thread{
        @Override
        public void run() {
            synchronized (object){
                flag = 2;
                /** 这里用这个死循环的意图是保证线程或者，要不然执行完就成了TERMINATED状态了 */
                while (true){}
            }
        }
    }

}
