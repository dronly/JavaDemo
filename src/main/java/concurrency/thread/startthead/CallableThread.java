package concurrency.thread.startthead;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 实现 Callable 接口
 *
 * 1. 创建Callable接口的实现类，并实现call方法
 * 2. 创建Callable实现类的实例，并用FutureTask类来包装该实例
 * 3. 使用FutureTask实例作为target创建Thread实例
 * 4. 调用Thread实例的start方法
 */
public class CallableThread implements Callable<Integer> {

    public static void main(String[] args){
        CallableThread ct = new CallableThread();
        List<FutureTask> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            FutureTask<Integer> task = new FutureTask<>(ct);
            tasks.add(task);
            new Thread(task).start();
        }

        tasks.forEach((task)-> {
            try {
                System.out.println(task.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(1);
        return 1;
    }
}
