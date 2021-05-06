package com.beta.fantasytm;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;

@Data
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany
    @Enumerated(EnumType.ORDINAL)
    private ArrayList<Buff> buffs;

    private Long balance;

    public void subtractBalance(Long number) {
        this.balance = this.balance - number;
    }
}
