package by.nichipor.taxiservice.service.notifier;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Max Nichipor on 21.08.2016.
 */

@Service
public class Notifier implements Runnable{
    private static Logger logger = Logger.getLogger(Notifier.class);

    private volatile CopyOnWriteArrayList<DeferredResultService> dispatchers;
    private volatile Map<String, DeferredResultService> users;
    private Thread thread;
    private boolean changes;
    private boolean start;
    private boolean newOrder;
    private boolean newStatus;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        }
    });


    public Notifier(){
        dispatchers = new CopyOnWriteArrayList<>();
        startThread();
        logger.debug(this.hashCode() + ": Notifier instance created");
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                        logger.debug(this.hashCode() + ": CHECKING is Alive");
                        for (DeferredResultService service : dispatchers) {
                            if (!service.isAlive()) {
                                dispatchers.remove(service);
                                logger.debug(this.hashCode() + ": dispatcher " + service.hashCode() + " removed");
                            }
                        }
                } catch (Exception e){
                    logger.error(e);
                }
            }
        }, 1, 30, TimeUnit.SECONDS);
    }

    private void startThread(){
        synchronized (this){
            thread = new Thread(this, "Notifier");
            logger.debug(this.hashCode() + ": Thread " + thread.hashCode() + " created");
            start = true;
            logger.debug(this.hashCode() + ": start = " + start);
            thread.start();
        }
    }

    public synchronized void addNewOrderNotification(){
        changes = true;
        newOrder = true;
        notify();
    }

    public synchronized void addNewStatusNotification(){
        changes = true;
        newStatus = true;
        notify();
    }

    public synchronized void registerDispatcher(DeferredResultService dispatcher){
            this.dispatchers.add(dispatcher);
            String str = " dispatcher registered; Dispatchers: [";
            for (DeferredResultService dispatcher1 : dispatchers) {
                str += dispatcher1.hashCode() + " isAlive = "+ dispatcher1.isAlive() + ";";
            }
            str += "]";
            logger.debug(this.hashCode() + ": " + dispatcher.hashCode() + str);

    }

    @Override
    public void run() {
        while (start){
            logger.debug(this.hashCode() + ": Thread Notif" + thread + " started");
            if (changes){
                for (DeferredResultService service: dispatchers){
                    if (newOrder) {
                        service.addNewNotification();
                    }
                    if (newStatus){
                        service.addStatusNotification();
                    }
                    logger.debug(this.hashCode() + ": add*****Notification() called on " + service.hashCode());
                    dispatchers = new CopyOnWriteArrayList<>();
                    newOrder = false;
                }
            }
            try {
                synchronized (this) {
                    logger.debug(this.hashCode() + ": Thread Notif " + thread + " waiting...");
                    wait();
                }
            } catch (InterruptedException e){
                logger.error(e);
            }
        }
        logger.debug(this.hashCode() + ": Thread " + this.hashCode() + " end.");
    }
}
