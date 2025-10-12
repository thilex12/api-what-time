package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.Entity.TagEvent;
import fr.isencaen.api_what_time.repository.EventRepository;
import fr.isencaen.api_what_time.repository.TagEventRepository;
import fr.isencaen.api_what_time.repository.TagRepository;
import fr.isencaen.api_what_time.service.Model.TagEventModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagEventService {

    @Autowired
    TagEventRepository tagEventRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    TagRepository tagRepository;

    @Transactional
    public TagEventModel createTagEvent(TagEventModel tagEventModel) {
        // Création d'un TagEvent à partir des identifiants uniquement
        return TagEventModel.of(tagEventRepository.save(
                new TagEvent(
                        tagRepository.findById(tagEventModel.tagId()).orElseThrow(),
                        eventRepository.findById(tagEventModel.eventId()).orElseThrow()
                )
        ));
    }

    @Transactional
    public void deleteTagEvent(int id) {
        tagEventRepository.deleteById(id);
    }
}
