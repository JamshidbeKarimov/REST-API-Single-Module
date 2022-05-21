package com.epam.esm.repository.gift_certificate;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCertificateRepository extends CrudRepository<GiftCertificateEntity, Long> {

    List<GiftCertificateEntity> getAllWithSearchAndTagName(
            String searchWord,
            String tagName,
            boolean doNameSort,
            boolean doDateSort,
            boolean isDescending
    );


}
