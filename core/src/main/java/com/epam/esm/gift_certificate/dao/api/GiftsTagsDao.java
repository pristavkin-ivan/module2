package com.epam.esm.gift_certificate.dao.api;

/**
 * Dao for operating with GiftCertificates and Tags many-to-many relationships
 */
public interface GiftsTagsDao {

    /**
     * Saves many-to-many relationships in db
     * @param gift_id
     * @param tag_id
     */
    void createAssociation(int gift_id, int tag_id);

    /**
     * Deletes many-to-many relationships in db
     * @param gift_id
     * @param tag_id
     */
    void deleteAllAssociationsById(int gift_id, int tag_id);
}
