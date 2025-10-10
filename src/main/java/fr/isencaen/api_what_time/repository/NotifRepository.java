package fr.isencaen.api_what_time.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import fr.isencaen.api_what_time.repository.Entity.Notif;

import java.util.List;


@Repository
public interface NotifRepository extends JpaRepository<Notif, Integer>{
    List<Notif> findAllByAccountIdAndArchive(int idUser, boolean archive);
}
