package by.nichipor.taxiservice.service.notifier;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Max Nichipor on 14.09.2016.
 */

public class DispatcherNotifier implements Runnable {
    private static Logger logger = Logger.getLogger(DispatcherNotifier.class);

    private LinkedBlockingQueue<String> notifications;
    private DeferredResult<String> result;
    private Lock lock;
    private Thread notifier;
    private final long creationTime;

    public DispatcherNotifier(){
        notifications = new LinkedBlockingQueue<>();
        result = new DeferredResult<>();
        lock = new ReentrantLock();
        notifier = new Thread(this, "notifier");
        creationTime = System.currentTimeMillis() + 28000;
    }

    public void addNotification(String notification) {
        lock.lock();
        try {
            this.notifications.put(notification);
        } catch (InterruptedException e) {
            logger.error(e);
        } finally {
            lock.unlock();
        }
    }

    public void getUpdate(DeferredResult<String> result){
        this.result = result;
        notifier.start();
    }

    public boolean isAlive(){
        logger.debug(this.hashCode() + ": CHECKING is Alive");
        long current = System.currentTimeMillis();
        return current < creationTime;
    }

    @Override
    public void run() {
        try {
            logger.debug("Notifier thread " + this.hashCode() + " started");
            result.setResult(notifications.take());
        } catch (InterruptedException e) {
            logger.error(e);
        } finally {
            logger.debug("Result is " + result.getResult());
        }
    }
}
