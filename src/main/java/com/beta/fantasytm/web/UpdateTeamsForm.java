package com.beta.fantasytm.web;

import com.beta.fantasytm.Player;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Data
public class UpdateTeamsForm {

    List<Player> players;

    public UpdateTeamsForm() {
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
