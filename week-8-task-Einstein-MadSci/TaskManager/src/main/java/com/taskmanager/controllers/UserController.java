package com.taskmanager.controllers;

import com.taskmanager.dto.UserDTO;
import com.taskmanager.models.ToDo;
import com.taskmanager.models.User;
import com.taskmanager.services.ToDoService;
import com.taskmanager.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;
    private ToDoService toDoService;

//    injecting userService and toDoService as a dependencies
    @Autowired
    public UserController(UserService userService, ToDoService toDoService) {
        this.userService = userService;
        this.toDoService = toDoService;
    }

//    Get mapping to display home page (which is login)
    @GetMapping("/")
    public ModelAndView displayHomePage(){
//        model and view to hold view and userDTO object
        ModelAndView modelAndView = new ModelAndView();
//        setting view name to landing ("log in page")
        modelAndView.setViewName("landing");
//        adding userDTO to modelandview to allow view to use user data
        modelAndView.addObject("user", new UserDTO());
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView userLogOut(ModelAndView modelAndView, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
            session.invalidate();
            modelAndView.setViewName("landing");
            modelAndView.addObject("user", new UserDTO());
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView userSignUp( ModelAndView modelAndView){
        modelAndView.setViewName("signup");
        modelAndView.addObject("user", new UserDTO());
        return modelAndView;
    }

    @PostMapping("/signin")
    public String signInForm(@ModelAttribute("user") UserDTO userDTO, HttpServletRequest httpServletRequest, Model model){
        User user = userService.findUser(userDTO);
        if(user == null){
            return "signup";
        }
        HttpSession session = httpServletRequest.getSession();
        Long id = user.getUserId();
        session.setAttribute("usernumber", id);
        model.addAttribute("todos", toDoService.findAllByUserId(id));
        return "redirect:/alltask";
    }
    @GetMapping("/landing")
    public ModelAndView signUpForm(ModelAndView modelAndView){
        modelAndView.setViewName("landing");
        modelAndView.addObject("user", new UserDTO());
        return modelAndView;
    }
    @PostMapping("/signup")
    public String signUpForm(@ModelAttribute("user") UserDTO userDTO){
        userService.addUser(userDTO);
        return "landing";
    }
}