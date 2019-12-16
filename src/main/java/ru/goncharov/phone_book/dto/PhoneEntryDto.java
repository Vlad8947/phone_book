package ru.goncharov.phone_book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.goncharov.phone_book.entity.PhoneEntryEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneEntryDto {

    private int id;
    private int ownerId;
    private String phoneName;
    private String phoneNumber;


    public PhoneEntryDto(PhoneEntryEntity entryEntity) {
        id = entryEntity.getId();
        ownerId = entryEntity.getOwner().getId();
        phoneName = entryEntity.getPhoneName();
        phoneNumber = entryEntity.getPhoneNumber();
    }
}
