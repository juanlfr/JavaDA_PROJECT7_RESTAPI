package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private static final Logger log = LogManager.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/list")
    public String home(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("users", userList);
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            //user.setPassword(encoder.encode(user.getPassword()));
            userService.createUser(user);
            //userRepository.save(user);
            //model.addAttribute("users", userRepository.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        //User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        Optional<User> userToUpdate = userService.findById(id);
        //user.setPassword("");
        model.addAttribute("user", userToUpdate);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
        try {

            Optional<User> userToUpdate = userService.findById(id);
            userToUpdate.get().setUsername(user.getUsername());
            userToUpdate.get().setFullname(user.getFullname());
            //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            //user.setPassword(encoder.encode(user.getPassword()));
            //user.setId(id);
            //userRepository.save(user);
            //model.addAttribute("users", userRepository.findAll());
            userService.createUser(userToUpdate.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        //User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        try {
            Optional<User> userToDelete = userService.findById(id);
            if (userToDelete.isPresent()) {
                userService.delete(userToDelete.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while deleting rating with id:" + id);
        }
        //model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
