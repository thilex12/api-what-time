package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.Entity.Tag;
import fr.isencaen.api_what_time.repository.TagRepository;
import fr.isencaen.api_what_time.service.Model.CreateTagModel;
import fr.isencaen.api_what_time.service.Model.TagModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;


    public List<TagModel> getTags() {
        return tagRepository.findAll().stream().map(TagModel::of).toList();
    }

    public TagModel getTagById(int id) {
        Tag tag = tagRepository.findById(id).orElseThrow();
        return TagModel.of(tag);
    }

    @Transactional
    public TagModel createTag(CreateTagModel tagModel) {
        return TagModel.of(tagRepository.save(
                new Tag(tagModel.name())
        ));
    }

    @Transactional
    public TagModel updateTag(int id, CreateTagModel tagModel) {
        Tag tag = tagRepository.findById(id).orElseThrow();
        tag.setName(tagModel.name());
        return TagModel.of(tag);
    }

    @Transactional
    public void deleteTag(int id) {
        Tag tag = tagRepository.findById(id).orElseThrow();
        // Remove tag from events
        // A FAIRE AVEC LE MERGE DE ARNAUD
//        if (tag.getEvents() != null) {
//            for (Event event : tag.getEvents()) {
//                if (event.getTags() != null) {
//                    event.getTags().remove(tag);
//                    // Sauvegarder l'event modifié
//                    // eventRepository.save(event); // à décommenter si eventRepository est accessible
//                }
//            }
//        }
//        // Remove tag from accounts
//        if (tag.getAccounts() != null) {
//            for (Account account : tag.getAccounts()) {
//                if (account.getTags() != null) {
//                    account.getTags().remove(tag);
//                    // Sauvegarder l'account modifié
//                    // accountRepository.save(account); // à décommenter si accountRepository est accessible
//                }
//            }
//        }
        tagRepository.delete(tag);
    }
}
