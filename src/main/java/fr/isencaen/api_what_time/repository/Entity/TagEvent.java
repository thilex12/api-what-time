package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tagEvent")
public class TagEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_tag", referencedColumnName = "id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id")
    private Event event;


    public TagEvent() {
    }

    public TagEvent(Tag tag, Event event) {
        this.tag = tag;
        this.event = event;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
