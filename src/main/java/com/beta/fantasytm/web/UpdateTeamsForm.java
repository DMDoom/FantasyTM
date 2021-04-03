package com.beta.fantasytm.web;

import com.beta.fantasytm.Player;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Data
public class UpdateTeamsForm {

    List<Optional<Player>> players;

    public void addPlayer(Optional<Player> player) {
        this.players.add(player);
    }
}
