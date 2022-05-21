package com.epam.esm.service.gift_certificate;

import com.epam.esm.dto.BaseResponse;
import com.epam.esm.dto.reponse.GiftCertificateGetResponse;
import com.epam.esm.dto.request.GiftCertificatePostRequest;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.repository.gift_certificate.GiftCertificateRepository;
import com.epam.esm.repository.tag.TagRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService{
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository, ModelMapper modelMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BaseResponse<GiftCertificateGetResponse> create(GiftCertificatePostRequest createCertificate) {
        validateCertificate(createCertificate);
        List<TagEntity> tagEntities = createCertificate.getTagEntities();
        GiftCertificateEntity certificateEntity = modelMapper.map(createCertificate, GiftCertificateEntity.class);
        if(createCertificate.getTagEntities() != null && certificateEntity.getTagEntities().size() != 0)
            certificateEntity.setTagEntities(createTags(tagEntities));
        GiftCertificateEntity saved = giftCertificateRepository.create(certificateEntity);
        GiftCertificateGetResponse response = modelMapper.map(saved, GiftCertificateGetResponse.class);
        return new BaseResponse<>(201, "certificate created", response);
    }

    @Override
    public BaseResponse<GiftCertificateGetResponse> get(Long certificateId) {
        Optional<GiftCertificateEntity> certificate = giftCertificateRepository.findById(certificateId);

        if(certificate.isPresent()){
            GiftCertificateGetResponse certificateDto = modelMapper.map(certificate.get(), GiftCertificateGetResponse.class);
            return new BaseResponse<>(200, "certificate retrieved", certificateDto);
        }
        throw new NoDataFoundException("no certificate found with id: " + certificateId);
    }

    @Override
    public BaseResponse delete(Long certificateId) {
        int delete = giftCertificateRepository.delete(certificateId);
        if(delete == 1)
            return new BaseResponse<>(204, "certificate deleted", null);
        throw new NoDataFoundException("no certificate to delete with id: " + certificateId);
    }

    @Override
    public BaseResponse<List<GiftCertificateGetResponse>> getAll(
            String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending
    ) {
        if(tagName == null)
            tagName = "";
        if(searchWord == null)
            searchWord = "";
        List<GiftCertificateEntity> certificateEntities = giftCertificateRepository.getAllWithSearchAndTagName(
                searchWord, tagName, doNameSort, doDateSort, isDescending);
        if(certificateEntities.size() == 0)
            throw new NoDataFoundException("no matching gift certificate found");
        List<GiftCertificateGetResponse> certificateDtos
                = modelMapper.map(certificateEntities, new TypeToken<List<GiftCertificateGetResponse>>() {}.getType());
        return new BaseResponse<>(200, "certificates", certificateDtos);
    }

    @Override
    public BaseResponse<GiftCertificateGetResponse> update(GiftCertificatePostRequest update, Long certificateId) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        Optional<GiftCertificateEntity> old = giftCertificateRepository.findById(certificateId);
        if(old.isEmpty())
            throw new NoDataFoundException("certificate with id: " + certificateId + " not found");
        GiftCertificateEntity certificate = old.get();
        List<TagEntity> tagEntities = certificate.getTagEntities();
        modelMapper.map(update, certificate);
        if(update.getTagEntities() != null && update.getTagEntities().size() != 0) {
            tagEntities.addAll(createTags(update.getTagEntities()));
        }
        certificate.setTagEntities(tagEntities);
        GiftCertificateEntity updated = giftCertificateRepository.update(certificate);
        GiftCertificateGetResponse response = modelMapper.map(updated, GiftCertificateGetResponse.class);
        return new BaseResponse<>(200, "certificate updated", response);
    }


    private List<TagEntity> createTags(List<TagEntity> tagEntities){
        List<TagEntity> tagEntityList = new ArrayList<>();
        tagEntities.forEach(tag -> {
            TagEntity byName = tagRepository.findByName(tag.getName());
            if(byName != null){
                tagEntityList.add(byName);
            }else{
                tagEntityList.add(tagRepository.create(tag));
            }
        });
        return tagEntityList;
    }
}
