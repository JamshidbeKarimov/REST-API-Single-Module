package com.epam.esm.service.tag;

import com.epam.esm.dto.BaseResponse;
import com.epam.esm.dto.reponse.TagGetResponse;
import com.epam.esm.dto.request.TagPostRequest;
import com.epam.esm.exception.tag.InvalidTagException;
import com.epam.esm.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService extends BaseService<TagPostRequest, TagGetResponse> {
    BaseResponse<List<TagGetResponse>> getAll();

    default void validateTag(TagPostRequest createTag){
        if(createTag.getName() == null)
            throw new InvalidTagException("Tag name cannot be empty");
    }
}
