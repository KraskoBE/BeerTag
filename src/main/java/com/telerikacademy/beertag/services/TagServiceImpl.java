package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Tag;
import com.telerikacademy.beertag.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("TagService")
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        return tagRepository.findById(id);
    }

    @Override
    public Optional<Tag> save(Tag tag) {
        Optional<Tag> oldTag = tagRepository.findByName(tag.getName());
        if(oldTag.isPresent() || tag.getName().isEmpty())
            return Optional.empty();
        return Optional.of(tagRepository.save(tag));
    }

    @Override
    public Optional<Tag> update(Integer id, Tag tag) {
        if (!findById(id).isPresent() || tagRepository.findByName(tag.getName()).isPresent())
            return Optional.empty();
        tag.setId(id);
        return Optional.of(tagRepository.save(tag));
    }

    @Override
    public void deleteById(Integer id) {
        tagRepository.deleteById(id);
    }


}










