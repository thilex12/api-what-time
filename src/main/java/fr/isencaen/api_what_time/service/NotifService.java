package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.Entity.Notif;
import fr.isencaen.api_what_time.repository.NotifRepository;
import fr.isencaen.api_what_time.service.Model.AccountPrincipal;
import fr.isencaen.api_what_time.service.Model.EventModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import fr.isencaen.api_what_time.service.Model.NotifModel;

import java.util.List;

@Service
public class NotifService {
    private final NotifRepository notifRepository;

    public NotifService(NotifRepository notifRepository) {
        this.notifRepository = notifRepository;
    }


    public Page<NotifModel> getAllNotifs(
            Pageable pageable
    ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal)) return null;
        AccountPrincipal user = (AccountPrincipal) auth.getPrincipal();
            return notifRepository.findAllByAccountIdAndArchive(user.getAccount().getId(), false, pageable)
                    .map(NotifModel::of);
    }

    public NotifModel getNotif(int idNotif){
        Notif notif = notifRepository.findById(idNotif).orElseThrow();
        notif.setRead(true);
        return NotifModel.of(notif);
    }
}
