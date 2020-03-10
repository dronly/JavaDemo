package yxxy.c_005;


import java.util.concurrent.TimeUnit;

class T implements Runnable {

    private int count=10;

    @Override
    public /*synchronized */ void run() {
        count--;
//        try {
//            TimeUnit.MILLISECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println(Thread.currentThread().getName()+" count = "+count);
    }

    public static void main(String[] args) {
        T t=new T();
        for (int i = 0; i < 5; i++) {
            new Thread(t,"Thread"+i).start();
        }
    }
}
