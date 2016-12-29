package org.sm.lab.mybooks.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "/static/app/html/index.html";
    }

}
