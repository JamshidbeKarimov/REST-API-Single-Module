package com.epam.esm.repository.gift_certificate;

import com.epam.esm.entity.GiftCertificateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GiftCertificateEntity create(GiftCertificateEntity certificate) {
        entityManager.persist(certificate);
        if(certificate.getId() != null)
            return certificate;
        return null;
    }

    @Override
    public List<GiftCertificateEntity> getAll(int limit, int offset) {
        return entityManager
                .createQuery("select gc from GiftCertificateEntity gc", GiftCertificateEntity.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Optional<GiftCertificateEntity> findById(Long id) {
        GiftCertificateEntity certificateEntity = entityManager.find(GiftCertificateEntity.class, id);
        return Optional.of(certificateEntity);
    }

    @Override
    public GiftCertificateEntity update(GiftCertificateEntity certificateUpdate) {
        GiftCertificateEntity updated = entityManager.merge(certificateUpdate);
        return updated;
    }

    @Override
    public int delete(Long id) {
        return entityManager
                .createQuery("delete from GiftCertificateEntity where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<GiftCertificateEntity> getAllWithSearchAndTagName(String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending) {
        String query ="select * from get_all(:search_word, :tag_name, :do_name_sort, :do_date_sort, :is_descending";
        return entityManager.createNativeQuery(query, GiftCertificateEntity.class)
                .setParameter("search_word", searchWord)
                .setParameter("tag_name", tagName)
                .setParameter("do_name_sort", doNameSort)
                .setParameter("do_date_sort", doDateSort)
                .setParameter("is_descending", isDescending)
                .getResultList();
    }
}
