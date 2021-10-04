package com.beta.fantasytm.data;

import com.beta.fantasytm.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Long> {

    Team findByUserId(Long id); // testing
    List<Team> findByOrderByPointsDesc();

}
