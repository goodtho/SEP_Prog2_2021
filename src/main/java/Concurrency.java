import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Concurrency {


    public void startThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("running");
                System.out.println("finished");
            }
        });
        thread.start();
    }

    public void threadPool(){
        // Create the pool
        ExecutorService service = Executors.newFixedThreadPool(2);

        // submit tasks for execution
        for(int i = 0; i < 10; i++)
            service.execute(new Task());
    }
}

class Task implements Runnable{
    public void run(){
        System.out.println("Thread Name: " + Thread.currentThread().getName());
    }
}
