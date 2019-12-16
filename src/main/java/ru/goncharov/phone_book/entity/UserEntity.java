package ru.goncharov.phone_book.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.goncharov.phone_book.dto.UserDto;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;


@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id","phoneEntryIds"})
public class UserEntity {

    private int id;
    private String name;
    private Set<Integer> phoneEntryIds = new ConcurrentSkipListSet<>();

    public UserEntity(String name) {
        this.name = name;
    }

    public UserEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserEntity(UserDto userDto) {
        name = userDto.getName();
    }

    public UserEntity merge(UserDto newUserDto) {
        if (!newUserDto.getName().isEmpty()) {
            name = newUserDto.getName();
        }
        return this;
    }

    public void addPhoneEntryId(int id) {
        phoneEntryIds.add(id);
    }

    public void removePhoneEntryId(int id) {
        phoneEntryIds.remove(id);
    }

}
