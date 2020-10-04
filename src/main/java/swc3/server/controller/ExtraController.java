package swc3.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExtraController {
    @RequestMapping("navbar")
    public String getNavbar() {
        return "home/navbar";
    }
}
