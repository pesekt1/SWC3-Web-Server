package swc3.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf")
public class HomeController {

    //html - advanced web page with update and delete
    @RequestMapping("")
    public String home(){
        return "home/index";
    }
}
