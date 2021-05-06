package com.beta.fantasytm.data;

import com.beta.fantasytm.Buff;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BuffRepository extends CrudRepository<Buff, Long> {

    List<Buff> findBuffsByWalletId(Long id);

}
