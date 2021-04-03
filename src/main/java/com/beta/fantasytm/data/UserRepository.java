package com.beta.fantasytm.data;

import com.beta.fantasytm.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    //User findByUsername(String username);
    List<User> findAllByUsername(String username);

}
