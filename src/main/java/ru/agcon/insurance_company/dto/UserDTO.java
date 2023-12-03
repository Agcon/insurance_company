package ru.agcon.insurance_company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    @NotEmpty(message = "Имя не должно быть пустым")
    @NotNull
    private String name;

    @NotEmpty(message = "Имя не должно быть пустым")
    @NotNull
    @Email
    private String email;

    @NotEmpty(message = "Логин не должен быть пустым")
    @NotNull
    private String login;

    @NotEmpty(message = "Пароль не может быть пустым")
    @NotNull
    private String password;
}