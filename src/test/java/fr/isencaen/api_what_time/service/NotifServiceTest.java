package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.repository.Entity.Event;
import fr.isencaen.api_what_time.repository.Entity.Notif;
import fr.isencaen.api_what_time.repository.NotifRepository;
import fr.isencaen.api_what_time.service.Model.NotifModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)

public class NotifServiceTest {
    @InjectMocks
    private NotifService notifService;

    @Mock
    private NotifRepository notifRepository;

    @Test
    public void testGetNotif(){
        Account userAccount = new Account(1, "Toto", "NOMDEFAMILLE", "mail@gmail.com", "1234");
        Event evt = new Event(1, "Basket-ball", "Tournoi", LocalDateTime.now(), LocalDateTime.now(), null, true) ;
        Notif notifInitial = new Notif(1, evt, 1, evt.getCreationDate(), userAccount, false, false);
        Mockito.when(notifRepository.findById(1)).thenReturn(Optional.of(notifInitial));

        Notif notif = notifRepository.findById(1).orElseThrow();

        Assertions.assertFalse(notif.isArchive());

        notif.setRead(true);

        NotifModel expectedModel = new NotifModel(
                notifInitial.getIdNotif(),
                notifInitial.getIdEvent(),
                notifInitial.getModify(),
                true,
                notifInitial.getCreationDate()
        );
        Assertions.assertEquals(expectedModel, NotifModel.of(notif));
    }

    @Test
    public void testDeleteNotif() {
        Account userAccount = new Account(1, "Toto", "NOMDEFAMILLE", "mail@gmail.com", "1234");
        Event evt = new Event(1, "Basket-ball", "Tournoi", LocalDateTime.now(), LocalDateTime.now(), null, true) ;
        Notif notifInitial = new Notif(1, evt, 1, evt.getCreationDate(), userAccount, false, false);
        Mockito.when(notifRepository.findById(1)).thenReturn(Optional.of(notifInitial));

        Notif notif = notifRepository.findById(1).orElseThrow();

        Assertions.assertFalse(notif.isArchive());

        notif.setArchive(true);

        NotifModel expectedModel = new NotifModel(
                notifInitial.getIdNotif(),
                notifInitial.getIdEvent(),
                notifInitial.getModify(),
                notifInitial.isRead(),
                notifInitial.getCreationDate()
        );
        Assertions.assertEquals(expectedModel, NotifModel.of(notif));
    }
}
