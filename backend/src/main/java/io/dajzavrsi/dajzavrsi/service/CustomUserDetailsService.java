
package io.dajzavrsi.dajzavrsi.service;

import io.dajzavrsi.dajzavrsi.entity.User;
import io.dajzavrsi.dajzavrsi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        var result = userRepository.findByUsername(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        var user = result.get();

        var passwordHash = new String(
            user.getPassword(), StandardCharsets.UTF_8
        ).replace("\u0000", "");

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(passwordHash)
            .roles(user.getRole().name())
            .build();
    }
}
