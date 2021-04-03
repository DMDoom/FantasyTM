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
    @Size(min = 6, message = "Team is incomplete")
    private List<Player> players;

    private Long cost;

    // Update value of the team
    // Will be used to subtract from user's wallet';
    public void calculateCost() {
        long sum = 0;
        for (Player player : players) {
            sum += player.getCost();
        }

        this.cost = sum;
    }

}
