package ru.goncharov.phone_book.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.goncharov.phone_book.dto.UserDto;
import ru.goncharov.phone_book.entity.UserEntity;
import ru.goncharov.phone_book.exception.EmptyFieldException;
import ru.goncharov.phone_book.exception.EntityNotFoundException;
import ru.goncharov.phone_book.service.UserService;

import java.util.List;

@Controller
@NoArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/id-{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDto getUserById(@PathVariable int id) {
        UserEntity userEntity = userService.get(id);
        if (userEntity == null) {
            throw new EntityNotFoundException();
        }
        return new UserDto(userEntity);
    }

    @GetMapping("/name-{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDto getUserByName(@PathVariable String name) {
        checkParam(name, "name");
        UserEntity userEntity = userService.get(name);
        if (userEntity == null) {
            throw new EntityNotFoundException();
        }
        return new UserDto(userEntity);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserDto user) {
        checkParams(user);
        userService.create(user);
    }

    @DeleteMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestParam int id) {
        UserEntity userEntity = userService.delete(id);
        if (userEntity == null) {
            throw new EntityNotFoundException();
        }
    }

    @PostMapping(value = "/edit")
    @ResponseStatus(HttpStatus.OK)
    public void editUser(@RequestBody UserDto userDto) throws EmptyFieldException {
        UserEntity userEntity = userService.edit(userDto);
        if (userEntity == null) {
            throw new EntityNotFoundException();
        }
    }

    private void checkParams(UserDto userDto) {
        checkParam(userDto.getName(), "name");
    }

    private void checkParam(String param, String paramName) {
        if (param.trim().isEmpty()) {
            throw new EmptyFieldException();
        }
    }

}
