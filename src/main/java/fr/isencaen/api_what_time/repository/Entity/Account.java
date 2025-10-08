package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String mail;
    private String mdp;

    public Account(){}
    public Account(int id, String name, String surname, String mail, String mdp){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.mdp = mdp;
    }
    public Account(String name, String surname, String mail, String mdp){
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.mdp = mdp;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public String getSurname(){return surname;}
    public String getMail(){return mail;}
    public String getMdp(){return mdp;}
}
