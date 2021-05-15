package com.beta.fantasytm;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private BuffType activeBuff;

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
    // Somehow adjust for active buffs
    // Maybe pass buff type
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

    // Issues with this:

    // Total player points get taken into account rather than previous step points
    // Player needs to have a field for recent points, and that's what should be taken into account
    // Take recent points, perform the below calculations, and then add to totalPoints of the player

    // ALSO, at the end of the season, in the terminal, there needs to be an option to update points of all underdogs
    // that reached playoffs by 1.5x

    // UNTESTED, TEST
    public void updatePoints(BuffType activeBuff) {
        double sum = 0;
        for (Player player : players) {
            if (player.getPosition() == Position.CAPTAIN) {
                if (activeBuff == BuffType.TRIPLE_CAPTAIN) {
                    sum += player.getRecentStepPoints() * BuffType.TRIPLE_CAPTAIN.getModifier();
                } else {
                    sum += player.getRecentStepPoints() * Position.CAPTAIN.getModifier();
                }
            } else if (player.getPosition() == Position.REGULAR) {
                if (activeBuff == BuffType.FIRE_WEEK) {
                    sum += player.getRecentStepPoints() * BuffType.FIRE_WEEK.getModifier();
                } else {
                    sum += player.getRecentStepPoints() * Position.REGULAR.getModifier();
                }
            } else if (player.getPosition() == Position.UNDERDOG) {
                if (activeBuff == BuffType.QUAD_UNDERDOG) {
                    sum += player.getRecentStepPoints() * BuffType.QUAD_UNDERDOG.getModifier();
                } else {
                    sum += player.getRecentStepPoints() * Position.UNDERDOG.getModifier();
                }
            }
        }

        this.points = sum;
    }

    // Maybe void updatePointsWithUnderdogs() ?

}
