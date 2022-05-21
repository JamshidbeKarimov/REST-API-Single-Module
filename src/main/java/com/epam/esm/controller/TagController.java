package com.epam.esm.controller;


import com.epam.esm.dto.BaseResponse;
import com.epam.esm.dto.reponse.TagGetResponse;
import com.epam.esm.dto.request.TagPostRequest;
import com.epam.esm.service.tag.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@AllArgsConstructor
public class TagController {

    private final TagService tagService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse<TagGetResponse>> create(
            @RequestBody TagPostRequest tag
    ){
        return ResponseEntity.status(201).body(tagService.create(tag));
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<BaseResponse<TagGetResponse>> get(
            @RequestParam Long id
    ){
        return ResponseEntity.ok(tagService.get(id));
    }

    @RequestMapping(value = "/get_all", method = RequestMethod.GET)
    public ResponseEntity<BaseResponse<List<TagGetResponse>>> getAll(){
        return ResponseEntity.ok(tagService.getAll());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<BaseResponse> delete(
            @RequestParam Long id
    ){
        return ResponseEntity.status(200).body(tagService.delete(id));
    }
}
