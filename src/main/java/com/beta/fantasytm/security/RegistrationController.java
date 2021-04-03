package com.beta.fantasytm.security;

import com.beta.fantasytm.Wallet;
import com.beta.fantasytm.data.UserRepository;
import com.beta.fantasytm.data.WalletRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepo;
    private WalletRepository walletRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController (UserRepository userRepo, PasswordEncoder passwordEncoder, WalletRepository walletRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.walletRepo = walletRepo;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form, Wallet wallet) {
        // If already exists, does not allow to create a user
        // Temporary solution, improve later with custom Validation
        if (!userRepo.findAllByUsername(form.getUsername()).isEmpty()) {
            return "redirect:/register";
        } else {
            userRepo.save(form.toUser(passwordEncoder));
            wallet.setUser(userRepo.findByUsername(form.getUsername()));
            wallet.setBalance(5000L);
            walletRepo.save(wallet);
            return "redirect:/login";
        }
    }
}
