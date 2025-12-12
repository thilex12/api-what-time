package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
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
    private boolean archived;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private List<Allow> allowedList;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private List<Inscription> inscriptions;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private List<FollowTag> followTags;

    private String role;

    //    org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: fr.isencaen.api_what_time.repository.Entity.Account.tags: could not initialize proxy - no Session
    public Account() {
        this.allowedList = new ArrayList<>();
        this.inscriptions = new ArrayList<>();
        this.followTags = new ArrayList<>();
        this.archived = false;
    }

    public Account(int id, String name, String surname, String mail, String pwd) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.pwd = pwd;
        this.allowedList = new ArrayList<>();
        this.inscriptions = new ArrayList<>();;
        this.followTags = new ArrayList<>();
        this.archived = false;
    }

    public Account(String name, String surname, String mail, String pwd) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.pwd = pwd;
        this.allowedList = new ArrayList<>();
        this.inscriptions = new ArrayList<>();
        this.followTags = new ArrayList<>();
        this.archived = false;
    }

    public Account(String name, String surname, String mail, String pwd, String role) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.pwd = pwd;
        this.allowedList = new ArrayList<>();
        this.inscriptions = new ArrayList<>();
        this.followTags = new ArrayList<>();
        this.archived = false;
        this.role = role;
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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean a) {
        this.archived = a;
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

    public List<FollowTag> getFollowTags() {
        return followTags;
    }

    public void setFollowTags(List<FollowTag> followTags) {
        // Plus besoin
    }
    public void addFollowTag(FollowTag followTag){
        this.followTags.add(followTag);
    }
    public void removeFollowTag(FollowTag followTags){
        this.followTags.remove(followTags);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
    }
    public void removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
    }

    //    public void addTag(Tag tag) {
//        if (this.tags == null) {
//            this.tags = new ArrayList<>();
//        }
//        if (!this.tags.contains(tag)) {
//            this.tags.add(tag);
//        } else {
//            this.tags.remove(tag);
//        }
//    }

//    public void removeTag(Tag tag) {
//        this.tags.remove(tag);
//    }

//    public void addInscription(Inscription inscription) {
//        this.inscriptions.add(inscription);
//    }
//
//    public void removeInscription(Inscription inscription) {
//        this.inscriptions.remove(inscription);
//    }
}
