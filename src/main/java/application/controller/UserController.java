package application.controller;

import application.dto.RoleDTO;
import application.dto.UserDTO;
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
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("user-list-json")
    public List<User> getListOfUsers() {
        List<User> userList = userService.findAll();
        return userList;
    }

    //Delete user
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "user-post-json", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> deleteUserJson(@RequestBody UserDTO user) {
        if (user != null) {
            userService.deleteUserById(user.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Save/Edit user
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "user-save-json", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> saveUserJson(@RequestBody UserDTO user) {
        if (user != null) {
            RoleDTO roleDTO = user.getRoles().stream().findFirst().get();
            if (user.getId() != null) {
                userService.save(new User(user.getId(), user.getName(), user.getLastName(), user.getEmail(), user.getAge(), Collections.singleton(new Role(roleDTO.getRoleId(), roleDTO.getRoleName())), user.getPassword()));
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                userService.save(new User(user.getName(), user.getLastName(), user.getEmail(), user.getAge(), Collections.singleton(new Role(roleDTO.getRoleId(), roleDTO.getRoleName())), user.getPassword()));
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "session-user-json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user;
    }

    //Logout USER.
    @RequestMapping(value = "/logout-custom", method = RequestMethod.POST)
    public RedirectView logoutPage(HttpServletRequest request,
                                   HttpServletResponse response) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new RedirectView("/login.html");
    }
}