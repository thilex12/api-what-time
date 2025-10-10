package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.repository.Entity.Event;
import fr.isencaen.api_what_time.repository.Entity.Notif;
import fr.isencaen.api_what_time.repository.NotifRepository;
import fr.isencaen.api_what_time.service.Model.AccountPrincipal;
import fr.isencaen.api_what_time.service.Model.EventModel;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import fr.isencaen.api_what_time.service.Model.NotifModel;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Transactional
    public NotifModel getNotif(int idNotif){
        try {
            Notif notif = notifRepository.findById(idNotif).orElseThrow();
            if (notif.isArchive()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deleted notif");
            }
            notif.setRead(true);
            return NotifModel.of(notif);
        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notif not found");
        }
    }

    @Transactional
    public NotifModel deleteNotif(int idNotif){
        try {
            Notif notif = notifRepository.findById(idNotif).orElseThrow();
            if (notif.isArchive()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deleted notif");
            }
            notif.setArchive(true);
            return NotifModel.of(notif);
        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deleted notif");
        }
    }
}
