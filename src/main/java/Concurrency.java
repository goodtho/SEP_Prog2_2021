import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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

    // Implementation of a Callable exception with String return
    public void callableThread() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> future = service.submit(new TaskCallable());

        String result = future.get();
        service.shutdown();
    }

    public void threadPool(){
        // Create the pool
        ExecutorService service = Executors.newFixedThreadPool(2);

        // submit tasks for execution
        for(int i = 0; i < 10; i++)
            service.execute(new TaskRunnable());
    }
}

class TaskRunnable implements Runnable{
    public void run(){
        System.out.println("Thread Name: " + Thread.currentThread().getName());
    }
}

class TaskCallable implements Callable<String> {
    public String call(){
        return "Hallo Welt";
    }
}

/**
 * Multithrading with Conditions and mutual exclusion -> {@link ReentrantLock} and .newCondition
 */
class MultiThreadingWithConditions {

    private boolean bridgeOccupied = false;
    private ReentrantLock mutex = new ReentrantLock();
    private Condition enterLeft = mutex.newCondition();
    private Condition enterRight = mutex.newCondition();

    /**
     * Lock -> await while occupied -> occupy = true -> signal it -> finally unlock
     */
    public void enterLeft() {
        mutex.lock();
        try {
            while(bridgeOccupied) {
                enterLeft.await();
            }
            bridgeOccupied = true;
        } catch (InterruptedException e) {
            System.err.println("Interrupt: " + e.getMessage());
        } finally {
            mutex.unlock();
        }
    }

    /**
     * Lock
     * -> occupy = false (cause leaving now)
     * -> checks if there is someone on the right with mutex.hasWaiters(enterRight)
     *      only use this if someone is a favourite! here Right goes first once left is gone.
     *      else just signal it without checking
     * -> signal it
     * -> finally unlock
     */
    public void leaveLeft() {
        mutex.lock();
        try {
            bridgeOccupied = false;
            if (mutex.hasWaiters(enterRight)) {
                enterRight.signal();
            } else {
                enterLeft.signal();
            }
        } finally {
            mutex.unlock();
        }
    }

    public void enterRight() {
        mutex.lock();
        try {
            while(bridgeOccupied) {
                enterRight.await();
            }
            bridgeOccupied = true;
        } catch (InterruptedException e) {
            System.err.println("Interrupt: " + e.getMessage());
        } finally {
            mutex.unlock();
        }
    }

    public void leaveRight() {
        mutex.lock();
        try {
            bridgeOccupied = false;
            if (mutex.hasWaiters(enterLeft)) {
                enterLeft.signal();
            } else {
                enterRight.signal();
            }
        } finally {
            mutex.unlock();
        }
    }

    /**
     * Synchronized
     */
    public class Acount {
        private int balance = 0;

        /**
         * The whole method is executed mutually excluded
         */
        public synchronized void transferAmount(int amount) {
            this.balance += amount;
        }

        /**
         * Code in the marked block {} is executed mutually excluded
         */
        public void transferAmount2(int amount) {
            synchronized (this) {
                this.balance += amount;
            }
        }
    }

    /**
     * Consumer & Producer (Synchronized)
     */
    public class SyncMonitor {
        private boolean condition = false;

        //called by consumer
        public synchronized void waitForCondition() {
            while(!condition) {
                try{
                    wait();
                }catch (InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }
            condition = false;
            //wake up producer
            notifyAll();
        }

        public synchronized void setCondition() {
            while(condition) {
                try {
                    wait();
                }catch (InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }
            condition = true;
            //wake up consumer
            notifyAll();
        }
    }

}
