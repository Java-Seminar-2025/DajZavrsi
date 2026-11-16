package io.dajzavrsi.dajzavrsi.user.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeUserPasswordRequestDTO(
    @NotBlank String username,
    @NotBlank String oldPassword,
    @NotBlank String newPassword) {}
