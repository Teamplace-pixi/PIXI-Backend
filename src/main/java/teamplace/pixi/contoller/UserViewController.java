package teamplace.pixi.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/users/login")
    public String login() {
        return "login";
    }

    @GetMapping("/users/signup")
    public String signup() {
        return "signup";
    }
}
