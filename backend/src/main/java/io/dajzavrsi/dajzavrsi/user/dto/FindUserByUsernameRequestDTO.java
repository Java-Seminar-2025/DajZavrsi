package io.dajzavrsi.dajzavrsi.user.dto;

import jakarta.validation.constraints.NotBlank;

public record FindUserByUsernameRequestDTO(
    @NotBlank String username
) {}
