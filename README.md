# Финальный проект JavaRush

---

## [REST API](http://localhost:8080/doc)

### Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

### Аналоги

- https://java-source.net/open-source/issue-trackers

### Тестирование

- https://habr.com/ru/articles/259055/

---



# Запуск приложения

---

### 1. Через Docker

1. Клонировать себе на машину проект.
2. Убедиться, что свободны следующие порты:
- `80` - Nginx
- `8080` - Java - приложение
- `5050` - pgAdmin
- `5432` - PostgreSQL

2. Выполнить команду в консоли `docker-compose up`
3. Дождаться билда приложения (1-2 минуты) и запуска всех контейнеров
4. Открыть приложение в браузере по адресу <a href="http://localhost">http://localhost</a> или <a href="http://localhost:80">http://localhost:80</a>


#### Примечания:
- Доступ к приложению напрямую через порт `:8080` закрыт. Доступ возможен только через `nginx` порт 
- pgAdmin находится по адресу <a href="http://localhost:5050">http://localhost:5050</a>
  
    Доступы:
    - `login`: admin@admin.com
    - `password`: root


- Во время запуска приложения произойдет популяция БД для работы. Если точнее – накатится структура и словари.
Чтоб «посмотреть» как работает приложение нужно выполнить скрипт `data.sql` из `resources/data4dev`.


- При изменении кода необходимо пересобрать приложение командой `docker-compose build`



-------------------------
### 2. Через intelijIdea
- Точка входа в приложение `com.javarush.jira.JiraRushApplication`

1. Клонировать себе на машину проект.
2. Запустить локально сервер БД (PostgreSQL). Рекомендую это делать через docker.

Команда для запуска контейнера 
```
docker run -p 5432:5432 --name postgres-db -e POSTGRES_USER=jira -e POSTGRES_PASSWORD=JiraRush -e POSTGRES_DB=jira -e PGDATA=/var/lib/postgresql/data/pgdata -v ./pgdata:/var/lib/postgresql/data -d postgres
``` 
3. Сбилдить приложение: mvn clean install
4. Создать конфигурацию запуска одним из вариантов: 
   - Запустить приложение через `точку входа` и оно упадет с ошибкой
   - Создать spring boot конфигурацию запуска вручную


5. Добавить `Environment Variables` в конфигурацию запуска:

    Cтрока `Environment Variables` для копирования в конфигурацию запуска.
    ```properties
    DB_HOST=localhost:5432;DB_NAME=jira;DB_PASSWORD=JiraRush;DB_USERNAME=jira;OAUTH_GITHUB_CLIENT_ID=3d0d8738e65881fff266;OAUTH_GITHUB_CLIENT_SECRET=0f97031ce6178b7dfb67a6af587f37e222a16120;OAUTH_GITLAB_CLIENT_ID=b8520a3266089063c0d8261cce36971defa513f5ffd9f9b7a3d16728fc83a494;OAUTH_GITLAB_CLIENT_SECRET=e72c65320cf9d6495984a37b0f9cc03ec46be0bb6f071feaebbfe75168117004;OAUTH_GOOGLE_CLIENT_ID=329113642700-f8if6pu68j2repq3ef6umd5jgiliup60.apps.googleusercontent.com;OAUTH_GOOGLE_CLIENT_SECRET=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;SMTP_HOST=smtp.yandex.ru;SMTP_PORT=587;SMTP_USERNAME=ryver.mailer@yandex.ru;SMTP_PASSWORD=sinoblcmolmguvhb
    ```

    Удобочитаемый вид строки `Environment Variables`:
    ```properties
    DB_HOST=localhost:5432;
    DB_NAME=jira;
    DB_PASSWORD=JiraRush;
    DB_USERNAME=jira;
    OAUTH_GITHUB_CLIENT_ID=3d0d8738e65881fff266;
    OAUTH_GITHUB_CLIENT_SECRET=0f97031ce6178b7dfb67a6af587f37e222a16120;
    OAUTH_GITLAB_CLIENT_ID=b8520a3266089063c0d8261cce36971defa513f5ffd9f9b7a3d16728fc83a494;
    OAUTH_GITLAB_CLIENT_SECRET=e72c65320cf9d6495984a37b0f9cc03ec46be0bb6f071feaebbfe75168117004;
    OAUTH_GOOGLE_CLIENT_ID=329113642700-f8if6pu68j2repq3ef6umd5jgiliup60.apps.googleusercontent.com;
    OAUTH_GOOGLE_CLIENT_SECRET=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;
    SMTP_HOST=smtp.yandex.ru;
    SMTP_PORT=587;
    SMTP_USERNAME=ryver.mailer@yandex.ru;
    SMTP_PASSWORD=sinoblcmolmguvhb;
    ```

    Если поле для `Environment Variables` в настройках конфигурации запуска выключено, то включить его, сделав следующее (см. скриншот): 
    
   - `Edit configuration` -> `Modify Options` -> `Environment Variables`

     ![/doc/idea-env-vars.png](/doc/idea-env-vars.png)

6. Если необходимо изменить доступы (например к БД), то отредактировать `Environment Variables`,
7. Запустить Spring Boot приложение (JiraRushApplication) с профилем prod
8. Открыть приложение в браузере по адресу <a href="http://localhost:8080">http://localhost:8080</a>

#### Примечание:
- Во время запуска приложения произойдет популяция БД для работы. Если точнее – накатится структура и словари. 
Чтоб «посмотреть» как работает приложение нужно выполнить скрипт `data.sql` из `resources/data4dev`.

---

## Список выполненных задач:

- Задачи находящиеся ниже - Выполнены
- Комментарии к некоторым задчам просто дают дополнительную информацию

---

1. Разобраться со структурой проекта (onboarding).

---

2. Удалить социальные сети: vk, yandex. Easy task
   
    #### Комментарий:
    - При поиске по проекту с запросом "`yandex`" будут показаны результаты с настройками SMTP. Он не относится к этой задаче и используется для `задачи 11`, так как изначально заданный в проекте у меня не захотел работать

---

3. Вынести чувствительную информацию в отдельный проперти файл:
   - логин
   - пароль БД
   - идентификаторы для OAuth регистрации/авторизации
   - настройки почты

    Значения этих проперти должны считываться при старте сервера из переменных окружения машины. Easy task
    #### Комментарий:
    - Значения вынесены в файл `resources/env.yaml`. 
    - Для запуска приложения через `intelijIdea` необходимо добавить переменные окружения в конфигурацию запуска
    - При запуске через Docker переменные окружения можно изменять в `docker-compose.yaml` в сервисе `app`

---
4. Переделать тесты так, чтоб во время тестов использовалась in memory БД (H2), а не PostgreSQL. Для этого нужно определить 2 бина, и выборка какой из них использовать должно определяться активным профилем Spring. H2 не поддерживает все фичи, которые есть у PostgreSQL, поэтому тебе прийдется немного упростить скрипты с тестовыми данными.

   #### Комментарий:
   - База данных для тестов была переключена в настройках приложения в файле `test/resources/env-tests.yaml` в блоке `datasource`
   - Для чего создавать 2 бина, мне непонятно, так как БД подменяется полностью через `.yaml` настройки. Если бы задача стояла следующим образом то понятно: "Создать отдельный профиль, при запуске с которым тесты запускаются с в H2."  
   - Для простоты запуска тестов через IntelijIdea логины/пароли не берутся из переменных окружения. (продовские же берутся из переменных окружения) 

---

5. Написать тесты для всех публичных методов контроллера ProfileRestController. Хоть методов только 2, но тестовых методов должно быть больше, т.к. нужно проверить success and unsuccess path.

    #### Комментарий:
    - Написанные тесты находятся в классе `profile.internal.web/ProfileRestControllerTest`

---

6. Сделать рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload чтоб он использовал современный подход для работы с файловой системмой. Easy task

---

7. Добавить новый функционал: добавления тегов к задаче (REST API + реализация на сервисе). Фронт делать необязательно. Таблица task_tag уже создана.

---

8. Добавить подсчет времени сколько задача находилась в работе и тестировании. Написать 2 метода на уровне сервиса, которые параметром принимают задачу и возвращают затраченное время:
   - Сколько задача находилась в работе (ready_for_review минус in_progress ).
   - Сколько задача находилась на тестировании (done минус ready_for_review).

    #### Комментарий: 
    - Основная логика расчета находится в методе `TaskService` -> `calculateTimeSpentInStatus()`
    - Эти показатели так же можно посмотреть на фронте: на странице списка задач перейти в детальную информацию по задаче (модальное окно)
---

9. Написать Dockerfile для основного сервера

---

10. Написать docker-compose файл для запуска контейнера сервера вместе с БД и nginx. Для nginx используй конфиг-файл config/nginx.conf. При необходимости файл конфига можно редактировать. Hard task

---

11. Добавить локализацию минимум на двух языках для шаблонов писем (mails) и стартовой страницы index.html.