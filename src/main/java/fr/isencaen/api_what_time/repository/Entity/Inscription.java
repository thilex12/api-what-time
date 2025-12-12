package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inscription")
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_account", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id")
    private Event event;

    public Inscription() {
    }

    public Inscription(Account account, Event event) {
        this.account = account;
        this.event = event;
        account.addInscription(this);
    }

//    public Insription(int id, Account account, Event event) {
//        this.id = id;
//        this.account = account;
//        this.event = event;
//    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
