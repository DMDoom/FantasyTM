package com.beta.fantasytm.security;

import com.beta.fantasytm.Wallet;
import com.beta.fantasytm.data.BuffRepository;
import com.beta.fantasytm.data.UserRepository;
import com.beta.fantasytm.data.WalletRepository;
import com.beta.fantasytm.Buff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepo;
    private WalletRepository walletRepo;
    private PasswordEncoder passwordEncoder;
    private BuffRepository buffRepo;

    @Autowired
    public RegistrationController (UserRepository userRepo, PasswordEncoder passwordEncoder, WalletRepository walletRepo, BuffRepository buffRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.walletRepo = walletRepo;
        this.buffRepo = buffRepo;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        // If already exists, does not allow to create a user
        // Temporary solution, improve later with custom Validation
        if (!userRepo.findAllByUsername(form.getUsername()).isEmpty()) {
            return "redirect:/register";
        } else {
            // Save user with encrypted password
            userRepo.save(form.toUser(passwordEncoder));

            // Was previously in the method arguments field for some reason
            Wallet wallet = new Wallet();

            // Set wallet ownership
            wallet.setUser(userRepo.findByUsername(form.getUsername()));

            // Set default wallet balance
            wallet.setBalance(5000L); // will be 650

            // Set default wallet buffs
            List<Buff> buffs = new ArrayList<>();
            buffRepo.findAll().forEach(buff -> buffs.add(buff));
            wallet.getBuffs().addAll(buffs);

            // Save the wallet
            walletRepo.save(wallet);

            return "redirect:/login";
        }
    }
}
