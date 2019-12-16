package ru.goncharov.phone_book.data.phone_book_data;

import ru.goncharov.phone_book.dto.PhoneEntryDto;
import ru.goncharov.phone_book.entity.PhoneEntryEntity;

import java.util.Collection;
import java.util.List;

public interface PhoneEntryDataInterface {

    void add(PhoneEntryEntity entryEntity);
    PhoneEntryEntity get(int entryId);
    PhoneEntryEntity get(String number);
    List<PhoneEntryDto> getAllDtos(Collection<Integer> ids);
    PhoneEntryEntity delete(int entryId);
    PhoneEntryEntity edit(PhoneEntryDto entryDto);

}
