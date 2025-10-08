package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.NotifRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import fr.isencaen.api_what_time.service.model.NotifModel;

import java.util.List;

@Service
public class NotifService {
    private final NotifRepository notifRepository;

    public NotifService(NotifRepository notifRepository) {
        this.notifRepository = notifRepository;
    }


    public List<NotifModel> getNotif(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        if (auth.isAuthenticated() && auth.getPrincipal())
            return notifRepository.findAllByUserIdAndRead(auth.get, 0)
                .stream().map(NotifModel::of).toList();
    }
}
