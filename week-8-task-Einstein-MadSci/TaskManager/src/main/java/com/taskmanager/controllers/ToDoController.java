package com.taskmanager.controllers;

import com.taskmanager.dto.ToDoDTO;
import com.taskmanager.models.ToDo;
import com.taskmanager.repositories.ToDoRepo;
import com.taskmanager.services.ToDoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ToDoController {
    private ToDoService toDoService;
    private ToDoRepo toDoRepo;

    @Autowired
    public ToDoController(ToDoService toDoService, ToDoRepo toDoRepo) {
        this.toDoService = toDoService;
        this.toDoRepo = toDoRepo;
    }

    @GetMapping("/addtask")
    public ModelAndView addingTask(ModelAndView modelAndView, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute("usernumber") == null){
            modelAndView.setViewName("redirect:/landing");
            return modelAndView;
        }
        modelAndView.addObject("todo", new ToDoDTO());
        modelAndView.setViewName("addtask");
        return modelAndView;
    }
    @PostMapping("/addtask")
    public String addTask(@ModelAttribute("todo") ToDoDTO toDoDTO, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute("usernumber") == null){
//            modelAndView.setViewName("redirect:/landing");
            return "landing";
        }
//        modelAndView.addObject("todo", toDoService.findAll());
//        modelAndView.setViewName("redirect:/alltask");
        toDoService.newTask(toDoDTO, (Long) session.getAttribute("usernumber"));
        return "redirect:/alltask";
    }

//    @PostMapping("/addtask")
//    public ModelAndView addTask(ModelAndView modelAndView, @ModelAttribute("todo") ToDoDTO toDoDTO){
////        HttpSession session = httpServletRequest.getSession();
////        if(session.getAttribute("usernumber") == null){
////            modelAndView.setViewName("redirect:/landing");
////            return modelAndView;
////        }
////        modelAndView.addObject("todo", toDoService.findAll());
//        modelAndView.setViewName("redirect:/alltask");
//        toDoService.newTask(toDoDTO);
//        return modelAndView;
//    }

    @GetMapping("/alltask")
    public ModelAndView allTask(ModelAndView modelAndView, HttpServletRequest httpServletRequest){
//        modelAndView.setViewName("alltask");
        HttpSession session = httpServletRequest.getSession();
        Long id = (Long) session.getAttribute("usernumber");
        if(id == null){
            modelAndView.setViewName("redirect:/landing");
            return modelAndView;
        }
        modelAndView.setViewName("alltask");
        modelAndView.addObject("todos", toDoService.findAllByUserId(id));
        return modelAndView;
    }

    @GetMapping("/completed")
    public ModelAndView showCompletedTasks(ModelAndView modelAndView, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute("usernumber") == null){
            modelAndView.setViewName("redirect:/landing");
            return modelAndView;
        }
        Long id = (Long) session.getAttribute("usernumber");
        List<ToDo> todos = toDoService.findAllByUserId(id);

        todos.removeIf(todo -> !todo.getStatus().equalsIgnoreCase("Completed"));
        modelAndView.setViewName("completed");
        modelAndView.addObject("todos", todos);
        return modelAndView;
    }

    @GetMapping("/pendingtask")
    public ModelAndView showPendingTasks(ModelAndView modelAndView, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute("usernumber") == null){
            modelAndView.setViewName("redirect:/landing");
            return modelAndView;
        }

        Long id = (Long) session.getAttribute("usernumber");
        List<ToDo> todos = toDoService.findAllByUserId(id);
        todos.removeIf(todo -> !todo.getStatus().equalsIgnoreCase("Pending"));
        modelAndView.setViewName("pendingtask");
        modelAndView.addObject("todos", todos);
        return modelAndView;
    }

    @GetMapping("/edittask/{id}")
    public String editTask(@PathVariable("id") Long id, Model model){
        ToDo todo = toDoService.findToDo(id);
        model.addAttribute("todo", todo);
        return "edittask";
    }

    @PostMapping("/edittask/{id}")
    public String editTask(@PathVariable("id") Long id, @ModelAttribute("todo") ToDoDTO toDoDTO){
        ToDo toDo = toDoService.findToDo(id);
        toDo.setTitle(toDoDTO.getTitle());
        toDo.setDescription(toDoDTO.getDescription());
        toDo.setUpdatedDate(toDoDTO.getUpdatedDate());
        toDoRepo.save(toDo);
        return "redirect:/alltask";
    }

    @GetMapping("/deletetask/{taskId}")
    public String deleteTask(@PathVariable("taskId") Long id){
        toDoService.deleteById(id);
        return "redirect:/alltask";
    }

    @GetMapping("/completed/{taskId}")
    public String completeTask(@PathVariable("taskId") Long id){
        toDoService.completeTask(id);
        return "redirect:/alltask";
    }
}
