package com.beta.fantasytm.web.forms;

import com.beta.fantasytm.Buff;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Needs to cooperate with BuffRepository

@Data
@Entity
public class BuffWrapper {

    // Pre-persist it much like Player and automatically add 3 buffs to each wallet
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Buff buff;

    public void setBuff(Buff buff) {
        this.buff = buff;
    }
}
