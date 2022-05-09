package com.beta.fantasytm.security;

import com.beta.fantasytm.User;
import com.beta.fantasytm.Wallet;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;


@Data
public class RegistrationForm {

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "Password cannot be empty")
    private String confirmPassword;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password));
    }

}
