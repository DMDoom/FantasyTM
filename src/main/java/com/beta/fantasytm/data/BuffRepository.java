package com.beta.fantasytm.data;

import com.beta.fantasytm.Buff;
import org.springframework.data.repository.CrudRepository;

public interface BuffRepository extends CrudRepository<Buff, Long> {

    // Throws error, fix
    // List<Buff> findBuffsByWalletId(Long id);

}
