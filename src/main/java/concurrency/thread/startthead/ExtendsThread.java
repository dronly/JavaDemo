package concurrency.thread.startthead;

/**
 * 继承Thread类创建线程类
 * <p>
 * 1. 继承Thread类，并重写该类的run方法
 * 2. new一个实例，通过start方法启动新线程
 */
public class ExtendsThread extends Thread {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new ExtendsThread().start();
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
