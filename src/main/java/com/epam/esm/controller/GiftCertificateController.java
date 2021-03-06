package com.epam.esm.controller;

import com.epam.esm.dto.request.GiftCertificatePostRequest;
import com.epam.esm.service.gift_certificate.GiftCertificateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.Long;

@RestController
@RequestMapping("/api/gift_certificate")
@AllArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(
            @RequestBody GiftCertificatePostRequest createCertificate
            ){
        return ResponseEntity.status(201).body(giftCertificateService.create(createCertificate));
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(
            @RequestParam Long id
    ){
        return ResponseEntity.ok(giftCertificateService.get(id));
    }

    @RequestMapping(value = "/get_all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) boolean doNameSort,
            @RequestParam(required = false) boolean doDateSort,
            @RequestParam(required = false) boolean isDescending
    ){
        return ResponseEntity.ok(giftCertificateService.getAll(
               searchWord, tagName, doNameSort, doDateSort, isDescending
            ));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(
            @RequestParam Long id
    ){
        return ResponseEntity.ok(giftCertificateService.delete(id));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public ResponseEntity<?> update(
            @RequestBody GiftCertificatePostRequest update,
            @RequestParam(value = "id") Long certificateId
    ){
        return ResponseEntity.ok(giftCertificateService.update(update, certificateId));
    }

}
