package by.nichipor.taxiservice.controller;

import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;
import by.nichipor.taxiservice.service.usermanager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * Created by Max Nichipor on 08.08.2016.
 */

@Controller
@RequestMapping({"", "/", "/main"})
public class MainPageController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String showMainPage(Locale locale, Model ui){
        ui.addAttribute("msg", messageSource.getMessage("main.msg", null, locale));
        return "main";
    }

    @RequestMapping(value = "map", method = RequestMethod.GET)
    public String showMap(Model ui){
        ui.addAttribute("placeOut", "&origin=Belarus");
        return "map";
    }

    @RequestMapping(value = "map", method = RequestMethod.POST)
    public String showMap(@RequestParam("placeOut") String placeOut, @RequestParam("placeIn") String placeIn, Model ui){
        String enPlaceIn = null;
        String enPlaceOut = null;
        byte[] in = null;
        byte[] out = null;
        try {
            in = placeIn.getBytes();
            out = placeOut.getBytes();
            enPlaceIn = new String(in, "UTF-8");
            enPlaceOut = new String(out, "UTF-8");
        } catch (UnsupportedEncodingException e){
            System.err.println(e);
        }
        ui.addAttribute("placeOut", "&origin=" + enPlaceOut);
        if (placeIn.length() > 0) {
            ui.addAttribute("placeIn", "&destination=" + enPlaceIn);
        }
        return "map";
    }
}
