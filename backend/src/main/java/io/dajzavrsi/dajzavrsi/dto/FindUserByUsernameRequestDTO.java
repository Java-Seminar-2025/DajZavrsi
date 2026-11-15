package io.dajzavrsi.dajzavrsi.dto;

import jakarta.validation.constraints.NotBlank;

public record FindUserByUsernameRequestDTO(
    @NotBlank String username
) {}
