package com.beta.fantasytm.web;

import com.beta.fantasytm.*;
import com.beta.fantasytm.data.BuffRepository;
import com.beta.fantasytm.data.PlayerRepository;
import com.beta.fantasytm.data.TeamRepository;
import com.beta.fantasytm.data.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/create")
public class CreateTeamController {

    private static final Logger log = LoggerFactory.getLogger(CreateTeamController.class);
    private final PlayerRepository playerRepo;
    private TeamRepository teamRepo;
    private WalletRepository walletRepo;
    private BuffRepository buffRepo;

    @Autowired
    public CreateTeamController(PlayerRepository playerRepo, TeamRepository teamRepo, WalletRepository walletRepo, BuffRepository buffRepo) {
        this.playerRepo = playerRepo;
        this.teamRepo = teamRepo;
        this.walletRepo = walletRepo;
        this.buffRepo = buffRepo;
    }

    @GetMapping
    public String showCreateTeamForm(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("team", new Team()); // maybe unnecessary

        // Passing available balance so it can be displayed on the page
        Wallet wallet = walletRepo.findByUserId(user.getId());
        long balance = wallet.getBalance();
        model.addAttribute("availableFunds", balance);

        return "createTeam";
    }

    @PostMapping
    public String processCreation(@Valid @ModelAttribute("team") Team team, Errors errors, @AuthenticationPrincipal User user) {
        // Checking for errors
        if (errors.hasErrors()) {
            log.info(String.valueOf(errors.getAllErrors()));
        }

        // Calculating team value and subtracting from user's wallet
        Wallet wallet = walletRepo.findByUserId(user.getId());
        team.calculateCost();
        wallet.subtractBalance(team.getCost());
        walletRepo.save(wallet); // overwriting wallet data

        // Saving the team to the database
        team.setUser(user);
        team.setActiveBuff(buffRepo.findById(1L).get());
        //team.setActiveBuff(BuffType.NULL);
        teamRepo.save(team);

        return "redirect:/manage";
    }

    @ModelAttribute(name = "team")
    public Team teamModel() {
        return new Team();
    }

    @ModelAttribute
    public void playersModel(Model model) {
        // Add all players from database to list
        List<Player> players = new ArrayList<>();
        playerRepo.findAll().forEach(p -> players.add(p));

        // For each Position, add all players from that position to the model so that displayed data is ultimately fetched from database
        Position[] positions = Position.values();
        for (Position pos : positions) {
            model.addAttribute(pos.toString().toLowerCase(), filterByPosition(players, pos));
        }
    }

    // Returns a list of all players of a given position
    private List<Player> filterByPosition(List<Player> players, Position position)  {
        List<Player> toReturn = players.stream()
                .filter(x -> x.getPosition().equals(position))
                .collect(Collectors.toList());
        return toReturn;
    }

}
