package com.beta.fantasytm;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private Long balance;

    public void subtractBalance(Long number) {
        this.balance = this.balance - number;
    }
}
