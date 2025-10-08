package fr.isencaen.api_what_time.service.Model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private String id;

    public Role(String id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return id;
    }


}
