
package io.dajzavrsi.dajzavrsi.service;

import io.dajzavrsi.dajzavrsi.entity.User;
import io.dajzavrsi.dajzavrsi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {

        Optional<User> result = userRepository.findByUsername(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = result.get();

        String passwordHash = new String(
            user.getPassword(), StandardCharsets.UTF_8
        ).replace("\u0000", "");

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(passwordHash)
            .roles(user.getRole().name())
            .build();
    }
}
