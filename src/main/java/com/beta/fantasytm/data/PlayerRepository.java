package com.beta.fantasytm.data;

import com.beta.fantasytm.Player;
import com.beta.fantasytm.Position;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    List<Player> findByOrg(String org);
    List<Player> findByName(String name);
    List<Player> findByCost(Long cost);
    List<Player> findByAge(int age);
}
