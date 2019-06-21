package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Tag;
import com.telerikacademy.beertag.repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service("TagService")
public class TagServiceImpl implements Service<Tag, Integer> {

    private Repository<Tag, Integer> tagRepository;

    @Autowired
    public TagServiceImpl(Repository<Tag, Integer> tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag add(Tag tag) {
        List<Tag> tags = getAll().stream()
                .filter(beer1 -> beer1.getName().equals(tag.getName()))
                .collect(Collectors.toList());

        if (tags.size() > 0) {
            throw new IllegalArgumentException("Tag already exists");
        }
        return tagRepository.add(tag);
    }

    @Override
    public Tag get(Integer id) {
        Tag tag = tagRepository.get(id);
        if (tag == null)
            throw new IllegalArgumentException(String.format("Tag with id %d not found.", id));
        return tag;
    }

    @Override
    public Tag update(Integer id, Tag newTag) {

        Tag oldTag = get(id);
        return tagRepository.update(oldTag, newTag);
    }

    @Override
    public void remove(Integer id) {
        tagRepository.remove(get(id));
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.getAll();
    }
}
