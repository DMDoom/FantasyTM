package com.beta.fantasytm;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class Player {

    @Id
    private final Long id;

    private final String name;
    private final String org;
    private final int cost;
    private final int age;

    @NonNull
    private double points; // figure out a way to assign this

    private final Position position;
}

