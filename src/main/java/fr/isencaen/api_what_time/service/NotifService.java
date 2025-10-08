package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.NotifRepository;
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
        return notifRepository.findAllByUserIdAndRead(userId, read)
                .stream().map(NotifModel::of).toList();
    }
}
