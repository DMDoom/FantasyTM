package com.beta.fantasytm.security;

import com.beta.fantasytm.User;
import com.beta.fantasytm.Wallet;
import com.beta.fantasytm.data.BuffRepository;
import com.beta.fantasytm.data.UserRepository;
import com.beta.fantasytm.data.WalletRepository;
import com.beta.fantasytm.Buff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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
    public String registerForm(Model model) {
        RegistrationForm form = new RegistrationForm();
        model.addAttribute("form", form);
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid @ModelAttribute("form") RegistrationForm form, Errors errors) {
        // Temporary solution
        if (!userRepo.findAllByUsername(form.getUsername()).isEmpty() || !form.getConfirmPassword().equals(form.getPassword())) {
            return "redirect:/register";
        } else if (errors.hasErrors()) {
            return "registration";
        } else {
            // Save user
            userRepo.save(form.toUser(passwordEncoder));
            Wallet wallet = new Wallet();

            // Set wallet ownership
            wallet.setUser(userRepo.findByUsername(form.getUsername()));

            // Set default wallet balance
            wallet.setBalance(650L);

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
