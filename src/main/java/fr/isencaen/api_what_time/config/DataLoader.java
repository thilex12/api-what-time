package fr.isencaen.api_what_time.config;

import fr.isencaen.api_what_time.repository.*;
import fr.isencaen.api_what_time.repository.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    AllowRepository allowRepository;

    @Autowired
    InscriptionRepository inscriptionRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Location loc1 = new Location();
        loc1.setName("Salle1");
        locationRepository.save(loc1);

        Tag tag1 = new Tag("Beebo");
        tagRepository.save(tag1);


        Event event1 = new Event();
        event1.setName("Event 1");
        event1.setDescription("Description for Event 1");
        event1.setStartDate(LocalDateTime.of(2025, 11, 25, 18, 0));
        event1.setEndDate(LocalDateTime.of(2025, 11, 25, 20, 0));
        event1.setVisibility(true);
        event1.setLocation(loc1);
        event1.setId_owner(1);
        eventRepository.save(event1);


        Event event2 = new Event();
        event2.setName("Event 2");
        event2.setDescription("Description for Event 2");
        event2.setStartDate(LocalDateTime.of(2025, 12, 1, 20, 0));
        event2.setEndDate(LocalDateTime.of(2025, 12, 1, 22, 0));
        event2.setVisibility(false);
        event1.setId_owner(1);
        eventRepository.save(event2);


        Account account = new Account("John", "John", "mail@gmail.com", bCryptPasswordEncoder.encode("1234"));
        accountRepository.save(account);

        Inscription inscription = new Inscription(account, event1);
        inscriptionRepository.save(inscription);

        Inscription inscription2 = new Inscription(account, event2);
        inscriptionRepository.save(inscription2);

        Allow allow = new Allow(account, event2);
        allowRepository.save(allow);

    }
}
