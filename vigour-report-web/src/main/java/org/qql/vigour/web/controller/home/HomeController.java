package org.qql.vigour.web.controller.home;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Home页控制器
 *
 */
@Controller
@RequestMapping(value = {"", "/", "/home"})
public class HomeController {

    @GetMapping(value = {"", "/", "/index"})
    public String index(final Model model) {
        model.addAttribute("roleNames", "USER");
        model.addAttribute("user", "USER");
        return "home/index";
    }

    @ResponseBody
    @GetMapping(value = "/rest/home/getMenus")
    public List getMenus() {
        return null;
    }
}