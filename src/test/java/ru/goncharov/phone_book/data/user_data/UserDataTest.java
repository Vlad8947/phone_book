package ru.goncharov.phone_book.data.user_data;


import org.junit.Before;
import org.junit.Test;
import ru.goncharov.phone_book.dto.UserDto;
import ru.goncharov.phone_book.entity.UserEntity;

import static org.junit.Assert.*;

public class UserDataTest {

    private UserData userData;

    @Before
    public void before() {
        userData = new UserData();
    }

    @Test
    public void addTest() {
        UserEntity actualUserEntity = new UserEntity("Masha");
        // id=1
        userData.add(actualUserEntity);
        int idKey = 1;
        UserEntity expectedUserEntity = userData.getUserMap().get(idKey);
        // user found
        assertNotNull(expectedUserEntity);
        // assert id
        assertEquals("Assert user id", expectedUserEntity.getId(), idKey);
        // assert another user params
        assertEquals("Assert user params", expectedUserEntity, actualUserEntity);
    }

    @Test
    public void getByIdTest() {
        int id = 1;
        String actName = "Masha";
        UserEntity actualUserEntity = new UserEntity(id,actName);
        userData.getUserMap().put(id, actualUserEntity);

        // When name exist
        UserEntity expectedUserEntity = userData.get(id);
        // User was found
        assertNotNull("User was not found", expectedUserEntity);
        // Users equals
        assertEquals("Ids not equals", expectedUserEntity.getId(), id);
        assertEquals("User params not equals", expectedUserEntity, actualUserEntity);

        // When name not exist
        id = 2;
        expectedUserEntity = userData.get(id);
        // User was not found
        assertNull("User not exist but found", expectedUserEntity);
    }

    @Test
    public void getByPartOfNameTest() {
        int id = 1;
        String actName = "Masha";
        UserEntity actualUserEntity = new UserEntity(id,actName);
        userData.getUserMap().put(id, actualUserEntity);

        // When name exist
        String partOfName = "ash";
        UserEntity expectedUserEntity = userData.get(partOfName);
        // User was found
        assertNotNull("User was not found", expectedUserEntity);
        // Users equals
        assertEquals("Ids not equals", expectedUserEntity.getId(), id);
        assertEquals("User params not equals", expectedUserEntity, actualUserEntity);

        // When name not exist
        partOfName = "HotDor";
        expectedUserEntity = userData.get(partOfName);
        // User was not found
        assertNull("User not exist but found", expectedUserEntity);
    }

    @Test
    public void deleteTest() {
        int id = 1;
        String actName = "Masha";
        UserEntity actualUserEntity = new UserEntity(id,actName);
        userData.getUserMap().put(id, actualUserEntity);

        // When name exist
        UserEntity expectedUserEntity = userData.get(id);
        // User was found
        assertNotNull("User was not found", expectedUserEntity);
        // Users equals
        assertEquals("Ids not equals", expectedUserEntity.getId(), id);
        assertEquals("User params not equals", expectedUserEntity, actualUserEntity);

        // When name not exist
        id = 2;
        expectedUserEntity = userData.get(id);
        // User was not found
        assertNull("User not exist but found", expectedUserEntity);
    }

    @Test
    public void editTest() {
        int actId = 1;
        String actName = "Masha";
        UserEntity actualUserEntity = new UserEntity(actId,actName);
        String expName = "Diana";
        UserDto userDto = new UserDto(actId, expName);
        userData.getUserMap().put(actId, actualUserEntity);

        // When User exist
        UserEntity expectedUserEntity = userData.edit(userDto);
        // User was found
        assertNotNull("User was not found", expectedUserEntity);
        // Ids equals
        assertEquals("Ids not equals", expectedUserEntity.getId(), actId);
        // Params was modified
        assertEquals("Name was not modified", expectedUserEntity.getName(), expName);

        // When user not exist
        userDto.setId(2);
        expectedUserEntity = userData.edit(userDto);
        assertNull("User not exist but found", expectedUserEntity);

    }
}