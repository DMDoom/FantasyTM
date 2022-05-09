package com.beta.fantasytm;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
public class Buff {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "BUFF_TYPE")
    @Enumerated(EnumType.STRING)
    private BuffType buff;

    String buffName;

    String buffDescription;

    public void setBuff(BuffType buff) {
        this.buff = buff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buff buff1 = (Buff) o;
        return buff == buff1.buff;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buff);
    }
}