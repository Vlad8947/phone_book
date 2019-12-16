# phone_book

### Стек технологий:
  Java 8, Spring Boot, JUnit, Lombok.
  
### Инструкция по запуску:
  1) Скачать файл "phone_book.jar"
  2) В окне команд запустить программу командой "java -jar phone_book.jar"
  Для приостановки программы в окне нажать сочетание клавиш Ctrl+C.
  
### Примеры запросов:
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
