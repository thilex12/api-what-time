package fr.isencaen.api_what_time.controller;

import fr.isencaen.api_what_time.controller.Dto.TagDto;
import fr.isencaen.api_what_time.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("v1/tags")
    public List<TagDto> getTags() {
        return tagService.getTags().stream().map(TagDto::of).toList();
    }

    @GetMapping("v1/tags/{id}")
    public TagDto getTagById(
            @PathVariable int id
    ) {
        return TagDto.of(tagService.getTagById(id));
    }

    
}
