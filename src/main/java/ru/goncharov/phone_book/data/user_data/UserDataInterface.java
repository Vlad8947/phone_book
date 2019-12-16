package ru.goncharov.phone_book.data.user_data;

import ru.goncharov.phone_book.dto.UserDto;
import ru.goncharov.phone_book.entity.UserEntity;

public interface UserDataInterface {

    void add(UserEntity user);
    UserEntity get(int id);
    UserEntity get(String name);
    UserEntity delete(int id);
    UserEntity edit(UserDto userDto);

}
