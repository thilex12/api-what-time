package fr.isencaen.api_what_time.repository;

import fr.isencaen.api_what_time.repository.Entity.Notif;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotifRepository extends JpaRepository<Notif, Integer> {
    //    Page<Notif> findAllByAccountIdAndArchive(int idUser, boolean archive, Pageable pageable);
    Page<Notif> findAllByAccountIdAndArchive(int idUser, boolean archive, Pageable pageable);
}
