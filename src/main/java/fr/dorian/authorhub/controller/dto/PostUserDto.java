package fr.dorian.authorhub.controller.dto;

import jakarta.validation.constraints.NotNull;

public record PostUserDto(@NotNull String username, @NotNull String password, @NotNull String mail) {}