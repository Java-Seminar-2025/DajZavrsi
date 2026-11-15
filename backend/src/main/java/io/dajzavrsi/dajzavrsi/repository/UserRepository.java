package io.dajzavrsi.dajzavrsi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import io.dajzavrsi.dajzavrsi.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
