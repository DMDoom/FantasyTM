package com.beta.fantasytm.web;

import com.beta.fantasytm.*;
import com.beta.fantasytm.data.PlayerRepository;
import com.beta.fantasytm.data.TeamRepository;
import com.beta.fantasytm.data.WalletRepository;
import com.beta.fantasytm.web.forms.ShowTeamsForm;
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
import java.util.Collection;


@Controller
@RequestMapping("/manage")
public class ManageController {

    private static final Logger log = LoggerFactory.getLogger(ManageController.class);
    private final PlayerRepository playerRepo;
    private TeamRepository teamRepo;
    private WalletRepository walletRepo;

    @Autowired
    public ManageController(PlayerRepository playerRepo, TeamRepository teamRepo, WalletRepository walletRepo) {
        this.playerRepo = playerRepo;
        this.teamRepo = teamRepo;
        this.walletRepo = walletRepo;
    }

    @ModelAttribute
    public void loadData(Model model, @AuthenticationPrincipal User user) {
        // Rankings (wrapper class)
        ShowTeamsForm showTeamsForm = new ShowTeamsForm();
        showTeamsForm.getTeams().addAll((Collection<? extends Team>) teamRepo.findAll());
        model.addAttribute("allTeams", showTeamsForm);

        // User players
        Team userTeam = teamRepo.findByUserId(user.getId());
        ArrayList<Player> players = new ArrayList<>();
        players.addAll(userTeam.getPlayers());
        model.addAttribute("userPlayers", players);

        // User team
        model.addAttribute("userTeam", userTeam);

        // User buffs
        ArrayList<Buff> buffs = new ArrayList<>();
        buffs.addAll(walletRepo.findByUserId(user.getId()).getBuffs());
        model.addAttribute("userBuffs", buffs);

        // User wallet
        Wallet userWallet = walletRepo.findByUserId(user.getId());
        model.addAttribute("userWallet", userWallet);

        // For return team, the one it will bind it to after transfers and buffs
        model.addAttribute("newTeam", new Team());

    }

    @GetMapping
    public String showManageForm() {
        return "manage";
    }

    // NEEDS TESTING
    @PostMapping(params = "action=updateBuffs")
    public String updateBuffs(@AuthenticationPrincipal User user, @ModelAttribute("newTeam") Team newTeam, Errors errors) {

        // Overwrite user's team
        Team team = teamRepo.findByUserId(user.getId());
        team.setBuff(newTeam.getBuff());
        teamRepo.save(team);

        // Subtract buffs from wallet
        // Ensure this post is impossible unless the user has the buff in the first place
        Wallet wallet = walletRepo.findByUserId(user.getId());
        wallet.getBuffs().remove(newTeam.getBuff());
        walletRepo.save(wallet);


        return "redirect:/manage";
    }

    /*
    When transferring, a newly formed team will be created and returned. It will then overwrite the original team from
    before the transfer
    @PostMapping(params = "action=updateAfterTransfer")
    public String updateAfterTransfer(@Valid @ModelAttribute("team") Team team, @AuthenticationPrincipal User user, Errors errors) {
        if (errors.hasErrors()) {
            log.info(errors.getAllErrors().toString());
        }

        // TEST THIS
        // Needs to overwrite current player's team
        teamRepo.save(team);

        return "redirect:/manage";
    }
    */

    // Implement token activation here
    // First will need planning and implementing the feature in other areas!
    // Tokens will be held in the Wallet
    // A team will only be allowed one token to be active, so each team can have a token object
    // Make sure this works with all the other currently implemented features

}
