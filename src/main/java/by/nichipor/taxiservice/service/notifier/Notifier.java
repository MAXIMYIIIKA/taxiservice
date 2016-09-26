package by.nichipor.taxiservice.service.notifier;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Max Nichipor on 14.09.2016.
 */

@Service
public class Notifier {
    private static Logger logger = Logger.getLogger(Notifier.class);

    private static volatile CopyOnWriteArrayList<DispatcherNotifier> dispatchers = new CopyOnWriteArrayList<>();
    private Lock lock;

    public Notifier(){
        this.lock = new ReentrantLock();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread1 = new Thread(runnable);
            thread1.setDaemon(true);
            return thread1;
        });
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!dispatchers.isEmpty()) {
                    try {
                        for (DispatcherNotifier dispatcher : dispatchers) {
                            if (!dispatcher.isAlive()) {
                                dispatchers.remove(dispatcher);
                                logger.debug(this.hashCode() + ": dispatcher " + dispatcher.hashCode() + " removed");
                            }
                        }
                    } catch (Exception e){
                        logger.error(e);
                    }
                }
            }
        }, 1, 30, TimeUnit.SECONDS);
    }

    public void registerDispatcher(DispatcherNotifier dispatcher){
        lock.lock();
        try {
            dispatchers.add(dispatcher);
        } finally {
            lock.unlock();
        }
    }

    public void addOrderNotification(){
        lock.lock();
        try {
            addNotification("newOrder");
        } finally {
            lock.unlock();
        }
    }

    public void addStatusNotification(){
        lock.lock();
        try {
            addNotification("newStatus");
        } finally {
            lock.unlock();
        }
    }

    private void addNotification(String notification){
        for (DispatcherNotifier dispatcher : dispatchers) {
            dispatcher.addNotification(notification);
            logger.debug("Notification sent to " + dispatcher.hashCode());
        }
    }

}
