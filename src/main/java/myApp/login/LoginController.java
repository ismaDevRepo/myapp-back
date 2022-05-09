package myApp.login;

import myApp.dto.UserLoginDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("login")
    public UserLoginDto postLogin(@RequestBody UserLoginDto userLoginDto) {
        return  userLoginDto;
    }
}
