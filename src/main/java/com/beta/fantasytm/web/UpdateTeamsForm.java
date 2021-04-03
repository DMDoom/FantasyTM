package com.beta.fantasytm.web;

import com.beta.fantasytm.Player;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class UpdateTeamsForm {

    List<Player> players;

    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
