package fr.isencaen.api_what_time.config;

import fr.isencaen.api_what_time.repository.AccountRepository;
import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.repository.Entity.Event;
import fr.isencaen.api_what_time.repository.EventRepository;
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
    AccountRepository accountRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Event event1 = new Event();
        event1.setName("Event 1");
        event1.setDescription("Description for Event 1");
        event1.setStartDate(LocalDateTime.of(2025, 11, 25, 18, 0));
        event1.setEndDate(LocalDateTime.of(2025, 11, 25, 20, 0));
        event1.setVisibility(true);
        eventRepository.save(event1);


        Event event2 = new Event();
        event2.setName("Event 2");
        event2.setDescription("Description for Event 2");
        event2.setStartDate(LocalDateTime.of(2025, 12, 1, 20, 0));
        event2.setEndDate(LocalDateTime.of(2025, 12, 1, 22, 0));
        event2.setVisibility(false);
        eventRepository.save(event2);


        Account account = new Account("John", "John", "mail@gmail.com", bCryptPasswordEncoder.encode("1234"));
        accountRepository.save(account);
    }
}
