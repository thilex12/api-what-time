package fr.isencaen.api_what_time.controller;

import fr.isencaen.api_what_time.controller.Dto.CreateTagDto;
import fr.isencaen.api_what_time.controller.Dto.TagDto;
import fr.isencaen.api_what_time.service.Model.CreateTagModel;
import fr.isencaen.api_what_time.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("v1/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(
            @RequestBody CreateTagDto tag
    ) {
        return TagDto.of(tagService.createTag(CreateTagModel.of(tag)));
    }

    @PutMapping("v1/tags/{id}")
    public TagDto updateTag(
            @PathVariable int id,
            @RequestBody CreateTagDto tag
    ) {
        // For the sake of example, let's assume the TagService has an updateTag method
        return TagDto.of(tagService.updateTag(id, CreateTagModel.of(tag)));
    }
}
