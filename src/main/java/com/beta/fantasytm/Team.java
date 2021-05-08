package com.beta.fantasytm;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, message = "Must be at least 3 characters long")
    private String name;

    @OneToOne
    private User user;

    @OneToMany
    @Size(min = 5, message = "Team needs at least 5 players!")
    private List<Player> players;

    private Buff buff;

    private Long cost;

    private double points;

    public String toString() {
        String s = "";
        for (Player player : this.players) {
            s += player.getName() + ", ";
        }

        return s;
    }

    // Determine and update cost of the team
    public void calculateCost() {
        long sum = 0;
        for (Player player : players) {
            sum += player.getCost();
        }

        this.cost = sum;
    }

    // Determine and update total earned points of the team
    // MARKED FOR FIX
    public void updatePoints() {
        double sum = 0;
        double helper = 0;
        for (Player player : players) {
            // Times modifier
            // wacky implementation, fix later
            if (player.getPosition() == Position.CAPTAIN) {
                helper = player.getPoints() * Position.CAPTAIN.getModifier();
                sum += helper;
                helper = 0;
            // this is dumb
            } else if (player.getPosition() == Position.UNDERDOG) {
                helper = player.getPoints() * Position.UNDERDOG.getModifier();
                sum += helper;
                helper = 0;
            } else if (player.getPosition() == Position.REGULAR) {
                sum += player.getPoints();
            }
        }

        this.points = sum;
    }

    // Maybe void updatePointsWithUnderdogs() ?

}
