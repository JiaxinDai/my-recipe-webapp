import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;



@RestController

public class UserController {

    @RequestMapping("/user")

    public User user(@AuthenticationPrincipal User principal) {

        return principal;

    }

}
