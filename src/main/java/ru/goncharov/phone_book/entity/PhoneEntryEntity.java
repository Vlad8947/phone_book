package ru.goncharov.phone_book.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.goncharov.phone_book.dto.PhoneEntryDto;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class PhoneEntryEntity {

    private int id;
    private UserEntity owner;
    private String phoneName;
    private String phoneNumber;

    public PhoneEntryEntity(UserEntity owner, String phoneName, String phoneNumber) {
        this.owner = owner;
        this.phoneName = phoneName;
        this.phoneNumber = phoneNumber;
    }

    public PhoneEntryEntity(UserEntity owner, PhoneEntryDto entryDto) {
        this.owner = owner;
        this.phoneName = entryDto.getPhoneName();
        this.phoneNumber = entryDto.getPhoneNumber();
    }

    public PhoneEntryEntity merge(PhoneEntryDto entryDto) {
        if(!entryDto.getPhoneName().isEmpty()) {
            phoneName = entryDto.getPhoneName();
        }
        if (!entryDto.getPhoneName().isEmpty()) {
            phoneNumber = entryDto.getPhoneNumber();
        }
        return this;
    }

}
