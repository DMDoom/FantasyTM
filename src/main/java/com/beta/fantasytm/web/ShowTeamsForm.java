package com.beta.fantasytm.web;

import com.beta.fantasytm.Team;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShowTeamsForm {

    List<Team> teams;

    public ShowTeamsForm() {
        this.teams = new ArrayList<>();
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }
}
