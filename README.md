# phone_book

### Стек технологий:
  Java 8, Spring Boot, JUnit, Lombok.
  
### Инструкция по запуску:
  1) Скачать файл "phone_book.jar"
  2) В окне команд запустить программу командой "java -jar phone_book.jar"
  
  Для приостановки программы в окне нажать сочетание клавиш Ctrl+C.
  
### Примеры запросов:
  Для запросов используется хост "http://localhost:8080". Его нужно прописывать перед каждым URL запроса.
  
  Пример: http://localhost:8080/user/all
  
#### 1) Работа со списком пользователей

    - Получение всех пользователей
        Method: GET
        url: /user/all

    - Получение пользователя по id
        Method: GET
        url: /user/id-{userId}
          где userId - id номер пользователя
        Пример: user/id-1

    - Получение юзера по части имени
        Method: GET
        url: /user/name-{userName}
          где userName - часть имени пользователя
        Пример: user/name-vlad , user/name-la

    - Создание нового пользователя
        Method: POST
        url: /user/create
        Request body: 	
          {"name": "vlad"}
            где поле name - имя создаваемого пользователя.

    - Удаление пользователя
        Method: DELETE
        url: /user/delete
        Request params:
          id - идентификатор пользователя.
        Пример: user/delete?id=2

    - Изменение параметров пользователя
        Method: POST
        url: /user/edit
        Request body:
          {"id":2,"name":"Anton"}
            id - идентификатор пользователя
            name - новое имя пользователя

#### 2) Работа с телефонными записями:

    - Получение всех телефонных записей пользователя
      Method: GET
      url: /phone_entry/user_id-{id}
        где id - идентификатор пользователя
      Пример: /phone_entry/user_id-2

    - Получение телефонной записи по id
      Method: GET
      url: /phone_entry/id-{id}
        где id - идентификатор пользователя
      Пример: /phone_entry/id-2

    - Получение телефонной записи по номеру телефона
      Method: GET
      url: /phone_entry/number-{number}
        где number - номер телефона
      Пример: /phone_entry/number-803

    - Создание телефонной записи
      Method: POST
      url: /phone_entry/create
      Request body: 	
        {
          "ownerId"		: 3,
          "phoneName"	: "Andrey",
          "phoneNumber"	: "505"
        }
          где "ownerId" - id пользователя, которому назначить данную запись;
            "phoneName" - имя собственника номера телефона;
            "phoneNumber" - номер телефона.

    - Удаление записи
      Method: DELETE
      url: /phone_entry/delete
      Request params:
        id - идентификатор пользователя.
      Пример: user/delete?id=2


    - Изменение параметров записи
      Method: POST
      url: /user/edit
      Request body:
        {
          "id" : 19
          "ownerId"	: 3,
          "phoneName" : "Andrey",
          "phoneNumber"	: "505"
        }
          где	"id" - идентификатор записи
            "ownerId" - id пользователя, которому назначить данную запись;
            "phoneName" - имя собственника номера телефона;
            "phoneNumber" - номер телефона.
