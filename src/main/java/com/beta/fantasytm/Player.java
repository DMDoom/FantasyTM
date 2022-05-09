package com.beta.fantasytm;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
public class Player implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;
    private String org;
    private int cost;
    private int age;

    @NonNull
    private double points;

    private double recentStepPoints;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Override
    public boolean isNew() {
        return false;
    }

    // Update total points
    public void sumUpPoints() {
        this.points += recentStepPoints;
    }
}

