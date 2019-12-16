package ru.goncharov.phone_book.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.goncharov.phone_book.data.user_data.UserData;
import ru.goncharov.phone_book.dto.UserDto;
import ru.goncharov.phone_book.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@NoArgsConstructor
public class UserService {

    @Autowired
    private UserData userData;

    // Return all Users
    public List<UserDto> getAll() {
        List<UserDto> userList = new ArrayList<>();
        Map<Integer, UserEntity> userMap = userData.getUserMap();
        userMap.forEach((id, userEntity) -> {
            userList.add(new UserDto(userEntity));
        });
        return userList;
    }

    // Create new user
    public void create(UserDto userDto) {
        userData.add(new UserEntity(userDto));
    }

    // Get user by id
    public UserEntity get(int id) {
        return userData.get(id);
    }

    // Get UserDto by user id
    public UserDto getDto(int id) {
        UserEntity userEntity = get(id);
        if (userEntity == null) {
            return null;
        }
        return new UserDto(userEntity);
    }

    // Get UserDto by part of user name
    public UserDto getDto(String partOfName) {
        UserEntity userEntity = get(partOfName);
        if (userEntity == null) {
            return null;
        }
        return new UserDto(userEntity);
    }

    // Get user by part of name
    public UserEntity get(String name) {
        return userData.get(name);
    }

    // Delete user by id
    public UserEntity delete(int id) {
        return userData.delete(id);
    }

    // Edit user by userDto id
    public UserEntity edit(UserDto userDto) {
        return userData.edit(userDto);
    }

}
