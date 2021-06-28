package com.epam.esm.gift_certificate.dao.api;

/**
 * Dao for operating with GiftCertificates and Tags many-to-many relationships
 */
public interface GiftsTagsDao {

    /**
     * Saves many-to-many relationships in db
     * @param giftId
     * @param tagId
     */
    void createAssociation(int giftId, int tagId);

    /**
     * Deletes many-to-many relationships in db
     * @param giftId
     * @param tagId
     */
    void deleteAllAssociationsById(int giftId, int tagId);
}
