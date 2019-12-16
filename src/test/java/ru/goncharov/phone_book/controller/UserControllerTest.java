package ru.goncharov.phone_book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.goncharov.phone_book.dto.UserDto;
import ru.goncharov.phone_book.entity.UserEntity;
import ru.goncharov.phone_book.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getAllTest() throws Exception {
        int id = 1;
        UserDto userDto1 = new UserDto(id++, "Misha");
        UserDto userDto2 = new UserDto(id++, "Pasha");
        List<UserDto> userDtoList = Arrays.asList(
                userDto1, userDto2);

        when(userService.getAll()).thenReturn(userDtoList);
        mockMvc.perform(
                get("/user/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(userDtoList.size())))
                .andExpect(jsonPath("$[0].id", is(userDto1.getId())))
                .andExpect(jsonPath("$[0].name", is(userDto1.getName())))
                .andExpect(jsonPath("$[1].id", is(userDto2.getId())))
                .andExpect(jsonPath("$[1].name", is(userDto2.getName())));

    }

    @Test
    public void getByIdTest() throws Exception {
        int id = 1;
        UserEntity userEntity = new UserEntity(id, "Misha");

        when(userService.get(id)).thenReturn(userEntity);
        mockMvc.perform(
                get("/user/id-{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userEntity.getId())))
                .andExpect(jsonPath("$.name", is(userEntity.getName())));

        int notExistId = 2;
        when(userService.get(notExistId)).thenReturn(null);
        mockMvc.perform(
                get("/user/id-{id}", notExistId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getByNameTest() throws Exception {
        UserEntity userEntity = new UserEntity(1, "Misha");

        String partOfName = "ish";
        when(userService.get(partOfName)).thenReturn(userEntity);
        mockMvc.perform(
                get("/user/name-{name}", partOfName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userEntity.getId())))
                .andExpect(jsonPath("$.name", is(userEntity.getName())));

        String notExistPartOfName = "aposh";
        when(userService.get(notExistPartOfName)).thenReturn(null);
        mockMvc.perform(
                get("/user/name-{name}", notExistPartOfName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        String emptyField = " ";
        when(userService.get(emptyField)).thenReturn(userEntity);
        mockMvc.perform(
                get("/user/name-{name}", emptyField)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createTest() throws Exception {
        UserDto user = new UserDto(1, "Misha");
        mockMvc.perform(
                post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isCreated());

        // Empty field
        user.setName(" ");
        mockMvc.perform(
                post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteTest() throws Exception {
        int id = 1;
        UserEntity userEntity = new UserEntity(id, "Misha");

        when(userService.delete(id)).thenReturn(userEntity);
        mockMvc.perform(
                delete("/user/delete?id={id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        int notExistId = 3;
        when(userService.delete(notExistId)).thenReturn(null);
        mockMvc.perform(
                delete("/user/delete?id={id}", notExistId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void editTest() throws Exception {
        int id = 1;
        String userName = "Masha";
        UserEntity userEntity = new UserEntity(id, userName);
        UserDto userDto = new UserDto(id, userName);

        when(userService.edit(userDto)).thenReturn(userEntity);
        mockMvc.perform(
                post("/user/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(status().isOk());

        int notExistId = 3;
        userDto.setId(notExistId);
        when(userService.edit(userDto)).thenReturn(null);
        mockMvc.perform(
                post("/user/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(status().isNotFound());
    }

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}