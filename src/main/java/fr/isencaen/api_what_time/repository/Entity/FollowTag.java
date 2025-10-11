package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.*;

public class FollowTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_tag", referencedColumnName = "id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "id_account", referencedColumnName = "id")
    private Account account;

    public FollowTag() {}

    public FollowTag(Tag tag, Account account) {
        this.tag = tag;
        this.account = account;
        account.addTag(tag);
    }

    public void removeFollow(){
        this.account.removeTag(this.tag);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
