package application.controller;

import application.dto.UserDTO;
import application.model.User;
import application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

    //Получение текущего пользователя.
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("get-all-users")
    public List<User> getListOfUsers() {
        List<User> userList = userService.findAll();
        return userList;
    }

    //Удаление пользователя.
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "delete-user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> deleteUserJson(@RequestBody UserDTO user) {
        if (user != null) {
            userService.deleteUserById(user.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Сохранение и удаление пользователя.
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "save-update-user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> saveUserJson(@RequestBody UserDTO user) {
        if (user != null) {
            if (user.getId() != null) {
                userService.save(new User(user.getId(), user.getName(), user.getLastName(), user.getEmail(), user.getAge(), user.getRoleId(), user.getPassword()));
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                userService.save(new User(user.getName(), user.getLastName(), user.getEmail(), user.getAge(), user.getRoleId(), user.getPassword()));
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Получение текущего пользователя в сессии.
    @RequestMapping(value = "current-session-user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user;
    }

    //Logout пользователя.
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
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