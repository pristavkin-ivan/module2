package com.epam.esm.gift_certificate.dao.api;

public interface GiftsTagsDao {
    void createAssociation(int gift_id, int tag_id);
    boolean isAssociationExists(int gift_id, int tag_id);
    void deleteAllAssociationsById(int gift_id);
}
