package io.dajzavrsi.dajzavrsi.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import io.dajzavrsi.dajzavrsi.user.dto.*;

@Component
@RequiredArgsConstructor
public class BootstrapInitialAdmin implements CommandLineRunner {
    private final UserService userService;

    @Value("${io.dajzavrsi.admin.username:admin}")
    private String username;

    @Value("${io.dajzavrsi.admin.password:admin}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        var maybeUser = userService.findUserByUsername(new FindUserByUsernameRequestDTO(username));
        if (maybeUser.id() != null) {
            return;
        }

        var createResponse = userService.createUser(new CreateUserRequestDTO(username, Role.ADMIN));

        System.out.println("Created admin account " + username + " with password " + createResponse.password());

        userService.changeUserPassword(new ChangeUserPasswordRequestDTO(username, createResponse.password(), password));

        System.out.println("Created default user " + username + " with password " + password);
    }
}
