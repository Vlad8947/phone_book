package ru.goncharov.phone_book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.goncharov.phone_book.dto.PhoneEntryDto;
import ru.goncharov.phone_book.entity.PhoneEntryEntity;
import ru.goncharov.phone_book.entity.UserEntity;
import ru.goncharov.phone_book.service.PhoneEntryService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PhoneEntryController.class)
public class PhoneEntryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneEntryService entryService;

    @Test
    public void getAllByUserIdTest() throws Exception {
        int id = 1;
        int notExistId = 3;
        List<PhoneEntryDto> entryDtos = new ArrayList<>();
        PhoneEntryDto entryDto1 = new PhoneEntryDto(1, 1, "Mom", "111");
        entryDtos.add(entryDto1);
        PhoneEntryDto entryDto2 = new PhoneEntryDto(2, 2, "Dad", "222");
        entryDtos.add(entryDto2);

        when(entryService.getAllDtosByUserId(id)).thenReturn(entryDtos);
        when(entryService.getAllDtosByUserId(notExistId)).thenReturn(null);

        mockMvc.perform(
                get("/phone_entry/user_id-{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(entryDtos.size())))
                .andExpect(jsonPath("$[0].id", is(entryDto1.getId())))
                .andExpect(jsonPath("$[0].ownerId", is(entryDto1.getOwnerId())))
                .andExpect(jsonPath("$[0].phoneName", is(entryDto1.getPhoneName())))
                .andExpect(jsonPath("$[0].phoneNumber", is(entryDto1.getPhoneNumber())))

                .andExpect(jsonPath("$[1].id", is(entryDto2.getId())))
                .andExpect(jsonPath("$[1].ownerId", is(entryDto2.getOwnerId())))
                .andExpect(jsonPath("$[1].phoneName", is(entryDto2.getPhoneName())))
                .andExpect(jsonPath("$[1].phoneNumber", is(entryDto2.getPhoneNumber())));

        mockMvc.perform(
                get("/phone-entry/user_id-{id}", notExistId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getEntryByIdTest() throws Exception {
        int id = 1;
        int notFoundId = 3;
        PhoneEntryEntity actEntryEntity = new PhoneEntryEntity(new UserEntity(), "Andrey", "123");
        actEntryEntity.setId(id);

        when(entryService.get(id)).thenReturn(actEntryEntity);
        mockMvc.perform(
                get("/phone_entry/id-{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(actEntryEntity.getId())))
                .andExpect(jsonPath("$.phoneName", is(actEntryEntity.getPhoneName())))
                .andExpect(jsonPath("$.phoneNumber", is(actEntryEntity.getPhoneNumber())));

        // Not found
        when(entryService.get(notFoundId)).thenReturn(null);
        mockMvc.perform(
                get("/phone_entry/id-{id}", notFoundId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getEntryByNumberTest() throws Exception {
        int id = 1;
        String actNumber = "123";
        String notExistNumber = "456";
        String emptyField = " ";
        PhoneEntryEntity actEntryEntity = new PhoneEntryEntity(new UserEntity(), "Andrey", actNumber);
        actEntryEntity.setId(id);

        when(entryService.get(actNumber)).thenReturn(actEntryEntity);
        mockMvc.perform(
                get("/phone_entry/number-{number}", actNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(actEntryEntity.getId())))
                .andExpect(jsonPath("$.phoneName", is(actEntryEntity.getPhoneName())))
                .andExpect(jsonPath("$.phoneNumber", is(actEntryEntity.getPhoneNumber())));

        // Empty field
        mockMvc.perform(
                get("/phone_entry/number-{number}", emptyField)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Not found
        when(entryService.get(notExistNumber)).thenReturn(null);
        mockMvc.perform(
                get("/phone_entry/number-{number}", notExistNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createEntryTest() throws Exception {
        int id = 1;
        PhoneEntryDto entryDto =
                new PhoneEntryDto(id, 1, "Andrey", "123");
        UserEntity userEntity = new UserEntity(entryDto.getOwnerId(), "Vlad");
        PhoneEntryEntity entryEntity =
                new PhoneEntryEntity(userEntity, entryDto.getPhoneName(), entryDto.getPhoneNumber());

        // Created
        when(entryService.create(entryDto)).thenReturn(true);
        mockMvc.perform(
                post("/phone_entry/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(entryDto)))
                .andExpect(status().isCreated());

        // Not found
        when(entryService.create(entryDto)).thenReturn(false);
        mockMvc.perform(
                post("/phone_entry/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(entryDto)))
                .andExpect(status().isNotFound());

        // Empty field
        entryDto.setPhoneName(" ");
        mockMvc.perform(
                post("/phone_entry/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(entryDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteEntryTest() throws Exception {
        int id = 1;
        PhoneEntryEntity entryEntity = new PhoneEntryEntity();
        entryEntity.setId(id);

        // Ok
        when(entryService.delete(id)).thenReturn(entryEntity);
        mockMvc.perform(
                delete("/phone_entry/delete?id={id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Not found
        when(entryService.delete(id)).thenReturn(null);
        mockMvc.perform(
                delete("/phone_entry/delete?id={id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void editEntityTest() throws Exception {
        int id = 1;
        PhoneEntryDto entryDto = new PhoneEntryDto(id, id, "Andrey", "456");
        PhoneEntryEntity entryEntity = new PhoneEntryEntity();

        // Ok
        when(entryService.edit(entryDto)).thenReturn(entryEntity);
        mockMvc.perform(
                post("/phone_entry/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(entryDto)))
                .andExpect(status().isOk());

        // Not found
        when(entryService.edit(entryDto)).thenReturn(null);
        mockMvc.perform(
                post("/phone_entry/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(entryDto)))
                .andExpect(status().isNotFound());

        //Empty field
        entryDto.setPhoneName(" ");
        mockMvc.perform(
                post("/phone_entry/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(entryDto)))
                .andExpect(status().isBadRequest());
    }

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}