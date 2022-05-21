package com.epam.esm.repository.tag;


import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Long> {
    TagEntity findByName(String name);
}
