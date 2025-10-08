package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String mail;
    private String pwd;

    @OneToMany
    private List<Allow> allowedList;
    @OneToMany
    private List<Inscription> inscriptions;
    @OneToMany
    private List<Tag> tags;


    public Account() {
    }

    public Account(int id, String name, String surname, String mail, String pwd) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.pwd = pwd;
    }

    public Account(String name, String surname, String mail, String pwd) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<Allow> getAllowedList() {
        return allowedList;
    }

    public void setAllowedList(List<Allow> allowedList) {
        this.allowedList = allowedList;
    }

    public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
