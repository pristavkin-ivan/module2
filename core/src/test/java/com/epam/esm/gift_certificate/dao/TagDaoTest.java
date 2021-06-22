package com.epam.esm.gift_certificate.dao;

import com.epam.esm.gift_certificate.config.TestContextConfig;
import com.epam.esm.gift_certificate.dao.api.TagDao;
import com.epam.esm.gift_certificate.exception.NoSuchTagException;
import com.epam.esm.gift_certificate.model.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestContextConfig.class)
public class TagDaoTest {

    private final TagDao<Tag> tagDao;
    private static final Tag TAG = new Tag(14, "taginator");

    @Autowired
    public TagDaoTest(TagDao<Tag> tagDao) {
        this.tagDao = tagDao;
    }

    @Test
    public void getAllTest() {
        assertNotNull(tagDao.getAll());
    }

    @Test
    public void getByIdTest() {
        Assertions.assertDoesNotThrow(() -> tagDao.get(1));
    }

    @Test
    public void getByNameTest() {
        Assertions.assertDoesNotThrow(() -> tagDao.getTagByName("tagster"));
    }

    @Test
    public void deleteByIdTest() throws NoSuchTagException {
        tagDao.delete(24);
        Assertions.assertThrows(NoSuchTagException.class, () -> tagDao.get(24));
    }

    @Test
    public void createTest() {
        Assertions.assertDoesNotThrow(() -> tagDao.create(TAG));
    }

}
