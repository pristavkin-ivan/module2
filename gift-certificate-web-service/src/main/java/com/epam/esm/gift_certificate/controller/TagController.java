package com.epam.esm.gift_certificate.controller;

import com.epam.esm.gift_certificate.entity.Tag;
import com.epam.esm.gift_certificate.service.api.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping(value = "/tags", produces = "application/json", consumes = "application/json")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @ResponseBody
    public List<Tag> getTags() {
        return tagService.readAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Tag getTag(@PathVariable("id") int id) {
        return tagService.read(id);
    }

    @PostMapping()
    @ResponseBody
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.create(tag);
    }

    @DeleteMapping ("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable("id") int id) {
        tagService.delete(id);
    }
}
