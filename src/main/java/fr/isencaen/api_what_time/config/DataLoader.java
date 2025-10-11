package fr.isencaen.api_what_time.config;

import fr.isencaen.api_what_time.repository.*;
import fr.isencaen.api_what_time.repository.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Profile("!prod")
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    NotifRepository notifRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    AllowRepository allowRepository;

    @Autowired
    FollowRepository followRepository;

    @Autowired
    TagEventRepository tagEventRepository;

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
        event1.setVisibility(false);
        event1.setLocation(loc1);
        event1.setId_owner(1);
//        event1.setAllowedAccountsList(allowEvent1);
        eventRepository.save(event1);


        Event event2 = new Event();
        event2.setName("Event 2");
        event2.setDescription("Description for Event 2");
        event2.setStartDate(LocalDateTime.of(2025, 12, 1, 20, 0));
        event2.setEndDate(LocalDateTime.of(2025, 12, 1, 22, 0));
        event2.setVisibility(false);
        event2.setLocation(loc1);
        event2.setId_owner(2);
//        event2.setTags(tagRepository.findAll());
//        event2.setAllowedAccountsList(allowRepository.findAll());
        eventRepository.save(event2);

        TagEvent tagEvent = new TagEvent(tag1, event2);
        tagEventRepository.save(tagEvent);


        Event event3 = new Event();
        event3.setName("Event 3");
        event3.setDescription("Description for Event 2");
        event3.setStartDate(LocalDateTime.of(2025, 12, 1, 20, 0));
        event3.setEndDate(LocalDateTime.of(2025, 12, 1, 22, 0));
        event3.setVisibility(true);
        event3.setLocation(loc1);
        event3.setId_owner(1);
        event3.setArchived(true);
//        event3.setTags(tagRepository.findAll());
        eventRepository.save(event3);


        Account account = new Account("John", "John", "mail@gmail.com", bCryptPasswordEncoder.encode("1234"));
//        account.addTag(tag1);
        account.setRole("ROLE_USER");
        account.setTags(tagRepository.findAll());
        accountRepository.save(account);
        followRepository.save(new FollowTag(tag1, account));
        //account.setTags(tagRepository.findAll());


        Account account2 = new Account("Henri", "Poincaré", "henri.care@gmail.com", bCryptPasswordEncoder.encode("4321"));
//        account.addTag(tag1);
        accountRepository.save(account2);
        account2.setTags(tagRepository.findAll());

        Account accountAdmin = new Account("John", "Doe", "gmail@mail.com", bCryptPasswordEncoder.encode("1234"), "ROLE_ADMIN");
//        account.addTag(tag1);
        accountRepository.save(accountAdmin);
//        accountAdmin.setRole("ROLE_ADMIN");
        accountAdmin.setTags(tagRepository.findAll());


        Notif notif1 = new Notif(event1, 1, LocalDateTime.now(), false, true, account);
        notifRepository.save(notif1);

        Notif notif2 = new Notif(event2, 1, LocalDateTime.now(), false, false, account);
        notifRepository.save(notif2);

        Notif notif3 = new Notif(event1, 2, LocalDateTime.now(), false, false, account);
        notifRepository.save(notif3);

        Notif notif4 = new Notif(event2, 1, LocalDateTime.now(), false, false, account2);
        notifRepository.save(notif4);

        Notif notif5 = new Notif(event3, 1, LocalDateTime.now(), false, true, account2);
        notifRepository.save(notif5);

        Inscription inscription = new Inscription(account, event1);
        inscriptionRepository.save(inscription);

//        Inscription inscription2 = new Inscription(account, event2);
//        inscriptionRepository.save(inscription2);

//        Allow allow = new Allow(account, event2);
//        allowRepository.save(allow);

        // Ajout de user1 (account) dans la allowlist de event1
        Allow allowEvent1 = new Allow(account, event1);
        allowRepository.save(allowEvent1);


        Tag tag2 = new Tag("Test2");
        tagRepository.save(tag2);

    }
}
