
package io.dajzavrsi.dajzavrsi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public Map<String, String> getCurrentUser(Authentication authentication) {

        var result = new HashMap<String, String>();

        result.put("username", authentication.getName());

        return result;
    }
}
