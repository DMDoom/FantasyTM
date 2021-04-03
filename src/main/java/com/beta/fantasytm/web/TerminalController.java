package com.beta.fantasytm.web;

import com.beta.fantasytm.Player;
import com.beta.fantasytm.Position;
import com.beta.fantasytm.data.PlayerRepository;
import com.beta.fantasytm.data.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        for (int i = 1; i <= 16; i++) {
            Optional<Player> placeholder = playerRepo.findById((long) i);
            updateForm.addPlayer(placeholder);
        }

        model.addAttribute("form", updateForm);

        return "terminal";
    }

    @PostMapping
    public String processUpdates(UpdateTeamsForm form) {
        log.info(form.toString());
        return "redirect:/login";
    }

    /*
    @ModelAttribute
    public void playerListModel(Model model) {
        // Adding 16 players to a list of players to display on the page
        List<Optional<Player>> players = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            Optional<Player> placeholder = playerRepo.findById((long) i);
            players.add(placeholder);
        }

        model.addAttribute("playersList", players);
    }
    */
}
