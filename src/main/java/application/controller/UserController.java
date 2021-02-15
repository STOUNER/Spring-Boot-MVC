package application.controller;

import application.dto.RoleDTO;
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
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Получение текущего пользователя.
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "get-all-users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getListOfUsers() {
        List<User> userList = userService.findAll();
        List<UserDTO> userDTOList = new LinkedList<UserDTO>();
        for (User user : userList) {
            userDTOList.add(
                    new UserDTO(user.getId(), user.getName(), user.getLastName(), user.getEmail(), user.getAge(), user.getRole_number(), user.getPassword(), Collections.singletonList(new RoleDTO(user.
                            getRoleSet()
                            .stream()
                            .findFirst()
                            .get()
                            .getId(),
                            user
                                    .getRoleSet()
                                    .stream()
                                    .findFirst()
                                    .get()
                                    .getRoleName(),
                            user.getRoleSet()
                                    .stream()
                                    .findFirst()
                                    .get()
                                    .getRoleNumber()))));
        }
        return userDTOList;

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
                userService.update(user);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                User newUser = new User(user.getName(), user.getLastName(), user.getEmail(), user.getAge(), user.getRoleId(), user.getPassword());
                userService.save(newUser);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Получение текущего пользователя в сессии.
    @RequestMapping(value = "current-session-user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getLastName(), user.getEmail(), user.getAge(), user.getRole_number(), user.getPassword(), Collections.singletonList(new RoleDTO(user.
                getRoleSet()
                .stream()
                .findFirst()
                .get()
                .getId(),
                user
                        .getRoleSet()
                        .stream()
                        .findFirst()
                        .get()
                        .getRoleName(),
                user.getRoleSet()
                        .stream()
                        .findFirst()
                        .get()
                        .getRoleNumber())));
        return userDTO;
    }

    //Logout пользователя.
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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