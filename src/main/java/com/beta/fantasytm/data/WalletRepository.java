package com.beta.fantasytm.data;

import com.beta.fantasytm.Wallet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WalletRepository extends CrudRepository<Wallet, Long> {

    Wallet findByUserId(Long id); //testing
}
