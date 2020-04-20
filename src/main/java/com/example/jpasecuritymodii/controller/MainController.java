package com.example.jpasecuritymodii.controller;
import com.example.jpasecuritymodii.model.AppUser;
import com.example.jpasecuritymodii.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class MainController {

    private UserServiceImpl userServiceImpl;

    @Autowired
    public MainController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
    @RequestMapping("")
    public String webStarter(){
        return "registration";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/sing-up")
    public ModelAndView singup(){
        return new ModelAndView("registration", "user", new AppUser());
    }

    @RequestMapping("/registration")
    public ModelAndView registration(AppUser user, HttpServletRequest request)  {
            userServiceImpl.addNewUser(user, request);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/verifyToken")
    public ModelAndView verify(@RequestParam String token){

        userServiceImpl.verifyToken(token);

        return new ModelAndView("redirect:/login");
    }
    @GetMapping("/forAll")
    public String forAll(){
        return "YOU ARE LOGGED OUT: " ;
    }

    @GetMapping("/forUser")
    public String forUser(Principal principal){
        return "FOR USER: " +principal.getName();
    }

    @GetMapping("/forAdmin")
    public String forAdmin(Principal principal){
        return "FOR ADMIN: " +principal.getName();
    }
}
