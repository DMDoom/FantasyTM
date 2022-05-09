package com.beta.fantasytm.web;

import com.beta.fantasytm.Player;
import com.beta.fantasytm.Team;
import com.beta.fantasytm.data.BuffRepository;
import com.beta.fantasytm.data.PlayerRepository;
import com.beta.fantasytm.data.TeamRepository;
import com.beta.fantasytm.web.forms.UpdateTeamsForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/terminal")
public class TerminalController {

    private static final Logger log = LoggerFactory.getLogger(CreateTeamController.class);

    TeamRepository teamRepo;
    PlayerRepository playerRepo;
    BuffRepository buffRepo;

    @Autowired
    public TerminalController(TeamRepository teamRepo, PlayerRepository playerRepo, BuffRepository buffRepo) {
        this.teamRepo = teamRepo;
        this.playerRepo = playerRepo;
        this.buffRepo = buffRepo;
    }

    @GetMapping
    public String showTerminal(Model model) {
        // Display all players
        UpdateTeamsForm updateForm = new UpdateTeamsForm();
        updateForm.getPlayers().addAll((Collection<? extends Player>) playerRepo.findAll());
        model.addAttribute("form", updateForm);

        return "terminal";
    }

    // Right now only this is functional for recentStepPoints calculations that take into account active buffs
    @PostMapping(params = "action=updateBasedOnAll")
    public String processUpdates(@ModelAttribute("form") UpdateTeamsForm form) {

        // Update players
        for (Player player : form.getPlayers()) {
            player.sumUpPoints();
            playerRepo.save(player);
        }

        for (Team team : teamRepo.findAll()) {
            team.updatePoints();
            team.setActiveBuff(buffRepo.findById(1L).get());
            teamRepo.save(team);
        }

        return "redirect:/manage";
    }
}
