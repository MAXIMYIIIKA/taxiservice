package by.nichipor.taxiservice.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Max Nichipor on 21.08.2016.
 */

@Component
public class DeferredResultService implements Runnable {
    private static Logger logger = Logger.getLogger(DeferredResultService.class);

    private volatile DeferredResult<String> result;
//    private final BlockingQueue<DeferredResult<String>> resultQueue = new LinkedBlockingQueue<>();
    private Thread thread;
    private volatile boolean start = true;
    private volatile LinkedBlockingQueue<String> queue;

    public DeferredResultService(){
        queue = new LinkedBlockingQueue<>();
        result = new DeferredResult<>();
//        notificator.registerService(this);
//        startThread();
        logger.debug(this.hashCode() + ": DeferredResultService instance created");
    }

    private void startThread() {
        synchronized (this) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void addNewNotification(){
        try {
            if (queue.size() == 0) {
                this.queue.put("Yes");
                logger.debug(this.hashCode() + ": New notification added; Queue size is " + queue.size());
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    @Override
    public void run() {
        logger.debug(this.hashCode() + ": Thread " + thread + " started");
        boolean flag = true;
        while (flag) {
            try {
//                DeferredResult<String> result = resultQueue.take();
                logger.debug(this.hashCode() + ": DeferredResult is: " + result.hashCode());
                result.setResult(queue.take());
                logger.debug(this.hashCode() + ": Notification took; Queue size is " + queue.size());
                logger.debug(this.hashCode() + ": Result " + result.hashCode() + " message is:" + result.getResult());
                if (result.getResult() != null) {
                    flag = false;
                }
            } catch (InterruptedException e) {
                System.err.println("Cannot get latest update. " + e.getMessage());
            }
        }
        logger.debug(this.hashCode() + ": The end of the thread " + thread);
    }

    public synchronized void getUpdate(DeferredResult<String> result) {
        this.result = result;
        startThread();
//        resultQueue.add(result);
        logger.debug(this.hashCode() + ": Result " + result.hashCode() + " is added");
    }
}
