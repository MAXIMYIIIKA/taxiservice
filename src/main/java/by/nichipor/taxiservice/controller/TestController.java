package by.nichipor.taxiservice.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Max Nichipor on 21.08.2016.
 */

@Controller
@RequestMapping("/test")
public class TestController {
    private static Logger logger = Logger.getLogger(TestController.class);

    @Autowired
    private volatile TestClass testClass;

    @Autowired
    private volatile OrderNotificator notificator;

    private DeferredResultService resultService;

    private LinkedBlockingQueue<String> queue;

    @RequestMapping(method = RequestMethod.GET)
    public String test() {
        return "test";
    }

    @RequestMapping(value = "ajaxAddString")
    @ResponseBody
    public void addTest() throws InterruptedException{
        testClass.addString(Double.toString(Math.random()));
        logger.debug("String added");
        notificator.addNewOrderNotification();
        logger.debug("addNewOrderNotification() called on " + notificator.hashCode());
    }

    @RequestMapping(value = "ajaxShowStrings")
    @ResponseBody
    public Map<String, ArrayList<String>> ajaxTest() {
        Map<String, ArrayList<String>> records = new HashMap<>();
        records.put("strings", testClass.getTestStrings());
        return records;
    }

    @RequestMapping(value = "ajaxCheck")
    @ResponseBody
    public DeferredResult<String> check(@RequestParam("currentNumberOfOrders") int currNum) throws InterruptedException{
        resultService = new DeferredResultService();
        notificator.registerService(resultService);
        DeferredResult<String> result = new DeferredResult<>();
        resultService.getUpdate(result);
        logger.debug("getUpdate(" + result.hashCode() + ") called on " + resultService.hashCode()
                        + "; Return value = " + result.getResult());
//        notificator.unregisterService(resultService);
        return result;
    }
}
