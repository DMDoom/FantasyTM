package com.beta.fantasytm.web;

import com.beta.fantasytm.Player;
import com.beta.fantasytm.Position;
import com.beta.fantasytm.Team;
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

    @Autowired
    public TerminalController(TeamRepository teamRepo, PlayerRepository playerRepo) {
        this.teamRepo = teamRepo;
        this.playerRepo = playerRepo;
    }

    @GetMapping
    public String showTerminal(Model model) {
        UpdateTeamsForm updateForm = new UpdateTeamsForm();

        // Add all existing players to the model
        updateForm.getPlayers().addAll((Collection<? extends Player>) playerRepo.findAll());

        model.addAttribute("form", updateForm);

        return "terminal";
    }

    @PostMapping(params = "action=updateBasedOnAll")
    public String processUpdates(@ModelAttribute("form") UpdateTeamsForm form) {
        // Update players
        for (Player player : form.getPlayers()) {
            playerRepo.save(player);
        }

        // Update all teams based on updated players score
        for (Team team : teamRepo.findAll()) {
            team.updatePoints();
            teamRepo.save(team);
        }


        return "redirect:/login";
    }

    // UNTESTED
    // TODO:
    // TEST
    @PostMapping(params = "action=updateBasedOnCaptains")
    public String processFirstSix(@ModelAttribute("form") UpdateTeamsForm form) {

        // Updating captains
        for (int i = 1; i <= 16; i++) {
            playerRepo.save(form.getPlayers().get(i));
        }

        // Updating regulars based on captains
        int counterForCaptains = 1;
        for (int i = 17; i <= 33; i++) {
            Player placeholder = form.getPlayers().get(counterForCaptains);
            placeholder.setPosition(Position.REGULAR);
            placeholder.setId((long) i);
            playerRepo.save(placeholder);

            counterForCaptains++;
        }

        counterForCaptains = 9; // adjusted for underdogs being lower in rankings

        // Updating underdogs based on captains
        for (int i = 35; i <= 42; i++) {
            Player placeholder = form.getPlayers().get(counterForCaptains);
            placeholder.setPosition(Position.UNDERDOG);
            placeholder.setId((long) i);
            playerRepo.save(placeholder);

            counterForCaptains++;
        }

        // Update all teams based on updated players score
        for (Team team : teamRepo.findAll()) {
            team.updatePoints();
            teamRepo.save(team);
        }

        return "redirect:/login";
    }
}
