package com.beta.fantasytm.web;

import com.beta.fantasytm.Player;
import com.beta.fantasytm.Team;
import com.beta.fantasytm.data.PlayerRepository;
import com.beta.fantasytm.data.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/rankings")
public class RankingsController {

    TeamRepository teamRepo;
    PlayerRepository playerRepo;

    @Autowired
    public RankingsController(TeamRepository teamRepo, PlayerRepository playerRepo) {
        this.teamRepo = teamRepo;
        this.playerRepo = playerRepo;
    }

    @GetMapping
    public String showRankings(Model model) {
        ShowTeamsForm showTeamsForm = new ShowTeamsForm();

        showTeamsForm.getTeams().addAll((Collection<? extends Team>) teamRepo.findAll());

        model.addAttribute("form", showTeamsForm);

        return "rankings";
    }
}
