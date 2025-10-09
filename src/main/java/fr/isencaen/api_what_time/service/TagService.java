package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.Entity.Tag;
import fr.isencaen.api_what_time.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public Tag getTagById(int id) {
        return tagRepository.findById(id).orElseThrow();
    }

}
