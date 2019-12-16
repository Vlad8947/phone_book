package ru.goncharov.phone_book.data.phone_book_data;

import org.junit.Before;
import org.junit.Test;
import ru.goncharov.phone_book.dto.PhoneEntryDto;
import ru.goncharov.phone_book.entity.PhoneEntryEntity;
import ru.goncharov.phone_book.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PhoneEntryDataTest {

    private PhoneEntryData entryData;

    @Before
    public void before() {
        entryData = new PhoneEntryData();
    }

    private List<PhoneEntryEntity> getEntries() {
        List<PhoneEntryEntity> entries = new ArrayList<>();

        PhoneEntryEntity entryEntity1 = new PhoneEntryEntity();
        entryEntity1.setPhoneName("Albert");
        entryEntity1.setPhoneNumber("890");
        entryEntity1.setOwner(new UserEntity());
        entries.add(entryEntity1);

        PhoneEntryEntity entryEntity2 = new PhoneEntryEntity();
        entryEntity2.setPhoneName("Larisa");
        entryEntity2.setPhoneNumber("654");
        entryEntity2.setOwner(new UserEntity());
        entries.add(entryEntity2);

        return entries;
    }

    @Test
    public void addTest() {
        int id = 0;
        List<PhoneEntryEntity> entries = getEntries();

        PhoneEntryEntity actEntryEntity1 = entries.get(id++);
        actEntryEntity1.setId(id);
        entryData.add(actEntryEntity1);

        PhoneEntryEntity actEntryEntity2 = entries.get(id++);
        actEntryEntity2.setId(id);
        entryData.add(actEntryEntity2);

        // Assert actEntryEntity1
        int actId = 1;
        assertEntryOnAddTest(actId, actEntryEntity1);

        // Assert actEntryEntity2
        actId = 2;
        assertEntryOnAddTest(actId, actEntryEntity2);
    }

    private void assertEntryOnAddTest(int actId, PhoneEntryEntity actEntryEntity) {
        PhoneEntryEntity expEntryEntity = entryData.getEntryMap().get(actId);
        // Entry was found
        assertNotNull("Entry with id " + actId + " was not found", expEntryEntity);
        // Equals id
        assertEquals("Ids aren't equals", expEntryEntity.getId(), actId);
        // Equals entry params
        assertEquals("Entry params isn't equals", expEntryEntity, actEntryEntity);
        // Added id into owner
        int actEntryIdsSize = 1;
        int expEntryIdsSize = expEntryEntity.getOwner().getPhoneEntryIds().size();
        assertEquals("Id was not added", expEntryIdsSize, actEntryIdsSize);
        // Owner are not contain id
        boolean containId = expEntryEntity.getOwner().getPhoneEntryIds().contains(actId);
        assertTrue("Owner is not contain id", containId);
    }

    private void assertEntry(PhoneEntryEntity expEntryEntity, PhoneEntryEntity actEntryEntity) {
        // Entry was found
        assertNotNull("Entry with id " + actEntryEntity.getId() + " was not found", expEntryEntity);
        // Equals id
        assertEquals("Ids aren't equals", expEntryEntity.getId(), actEntryEntity.getId());
        // Equals entry params
        assertEquals("Entry params isn't equals", expEntryEntity, actEntryEntity);
    }

    @Test
    public void getAllDtosTest() {
        List<Integer> ids = new ArrayList<>();
        List<PhoneEntryEntity> entries = getEntries();
        int id = 0;

        PhoneEntryEntity actEntryEntity1 = entries.get(id++);
        ids.add(id);
        actEntryEntity1.setId(id);
        entryData.add(actEntryEntity1);

        PhoneEntryEntity actEntryEntity2 = entries.get(id++);
        ids.add(id);
        actEntryEntity2.setId(id);
        entryData.add(actEntryEntity2);

        List<PhoneEntryDto> dtos = entryData.getAllDtos(ids);
        //Dtos is empty
        boolean empty = dtos.isEmpty();
        assertFalse("Dto list is empty", empty);
        // Assert list size
        assertEquals("List sizes are not equal", dtos.size(), ids.size());
        
        // Assert entry 1
        int index = 0;
        PhoneEntryDto expEntryDto = dtos.get(index++);
        assertNotNull("Dto was not found", expEntryDto);
        assertEntityAndDto(actEntryEntity1, expEntryDto);
        // Assert entry 2
        expEntryDto = dtos.get(index);
        assertNotNull("Dto was not found", expEntryDto);
        assertEntityAndDto(actEntryEntity2, expEntryDto);

    }

    private void assertEntityAndDto(PhoneEntryEntity entryEntity, PhoneEntryDto entryDto) {
        // id
        assertEquals("Ids aren't equal", entryEntity.getId(), entryDto.getId());
        // phone name
        assertEquals("Phone names aren't equal", entryEntity.getPhoneName(), entryDto.getPhoneName());
        // phone number
        assertEquals("Phone numbers aren't equal", entryEntity.getPhoneNumber(), entryDto.getPhoneNumber());
    }

    @Test
    public void getByIdTest() {
        // Initializing the begin data
        int id = 0;
        List<PhoneEntryEntity> entries = getEntries();

        PhoneEntryEntity actEntryEntity = entries.get(id++);
        actEntryEntity.setId(id);
        entryData.getEntryMap().put(id, actEntryEntity);

        // Get existing entity
        PhoneEntryEntity expEntryEntity = entryData.get(id);
        assertNotNull("Entry was not found", expEntryEntity);
        assertEntry(expEntryEntity, actEntryEntity);

        // Get not existing entity
        expEntryEntity = entryData.get(3);
        assertNull("Not existing entity was received", expEntryEntity);
    }

    @Test
    public void getByNumberTest() {
        // Initializing the begin data
        int id = 1;
        int index = 0;
        List<PhoneEntryEntity> entries = getEntries();
        String actNumber = "789";
        String notExNumber = "123";

        PhoneEntryEntity actEntryEntity = entries.get(index);
        actEntryEntity.setId(id);
        actEntryEntity.setPhoneNumber(actNumber);
        entryData.getEntryMap().put(id, actEntryEntity);

        // Get existing entity
        PhoneEntryEntity expEntryEntity = entryData.get(actNumber);
        assertNotNull("Entry was not found", expEntryEntity);
        assertEntry(expEntryEntity, actEntryEntity);

        // Get not existing entity
        expEntryEntity = entryData.get(notExNumber);
        assertNull("Not existing entity was received", expEntryEntity);
    }

    @Test
    public void deleteTest() {
        // Initializing the begin data
        int id = 1;
        int index = 0;
        List<PhoneEntryEntity> entries = getEntries();

        PhoneEntryEntity actEntryEntity = entries.get(index);
        actEntryEntity.setId(id);
        entryData.getEntryMap().put(id, actEntryEntity);

        // Delete existing entity
        PhoneEntryEntity expEntryEntity = entryData.delete(id);
        assertNotNull("Entry was not found", expEntryEntity);
        assertEntry(expEntryEntity, actEntryEntity);
        boolean contain = entryData.getEntryMap().containsKey(id);
        assertFalse("Entry data contain the entity after delete", contain);

        // Delete not existing entity
        id = 3;
        expEntryEntity = entryData.delete(id);
        assertNull("Not existing entity was received", expEntryEntity);
    }

}