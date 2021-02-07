package application.controller;

import application.model.Role;
import application.model.User;
import application.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("/admin")
//    public String getListOfUsers(Model model) {
//        List<User> userList = userService.findAll();
//        model.addAttribute("user", userList);
//        return "user_list";
//    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("user-list-json")
    public List<User> getListOfUsers() {
        List<User> userList = userService.findAll();
        return userList;
    }

    @GetMapping("admin/edit/{id}")
    public String editUser(Model model, @PathVariable("id") long id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "update_user";
    }

    @PostMapping("admin/edit/update/{id}")
    public String editUser(@PathVariable("id") long id, User user, Model model) {
        userService.save(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "user-post-json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteUserJson(@RequestBody User user) {
        userService.deleteUserById(user.getId());
    }

    @RequestMapping(value = "user-save-json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void saveUserJson(@RequestBody User user) throws JsonProcessingException {
        userService.save(user);
    }

    @GetMapping("admin/delete/{id}")
    public String deleteUser(Model model, @PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("user")
    public String getCurrentUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "user_info";
    }


    @GetMapping("admin/addUser")
    public String addUser(User user) {
        return "add_user";
    }

    @PostMapping("admin/saveUser")
    public String saveUser(User user) {
        userService.save(user);
        return "redirect:/admin";
    }

}