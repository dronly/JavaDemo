package concurrency.keywords;
/**
 *  java.util.concurrent.atomic 下的类都是支持并发访问的原子操作
 *  AtomXXX类本身方法都是原子性的，但不能保证多个方法连续调用是原子性的
 */

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicClass {
    static class T {
        AtomicInteger count = new AtomicInteger(0);

        public void add() {
            for (int i = 0; i < 10000; i++){
                count.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();
        Thread s1 = new Thread(t::add);
        Thread s2 = new Thread(t::add);
        s1.start();
        s2.start();
        try {
            s1.join();
            s2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t.count);
    }
}
