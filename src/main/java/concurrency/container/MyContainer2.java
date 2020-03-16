package concurrency.container;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 只能保证可以通知到另一个线程，但是不能保证，通信后，另一个线程立马执行
 *
 */
public class MyContainer2 {
    public volatile List list = new ArrayList<Object>();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        MyContainer2 c = new MyContainer2();
        CountDownLatch lock = new CountDownLatch(1);

        Thread t1 = new Thread(() -> {
            System.out.println("thread 01 start");
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add:" + i);
                if (c.size() == 5) {
                    lock.countDown();
                }
            }
            System.out.println("thread 01 end");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("thread 02 start");
            if (c.size() != 5) {
                try {
                    lock.await();
                    System.out.println("thread 02 wake up");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("thread 02 end");
        });
        t2.start();
        t1.start();
    }
}
