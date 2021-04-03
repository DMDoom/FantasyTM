package com.beta.fantasytm.security;

import com.beta.fantasytm.User;
import com.beta.fantasytm.Wallet;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

    private String username;
    private String password;

    // Converting registration form into a user with encrypted passwords
    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password));
    }

}
