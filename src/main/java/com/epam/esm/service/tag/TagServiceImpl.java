package com.epam.esm.service.tag;


import com.epam.esm.dto.BaseResponse;
import com.epam.esm.dto.reponse.TagGetResponse;
import com.epam.esm.dto.request.TagPostRequest;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.tag.TagAlreadyExistException;
import com.epam.esm.repository.tag.TagRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BaseResponse<TagGetResponse> create(TagPostRequest createTag){
        validateTag(createTag);
        TagEntity tagEntity = modelMapper.map(createTag, TagEntity.class);
        TagEntity createdTag = tagRepository.create(tagEntity);
        if(createdTag != null) {
            TagGetResponse response = modelMapper.map(createdTag, TagGetResponse.class);
            return new BaseResponse<>(201, "success", response);
        }
        throw new TagAlreadyExistException("tag with name: " + createdTag.getName() + "already exists");
    }


    @Override
    public BaseResponse<TagGetResponse> get(Long tagId) {
        TagEntity tag = tagRepository.findById(tagId).get();
        TagGetResponse tagGetResponse = modelMapper.map(tag, TagGetResponse.class);
        return new BaseResponse<>(200, "success", tagGetResponse);
    }

    @Override
    public BaseResponse delete(Long tagId) {
        tagRepository.delete(tagId);
        return new BaseResponse(204, "tag deleted", null);
        
//        throw new NoDataFoundException("no tag found with id: " + tagId);
    }


    @Override
    public BaseResponse<List<TagGetResponse>> getAll() {
        List<TagEntity> allTags = tagRepository.getAll(10, 20);
        List<TagGetResponse> tagGetResponses = modelMapper.map(allTags, new TypeToken<List<TagGetResponse>>() {}.getType());
        return new BaseResponse<>(200, "tag list", tagGetResponses);
    }
}