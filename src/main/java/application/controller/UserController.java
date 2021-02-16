package application.controller;

import application.dto.UserDTO;
import application.model.User;
import application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping("get-all-users")
    public List<UserDTO> getListOfUsers() {
        List<User> allUsersList = userService.findAll();
        List<UserDTO> resultUserDTOList = new LinkedList<>();
        for (User user : allUsersList) {
            resultUserDTOList.add(new UserDTO(user));
        }
        return resultUserDTOList;
    }

    @PostMapping("delete-user")
    public ResponseEntity<UserDTO> deleteUserJson(@RequestBody UserDTO user) {
        if (user != null) {
            userService.deleteUserById(user.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("save-update-user")
    public ResponseEntity<UserDTO> saveUserJson(@RequestBody UserDTO user) {
        if (user != null) {
            User gettingUser = new User(user);
            if (user.getId() != null) {
                userService.update(gettingUser);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                userService.save(gettingUser);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("current-session-user")
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        return userDTO;
    }

    @PostMapping("/logout")
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