package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int id_owner;
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    private boolean visibility;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Allow> allowedAccountsList;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Inscription> inscriptionsList;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Tag> tags;


    public Event() {
    }

    public Event(int id_owner, String name, String description, LocalDateTime startDate, LocalDateTime endDate, Location location, boolean visibility) {
        this.id_owner = id_owner;
        this.name = name;
        this.description = description;
        this.creationDate = LocalDateTime.now();
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.visibility = visibility;
    }

    public Event(int id_owner, String name, String description, LocalDateTime creationDate, LocalDateTime startDate, LocalDateTime endDate, Location location, boolean visibility) {
        this.id_owner = id_owner;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.visibility = visibility;
    }

    public Event(int id, int id_owner, String name, String description, LocalDateTime creationDate, LocalDateTime startDate, LocalDateTime endDate, Location location, boolean visibility) {
        this.id = id;
        this.id_owner = id_owner;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.visibility = visibility;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public int getId_owner() {
        return id_owner;
    }

    public void setId_owner(int id_owner) {
        this.id_owner = id_owner;
    }

    public List<Allow> getAllowedAccountsList() {
        return allowedAccountsList;
    }

    public void setAllowedAccountsList(List<Allow> allowedAccountsList) {
        this.allowedAccountsList = allowedAccountsList;
    }

    public List<Inscription> getInscriptionsList() {
        return inscriptionsList;
    }

    public void setInscriptionsList(List<Inscription> inscriptionsList) {
        this.inscriptionsList = inscriptionsList;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id && visibility == event.visibility && Objects.equals(name, event.name) && Objects.equals(description, event.description) && Objects.equals(creationDate, event.creationDate) && Objects.equals(startDate, event.startDate) && Objects.equals(endDate, event.endDate) && Objects.equals(location, event.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, creationDate, startDate, endDate, location, visibility);
    }
}
