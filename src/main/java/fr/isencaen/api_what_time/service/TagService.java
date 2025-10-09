package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.controller.Dto.CreateTagDto;
import fr.isencaen.api_what_time.repository.Entity.Tag;
import fr.isencaen.api_what_time.repository.TagRepository;
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
    public TagModel createTag(CreateTagDto tagDto) {
//        Tag tag = new Tag(tagDto.name());
        return TagModel.of(tagRepository.save(
                new Tag(tagDto.name())
        ));
    }

}
