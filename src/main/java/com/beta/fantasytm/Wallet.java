package com.beta.fantasytm;

import com.beta.fantasytm.web.forms.BuffWrapper;
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

    // BUG HERE POSSIBLY
    @OneToMany
    private List<BuffWrapper> buffs;

    private Long balance;

    public void subtractBalance(Long number) {
        this.balance = this.balance - number;
    }
}
