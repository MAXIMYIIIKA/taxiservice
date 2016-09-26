package by.nichipor.taxiservice.service.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * Created by Max Nichipor on 08.08.2016.
 */

@Controller
@RequestMapping({"", "/", "/main"})
public class MainPageController {
    private static final String MAIN_PAGE = "main";

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String showMainPage(Locale locale, Model ui){
        ui.addAttribute("msg", messageSource.getMessage("main.msg", null, locale));
        return MAIN_PAGE;
    }
}
