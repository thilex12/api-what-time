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
    private String pwd;

    public Account(){}
    public Account(int id, String name, String surname, String mail, String pwd){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.pwd = pwd;
    }
    public Account(String name, String surname, String mail, String pwd){
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.pwd = pwd;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public String getSurname(){return surname;}
    public String getMail(){return mail;}
    public String getPwd(){return pwd;}

    public void setName(String name){
        this.name = name;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    public void setMail(String mail){
        this.mail = mail;
    }
    public void setPwd(String pwd){
        this.pwd = pwd;
    }
}
