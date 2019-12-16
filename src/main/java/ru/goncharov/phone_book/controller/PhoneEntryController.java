package ru.goncharov.phone_book.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.goncharov.phone_book.dto.PhoneEntryDto;
import ru.goncharov.phone_book.entity.PhoneEntryEntity;
import ru.goncharov.phone_book.exception.EmptyFieldException;
import ru.goncharov.phone_book.exception.EntityNotFoundException;
import ru.goncharov.phone_book.service.PhoneEntryService;

import java.util.List;

@Controller
@RequestMapping(value = "/phone_entry")
@NoArgsConstructor
public class PhoneEntryController {

    @Autowired
    private PhoneEntryService phoneEntryService;


    @GetMapping("/user_id-{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PhoneEntryDto> getAllByUserId(@PathVariable int id) {
        List<PhoneEntryDto> entryDtos = phoneEntryService.getAllDtosByUserId(id);
        checkEntryNotFound(entryDtos);
        return entryDtos;
    }

    @GetMapping("/id-{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PhoneEntryDto getEntryById(@PathVariable int id) {
        PhoneEntryEntity entryEntity = phoneEntryService.get(id);
        checkEntryNotFound(entryEntity);
        return new PhoneEntryDto(entryEntity);
    }

    @GetMapping("/number-{number}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PhoneEntryDto getEntryByNumber(@PathVariable String number) {
        checkParam(number);
        PhoneEntryEntity entryEntity = phoneEntryService.get(number);
        checkEntryNotFound(entryEntity);
        return new PhoneEntryDto(entryEntity);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEntry(@RequestBody PhoneEntryDto entryDto) {
        checkDto(entryDto);
        if (!phoneEntryService.create(entryDto)) {
            throw new EntityNotFoundException();
        }
    }

    @DeleteMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEntry(@RequestParam int id) {
        PhoneEntryEntity entryEntity = phoneEntryService.delete(id);
        checkEntryNotFound(entryEntity);
    }

    @PostMapping(value = "/edit")
    @ResponseStatus(HttpStatus.OK)
    public void editEntry(@RequestBody PhoneEntryDto entryDto) {
        checkDto(entryDto);
        PhoneEntryEntity entryEntity = phoneEntryService.edit(entryDto);
        checkEntryNotFound(entryEntity);
    }

    private void checkDto(PhoneEntryDto entryDto) {
        if (entryDto.getPhoneName().trim().isEmpty()
                || entryDto.getPhoneNumber().isEmpty()) {
            throw new EmptyFieldException();
        }
    }

    private void checkParam(String param) {
        if (param.trim().isEmpty()) {
            throw new EmptyFieldException();
        }
    }

    private void checkEntryNotFound(Object object) {
        if (object == null) {
            throw new EntityNotFoundException();
        }
    }

}
