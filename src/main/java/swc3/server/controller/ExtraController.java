package swc3.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class ExtraController {

    //mapping the index.html
    @RequestMapping("thymeleaf")
    public String home(){
        return "home/index";
    }

    //function for accessing a reusable navbar.html
    @RequestMapping("navbar")
    public String getNavbar() {
        return "home/navbar";
    }
}
