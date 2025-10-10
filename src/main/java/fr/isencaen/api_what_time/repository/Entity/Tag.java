package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    //    @OneToMany(fetch = FetchType.LAZY)
    @OneToMany
    private List<Event> events;

    //    @OneToMany(fetch = FetchType.LAZY)
    @OneToMany
    private List<Account> accounts;


    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return id == tag.id && Objects.equals(name, tag.name) && Objects.equals(events, tag.events) && Objects.equals(accounts, tag.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, events, accounts);
    }
}
