package com.beta.fantasytm.data;

import com.beta.fantasytm.Buff;
import com.beta.fantasytm.web.forms.BuffWrapper;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BuffRepository extends CrudRepository<BuffWrapper, Long> {

    // Throws error, fix
    //List<BuffWrapper> findBuffsByWalletId(Long id);

}
