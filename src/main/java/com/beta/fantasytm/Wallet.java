package com.beta.fantasytm;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany
    private List<Buff> buffs;

    private Long balance;

    public Wallet() {
        this.buffs = new ArrayList<>();
    }

    public List<Buff> getBuffs() {
        return this.buffs;
    }

    public void subtractBalance(Long number) {
        this.balance = this.balance - number;
    }
}
