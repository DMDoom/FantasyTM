package com.beta.fantasytm;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.List;

// AcessLevel.PRIVATE -> PRIVATE

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
//@RequiredArgsConstructor
public class Player implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    //@Basic(optional = false)
    //@Column(name = "id", unique = true, nullable = false)
    private Long id;

    private String name;
    private String org;
    private int cost;
    private int age;

    // Change points -> totalPoints
    // Add recentPoints
    // Make adding up to total points automatic

    @NonNull
    private double points;

    private double recentStepPoints;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Override
    public boolean isNew() {
        return false;
    }

    // Used to calculate total sum of points based on recent step points entry
    // Is called when updating from terminal
    // Untested
    public void sumUpPoints() {
        this.points += recentStepPoints;
    }
}

