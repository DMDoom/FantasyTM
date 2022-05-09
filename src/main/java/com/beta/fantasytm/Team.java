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

    @ManyToMany
    @Size(min = 5, message = "Team needs at least 5 players!")
    private List<Player> players;

    @OneToOne
    private Buff activeBuff;

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

    public void setActiveBuff(Buff newActiveBuff) {
        this.activeBuff = newActiveBuff;
    }

    // Update points of the team taking into account player positions
    public void updatePoints() {
        double sum = 0;

        for (Player player : players) {
            if (player.getPosition() == Position.REGULAR) {
                if (this.activeBuff.getBuff() == BuffType.FIRE_WEEK) {
                    sum += player.getRecentStepPoints() * BuffType.FIRE_WEEK.getModifier();
                } else {
                    sum += player.getRecentStepPoints() * Position.REGULAR.getModifier();
                }
            } else if (player.getPosition() == Position.CAPTAIN) {
                if (this.activeBuff.getBuff() == BuffType.TRIPLE_CAPTAIN) {
                    sum += player.getRecentStepPoints() * BuffType.TRIPLE_CAPTAIN.getModifier();
                } else {
                    sum += player.getRecentStepPoints() * Position.CAPTAIN.getModifier();
                }
            } else if (player.getPosition() == Position.UNDERDOG) {
                if (this.activeBuff.getBuff() == BuffType.QUAD_UNDERDOG) {
                    sum += player.getRecentStepPoints() * BuffType.QUAD_UNDERDOG.getModifier();
                } else {
                    sum += player.getRecentStepPoints() * Position.UNDERDOG.getModifier();
                }
            }
        }

        this.points += sum;
    }

    // void updatePointsWithUnderdogs() method required
    // would be conditionally fired at the end of the season and would update the teams with underdog scores
    // underdog players who reached playoffs would have a global buff applied to all of their scored points
}
