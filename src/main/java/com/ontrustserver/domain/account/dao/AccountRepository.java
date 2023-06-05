package com.ontrustserver.domain.account.dao;

import com.ontrustserver.domain.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByEmailAndPassword(String email, String password);
}
