package ru.goncharov.phone_book.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.goncharov.phone_book.data.phone_book_data.PhoneEntryDataInterface;
import ru.goncharov.phone_book.dto.PhoneEntryDto;
import ru.goncharov.phone_book.entity.PhoneEntryEntity;
import ru.goncharov.phone_book.entity.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@NoArgsConstructor
public class PhoneEntryService {

    @Autowired
    private PhoneEntryDataInterface phoneEntryData;
    @Autowired
    private UserService userService;


    // Return phone entry list in dto
    public List<PhoneEntryDto> getAllDtosByUserId(int userId) {
        UserEntity userEntity = userService.get(userId);
        if (userEntity == null) {
            return null;
        }
        Collection<Integer> entryIds = userEntity.getPhoneEntryIds();
        return phoneEntryData.getAllDtos(entryIds);
    }

    // Get entry by id
    public PhoneEntryEntity get(int id) {
        return phoneEntryData.get(id);
    }


    // Get entry by phone number
    public PhoneEntryEntity get(String number) {
        return phoneEntryData.get(number);
    }

    // Create phone entry by params and user id in entryDto
    public boolean create(PhoneEntryDto entryDto) {
        UserEntity userEntity = userService.get(entryDto.getOwnerId());
        if (userEntity == null) {
            return false;
        }
        phoneEntryData.add(new PhoneEntryEntity(userEntity, entryDto));
        return true;
    }

    // Delete entry by id
    public PhoneEntryEntity delete (int id) {
        return phoneEntryData.delete(id);
    }

    // Edit entry by id in entryDto
    public PhoneEntryEntity edit(PhoneEntryDto entryDto) {
        return phoneEntryData.edit(entryDto);
    }
}
