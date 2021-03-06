package com.beta.fantasytm.web;

import com.beta.fantasytm.*;
import com.beta.fantasytm.data.BuffRepository;
import com.beta.fantasytm.data.PlayerRepository;
import com.beta.fantasytm.data.TeamRepository;
import com.beta.fantasytm.data.WalletRepository;
import com.beta.fantasytm.Buff;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Controller
@RequestMapping("/manage")
public class ManageController {

    private static final Logger log = LoggerFactory.getLogger(ManageController.class);
    private final PlayerRepository playerRepo;
    private TeamRepository teamRepo;
    private WalletRepository walletRepo;
    private BuffRepository buffRepo;

    @Autowired
    public ManageController(PlayerRepository playerRepo, TeamRepository teamRepo, WalletRepository walletRepo, BuffRepository buffRepo) {
        this.playerRepo = playerRepo;
        this.teamRepo = teamRepo;
        this.walletRepo = walletRepo;
        this.buffRepo = buffRepo;
    }

    @ModelAttribute
    public void loadData(Model model, @AuthenticationPrincipal User user) {
        // Rankings
        ShowTeamsForm showTeamsForm = new ShowTeamsForm();
        showTeamsForm.getTeams().addAll((Collection<? extends Team>) teamRepo.findAll());
        model.addAttribute("allTeams", showTeamsForm);

        // User team
        Team userTeam = teamRepo.findByUserId(user.getId());
        model.addAttribute("userTeam", userTeam);

        // User players
        ArrayList<Player> players = new ArrayList<>();
        players.addAll(userTeam.getPlayers());
        model.addAttribute("userPlayers", players);

        // User wallet
        Wallet userWallet = walletRepo.findByUserId(user.getId());
        model.addAttribute("userWallet", userWallet);

        // User
        model.addAttribute("userInformation", user);

        // Buff to return
        model.addAttribute("buff", new Buff());

        // Buffs available in user's wallet
        List<Buff> buffs = new ArrayList<>();
        buffs.addAll(userWallet.getBuffs());
        buffs.remove(0); // removing NULL buff
        model.addAttribute("availableBuffs", buffs);

        // Ranking
        // possibly teamsRanked.subList() with userTeamRank -5, +5
        List<Team> teamsRanked = new ArrayList<>();
        teamsRanked.addAll(teamRepo.findByOrderByPointsDesc());
        model.addAttribute("teamsRanked", teamsRanked);

        // User team rank
        Integer userTeamRank = 0;
        for (int i = 0; i < teamsRanked.size(); i++) {
            if (teamsRanked.get(i).getId().equals(userTeam.getId())) {
                userTeamRank = i + 1;
                break;
            }
        }

        model.addAttribute("userTeamRank", userTeamRank);

    }

    @GetMapping
    public String showManageForm() {
        return "manage";
    }

    @PostMapping
    public String updateBuffs(@AuthenticationPrincipal User user, @ModelAttribute("buff") Buff buff, Errors errors) {
        Team team = teamRepo.findByUserId(user.getId());
        Buff toAssign = buffRepo.findById(buff.getId()).get();
        Wallet wallet = walletRepo.findByUserId(user.getId());

        // Subtract buff from the wallet and apply as active to the team
        if (wallet.getBuffs().contains(toAssign)) { // Evaluates equality only on BuffType, not ID
            wallet.getBuffs().remove(buff);
            team.setActiveBuff(toAssign);
            teamRepo.save(team);
            walletRepo.save(wallet);

            return "redirect:/manage";
        } else {
            throw new RuntimeException("Wallet does not contain specified buff");
        }
    }
}
