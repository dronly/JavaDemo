package concurrency.container;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 曾经的面试题：
 * 实现一个容器，提供两个方法：add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * 给lists添加volatile之后，t2能够接到通知，但是t2线程的死循环很浪费cpu，如果不用死循环怎么做呢？
 * <p>
 * 分析下面这个程序，能完成这个功能吗？
 */
public class MyContainer {
    volatile List list = new ArrayList<Object>();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {

        MyContainer c = new MyContainer();
        final Object lock = new Object();
        Thread t1 = new Thread(() -> {
            System.out.println("t1 start");

            synchronized (lock){
                for (int i = 1; i < 11; i++) {
                    c.add(new Object());
                    System.out.println("add " + i);
                    if (c.size() == 5){
                        System.out.println("c.size :" + c.size());
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                System.out.println("t1结束");

            }

        });

        Thread t2 = new Thread(() -> {
            System.out.println("t2 start");
            synchronized (lock){
                if (c.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2结束");
                lock.notify();
            }
        });

        t1.start();
        t2.start();

    }
}
