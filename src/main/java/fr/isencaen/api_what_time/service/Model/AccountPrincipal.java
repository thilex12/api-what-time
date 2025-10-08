package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.repository.Entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AccountPrincipal implements UserDetails {
    private Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new Role("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return account.getMdp();
    }

    @Override
    public String getUsername() {
        return account.getMail();
    }
}
