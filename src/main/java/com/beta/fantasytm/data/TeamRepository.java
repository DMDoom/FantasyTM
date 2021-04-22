package com.beta.fantasytm.data;

import com.beta.fantasytm.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {

    Team findByUserId(Long id); // testing

}
