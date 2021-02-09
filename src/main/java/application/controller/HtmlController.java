package application.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HtmlController {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("user-test")
    public String getAdminPanel() {
        return "adminPanel";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("user-test2")
    public String getAdminInfo() {
        return "adminInfo";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("user-test3")
    public String getUserInfo() {
        return "userInfo";
    }

    @GetMapping("login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
}
