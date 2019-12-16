package ru.goncharov.phone_book;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.goncharov.phone_book.data.phone_book_data.PhoneEntryData;
import ru.goncharov.phone_book.data.user_data.UserData;
import ru.goncharov.phone_book.entity.PhoneEntryEntity;
import ru.goncharov.phone_book.entity.UserEntity;

@Component
@NoArgsConstructor
public class DataInit implements ApplicationRunner {

    @Autowired
    private PhoneEntryData phoneBookData;
    @Autowired
    private UserData userData;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        init();
    }

    private void init() {
        for(int i = 1; i <= 5; i++) {
            UserEntity userEntity = new UserEntity("user" + i);
            userData.add(userEntity);
            for(int j = 1; j <= 9; j++) {
                PhoneEntryEntity phoneEntry =
                        new PhoneEntryEntity(userEntity, "phoneName" + j, "80" + i + j);
                phoneBookData.add(phoneEntry);
            }
        }
    }

}
