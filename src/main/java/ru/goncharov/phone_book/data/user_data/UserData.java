package ru.goncharov.phone_book.data.user_data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.goncharov.phone_book.dto.UserDto;
import ru.goncharov.phone_book.entity.UserEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@NoArgsConstructor
public class UserData implements UserDataInterface {

    // Counter for id
    private AtomicInteger lastId = new AtomicInteger(0);
    @Getter
    private Map<Integer, UserEntity> userMap = new ConcurrentSkipListMap<>();


    // Add user in userMap
    //   Set id in "userEntity" and put
    @Override
    public void add(UserEntity user) {
        user.setId(lastId.incrementAndGet());
        userMap.put(lastId.get(), user);
    }

    @Override
    public UserEntity get(int id) {
        return userMap.get(id);
    }

    // Get user by part of user name
    @Override
    public UserEntity get(String partOfName) {
        // partOfName to lower case
        partOfName = partOfName.toLowerCase();
        // temporal variable for user names
        String lowerName;
        // Get all entries from map
        Collection<UserEntity> userCollection = userMap.values();
        // Find user by "partOfName" and return
        for (UserEntity userEntity: userCollection) {
            lowerName = userEntity.getName().toLowerCase();
            if (lowerName.contains(partOfName)) {
                return userEntity;
            }
        }
        // If user was not found, return null
        return null;
    }

    // Delete user by id
    @Override
    public UserEntity delete(int id) {
        return userMap.remove(id);
    }

    // Edit user from "userDto"
    @Override
    public UserEntity edit(UserDto userDto) {
        // Get user form map by id
        UserEntity oldUserEntity = userMap.get(userDto.getId());
        // If user not found, return null
        if (oldUserEntity == null) {
            return null;
        }
        // Merge changes into user and return
        return oldUserEntity.merge(userDto);
    }
}
