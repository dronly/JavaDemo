package concurrency.keywords;

/**
 * synchronized的作用主要有三个：
 *
 * 1. 确保线程互斥的访问代码
 * 2. 保证共享变量的修改能够及时可见（可见性）
 * 3. 可以阻止JVM的指令重排序
 * 在Java中所有对象都可以作为锁，这是synchronized实现同步的基础。
 *
 * synchronized 三种使用方式， 本质都是对某个对象加锁。
 * 1. 同步代码块
 * 2. 同步实例方法
 * 3. 同步静态方法
 *
 * 1. synchronized 同步代码块，锁的对象可以是 this 也可以是其他的实例对象。
 * 2. synchronized(o) 是可重入锁，即 一个线程获取到 o 对象的锁，再次获取 o 对象的锁时，也可以获取到。
 * 3. synchronized 在程序出现异常时，锁会自动释放。因此需要对数据进行回滚
 *
 * 原理：
 * https://www.cnblogs.com/paddix/p/5367116.html
 *
 *  #### 同步代码块
 * 每个对象有一个监视器锁（monitor）。当monitor被占用时就会处于锁定状态，线程执行 monitorenter 指令时尝试获取monitor的所有权，过程如下：
 *
 * monitorenter
 * 1. 如果monitor的进入数为0，则该线程进入monitor，然后将进入数设置为1，该线程即为monitor的所有者。
 * 2. 如果线程已经占有该monitor，只是重新进入，则进入monitor的进入数加1.
 * 3. 如果其他线程已经占用了monitor，则该线程进入阻塞状态，直到monitor的进入数为0，再重新尝试获取monitor的所有权。
 *
 * 执行monitorexit的线程必须是objectref所对应的monitor的所有者。
 * 指令执行时，monitor的进入数减1，如果减1后进入数为0，那线程退出monitor，不再是这个monitor的所有者。
 * 其他被这个monitor阻塞的线程可以尝试去获取这个 monitor 的所有权。
 *
 * 通过这两段描述，我们应该能很清楚的看出Synchronized的实现原理，Synchronized的语义底层是通过一个monitor的对象来完成，
 * 其实wait/notify等方法也依赖于monitor对象，这就是为什么只有在同步的块或者方法中才能调用wait/notify等方法，否则会抛出
 * java.lang.IllegalMonitorStateException的异常的原因。
 *
 * #### 同步方法
 * 方法的同步并没有通过指令monitorenter和monitorexit来完成（理论上其实也可以通过这两条指令来实现），不过相对于普通方法，其常量池中多
 * 了 ACC_SYNCHRONIZED 标示符。JVM就是根据该标示符来实现方法的同步的：当方法调用时，调用指令将会检查方法的 ACC_SYNCHRONIZED 访问标
 * 志是否被设置，如果设置了，执行线程将先获取monitor，获取成功之后才能执行方法体，方法执行完后再释放monitor。在方法执行期间，
 * 其他任何线程都无法再获得同一个monitor对象。 其实本质上没有区别，只是方法的同步是一种隐式的方式来实现，无需通过字节码来完成。
 */
public class SynchronizedKeywords {
    public static void main(String[] args) {
        new Thread(() -> new T2().m1()).start();
    }

    static class T {
        private static int static_i;
        int i = 0;
        Object o = new Object();

        /**
         * 同步代码块，锁的对象可以是其他实例对象 o， 也可以是 this 。当时 this 时，等同于 实例方法。
         */
        public void sum1() {
            synchronized (o) {
                i++;
            }
        }

        /**
         * 实例方法， 锁的对象是 this  即 synchronized (this)
         */
        public synchronized void sum2() {
            i++;
        }

        /**
         * 静态方法， 锁的对象时类对象 即 synchronized (T.class)
         */
        public synchronized static void sum3() {
            static_i++;
        }
    }
}

class T2 {
    synchronized void m1() {
        System.out.println("m1");
        m2();
    }

    synchronized void m2() {
        System.out.println("m2");

    }
}
