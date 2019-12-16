package ru.goncharov.phone_book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.goncharov.phone_book.entity.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;
    private String name;


    public UserDto(UserEntity userEntity) {
        id = userEntity.getId();
        name = userEntity.getName();
    }

}
