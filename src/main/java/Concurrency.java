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
}
