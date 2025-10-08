package fr.isencaen.api_what_time.repository;

import fr.isencaen.api_what_time.repository.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    List<Account> findAll();
    List<Account> findByMail(String mail);
}
