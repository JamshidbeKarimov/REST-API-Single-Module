package com.epam.esm.service.gift_certificate;

import com.epam.esm.dto.BaseResponse;
import com.epam.esm.dto.reponse.GiftCertificateGetResponse;
import com.epam.esm.dto.request.GiftCertificatePostRequest;
import com.epam.esm.exception.gift_certificate.InvalidCertificateException;
import com.epam.esm.service.BaseService;

import java.math.BigDecimal;
import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificatePostRequest, GiftCertificateGetResponse> {

    BaseResponse<List<GiftCertificateGetResponse>> getAll(
            String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending
    );

    BaseResponse<GiftCertificateGetResponse> update(GiftCertificatePostRequest update, Long certificateId);

    default void validateCertificate(GiftCertificatePostRequest certificate){
        if(certificate.getName() == null || certificate.getName().length() == 0)
            throw new InvalidCertificateException("Gift certificate name cannot be empty or null");
        if(certificate.getDuration() != null && certificate.getDuration() <= 0)
            throw  new InvalidCertificateException(
                    "duration should be greater than 0"
            );
        if(certificate.getPrice() != null && certificate.getPrice().compareTo(new BigDecimal(0)) == - 1)
            throw  new InvalidCertificateException(
                    "duration should be greater than 0"
            );
    }
}
