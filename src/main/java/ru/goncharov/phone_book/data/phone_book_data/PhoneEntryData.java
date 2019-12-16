package ru.goncharov.phone_book.data.phone_book_data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.goncharov.phone_book.dto.PhoneEntryDto;
import ru.goncharov.phone_book.entity.PhoneEntryEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@NoArgsConstructor
public class PhoneEntryData implements PhoneEntryDataInterface {

    // Counter for id
    private AtomicInteger lastId = new AtomicInteger(0);
    @Getter
    private Map<Integer, PhoneEntryEntity> entryMap =
            new ConcurrentSkipListMap<>();


    @Override
    public void add(PhoneEntryEntity entryEntity) {
        // Set id to entry
        entryEntity.setId(lastId.incrementAndGet());
        // Add entry id into list user id
        entryEntity.getOwner().getPhoneEntryIds().add(lastId.get());
        // Put entry into entry map
        entryMap.put(lastId.get(), entryEntity);
    }

    // Get all entries in Dto
    @Override
    public List<PhoneEntryDto> getAllDtos(Collection<Integer> ids) {
        // Result list
        List<PhoneEntryDto> entryDtos = new ArrayList<>();
        // Search id from "ids" and add into result list "entryDtos"
        ids.forEach((entryId) -> {
            PhoneEntryEntity phoneEntry = entryMap.get(entryId);
            if (phoneEntry == null) {
                return;
            }
            entryDtos.add(new PhoneEntryDto(phoneEntry));
        });
        return entryDtos;
    }

    // Get entry by id
    @Override
    public PhoneEntryEntity get(int id) {
        return entryMap.get(id);
    }

    // Get entry by phone number
    @Override
    public PhoneEntryEntity get(String number) {
        // All entries
        Collection<PhoneEntryEntity> entryEntities = entryMap.values();
        // Return a first entry with the given number
        for(PhoneEntryEntity entryEntity: entryEntities) {
            if (entryEntity.getPhoneNumber().equals(number)) {
                return entryEntity;
            }
        }
        // If entry was not found, return null
        return null;
    }

    // Delete entry by id
    @Override
    public PhoneEntryEntity delete(int entryId) {
        // Remove entry
        PhoneEntryEntity entryEntity = entryMap.remove(entryId);
        // If entry was not found, return null
        if (entryEntity == null) {
            return null;
        }
        // Remove entry id from user entry collection
        entryEntity.getOwner().getPhoneEntryIds().remove(entryId);
        return entryEntity;
    }

    @Override
    public PhoneEntryEntity edit(PhoneEntryDto entryDto) {
        // Get entry by entry id
        PhoneEntryEntity entryEntity = get(entryDto.getId());
        // If entry was not found
        if (entryEntity == null) {
            return null;
        }
        // Merge entry and return
        return entryEntity.merge(entryDto);
    }
}
