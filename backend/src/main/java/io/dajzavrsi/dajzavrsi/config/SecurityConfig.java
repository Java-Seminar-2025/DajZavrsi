
package io.dajzavrsi.dajzavrsi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {

        http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests((auth) -> auth
            .requestMatchers("/api/auth/login")
            .permitAll()
            .anyRequest()
            .authenticated());

        http.formLogin((form) -> form
            .loginPage("/users/login")
            .failureUrl("/users/login?error")
            .loginProcessingUrl("/api/auth/login")
        );

        // TODO: configure CSRF properly.
        http.csrf((csrf) -> csrf.disable());

        return http.build();
    }
}
