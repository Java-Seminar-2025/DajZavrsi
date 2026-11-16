package io.dajzavrsi.dajzavrsi.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import io.dajzavrsi.dajzavrsi.user.dto.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {

        var user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return user.get();
    }

    public FindUserByUsernameResponseDTO findUserByUsername(FindUserByUsernameRequestDTO request) {
        var maybe_user = userRepository.findByUsername(request.username());

        return new FindUserByUsernameResponseDTO(maybe_user.isEmpty() ? null : maybe_user.get().getId());
    }

    public CreateUserResponseDTO createUser(CreateUserRequestDTO request) {
        var password = generateInitialPassword();
        var password_hash = passwordEncoder.encode(password);

        var user = new User(
            null,
            request.username(),
            password_hash,
            request.role()
        );

        user = userRepository.save(user);

        var response = new CreateUserResponseDTO(user.getId(), password);

        return response;
    }

    public void changeUserPassword(ChangeUserPasswordRequestDTO request) {
        var maybe_user = userRepository.findByUsername(request.username());
        if (maybe_user.isEmpty()) {
            throw new UsernameNotFoundException(request.username());
        }

        var user = maybe_user.get();

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password must match current password!");
        }

        var new_password_hash = passwordEncoder.encode(request.newPassword());
        user.setPassword(new_password_hash);

        userRepository.save(user);
    }

    private String generateInitialPassword() {
        return "initial_password";
    }
}
