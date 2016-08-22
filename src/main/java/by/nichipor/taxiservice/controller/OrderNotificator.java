package by.nichipor.taxiservice.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max Nichipor on 21.08.2016.
 */

@Service
public class OrderNotificator {
    private static Logger logger = Logger.getLogger(OrderNotificator.class);

    private List<DeferredResultService> services;

    public OrderNotificator(){
        services = new ArrayList<>();
        logger.debug(this.hashCode() + ": OrderNotificator instance created");
    }

    public synchronized void addNewOrderNotification(){
        for (DeferredResultService service: services){
            service.addNewNotification();
            logger.debug(this.hashCode() + ": addNewNotification() called on " + service.hashCode());
            services = new ArrayList<>();
        }
    }

    public synchronized void registerService(DeferredResultService service){
            this.services.add(service);
            String str = " service registered; Services: [";
            for (DeferredResultService service1 : services) {
                str += service1.hashCode() + ";";
            }
            str += "]";
            logger.debug(this.hashCode() + ": " + service.hashCode() + str);

    }

    public synchronized void unregisterService(DeferredResultService service){
        this.services.remove(service);
        String str = " service unregistered; Services: [";
        for (DeferredResultService service1 : services) {
            str += service1.hashCode() + ";";
        }
        str += "]";
        logger.debug(this.hashCode() + ": " + service.hashCode() + str);

    }

}
