package by.nichipor.taxiservice.service.userpanel.controller;

import by.nichipor.taxiservice.entity.User;
import by.nichipor.taxiservice.service.usermanager.UserManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Max Nichipor on 05.09.2016.
 */

@Controller
@RequestMapping("/user")
public class UserPanelController {
    private static Logger logger = Logger.getLogger(UserPanelController.class);

    private static ArrayList<String> availableFormats;
    private static final String USER_PANEL_PAGE = "user";
    private static final String USER_SETTINGS_PAGE = "user_settings";
    private static final String FUNCTION_SCRIPT = "<script>visible();</script>";

    static {
        availableFormats = new ArrayList<>();
        availableFormats.add("png");
        availableFormats.add("jpg");
        availableFormats.add("gif");
        availableFormats.add("jpeg");
        availableFormats.add("bmp");
    }

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserManager userManager;

    @RequestMapping(method = RequestMethod.GET)
    public String userPage(Model ui){
        User user = userManager.findUserByUsername(UserManager.getCurrentUsername());
        ui.addAttribute("numberOfUserOrders", userManager.findNumberOfUserOrders(user));
        ui.addAttribute("orders", userManager.findAllAcceptedUserOrders(user));
        ui.addAttribute("image", getCurrentUserAvatar());
        ui.addAttribute("hasAvatar", userManager.hasAvatar(UserManager.getCurrentUsername()));
        ui.addAttribute("username", UserManager.getCurrentUsername());
        return USER_PANEL_PAGE;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void applyChanges(@RequestPart ("avatar") MultipartFile avatar, Model ui, Locale locale) throws IOException{
        String currentUser = UserManager.getCurrentUsername();
        String fileFormat = avatar.getOriginalFilename().split("\\.")[1];
        if (availableFormats.contains(fileFormat)) {
            if (avatar.getSize() < 1000000) {
                if (!userManager.hasAvatar(currentUser)) {
                    userManager.addAvatar(currentUser, avatar.getInputStream());
                } else {
                    userManager.updateAvatar(currentUser, avatar.getInputStream());
                }
            } else {
                ui.addAttribute("error", messageSource.getMessage("file_too_large", null, locale));
                ui.addAttribute("function", FUNCTION_SCRIPT);
            }
        } else {
            ui.addAttribute("error", messageSource.getMessage("invalid_file_format", null, locale));
            ui.addAttribute("function", FUNCTION_SCRIPT);
        }
        userPage(ui);
    }

    @RequestMapping(value = "", params = {"deleteAvatar"}, method = RequestMethod.GET)
    public void deleteAvatar(Model ui){
        userManager.deleteAvatar(UserManager.getCurrentUsername());
        userPage(ui);
    }

    @RequestMapping(value = "", params = {"changeUsername"}, method = RequestMethod.GET)
    public void changeUsername(@RequestParam("username") String newUsername, Model ui){
        User currentUser = userManager.findUserByUsername(UserManager.getCurrentUsername());
        if (!newUsername.isEmpty()) {
            userManager.changeUserName(currentUser, newUsername);
        }
        userPage(ui);
    }

    @RequestMapping(value = "invalidFileFormat", method = RequestMethod.GET,
            produces = "text/html; charset=utf-8")
    @ResponseBody
    public String invalidFileErrorMessage(){
        return messageSource.getMessage("invalid_file_format", null, RequestContextUtils.getLocale(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()));
    }

    @RequestMapping(value = "fileTooLarge", method = RequestMethod.GET,
            produces = "text/html; charset=utf-8")
    @ResponseBody
    public String tooLargeFileErrorMessage(){
        return messageSource.getMessage("file_too_large", null, RequestContextUtils.getLocale(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()));
    }

    private String getCurrentUserAvatar() {
        byte[] encodeBase64 = Base64.encode(userManager.findAvatar(UserManager.getCurrentUsername()));
        String base64Encoded = null;
        try {
            base64Encoded = new String(encodeBase64, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        return base64Encoded;
    }
}
