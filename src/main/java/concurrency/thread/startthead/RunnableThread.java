package concurrency.thread.startthead;

/**
 *  实现 Runnable 接口 创建线程
 *
 *  1. 创建一个类实现 Runnable 接口，并重写该类的run方法。
 *  2. new 一个类实例，将该实例作为Thread的target创建Thread对象
 *  3. 调用 Thread 的 start 方法
 */
public class RunnableThread {
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {

            int finalI = i;

            // 匿名类实现 Runnable 接口
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            }, "thread_anonymous" + finalI).start();

            // Java 8 lambda 语法
            new Thread(() -> System.out.println(Thread.currentThread().getName()), "threadName_lambda" + finalI).start();

            //
            T t = new T();
            new Thread(t, "threadName_class" + finalI).start();
        }
    }

    static class T implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
