DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2021-06-21 07:05:06', 'Завтрак', 500, 100000),
       ('2021-06-21 12:05:07', 'Обед', 1000, 100000),
       ('2021-06-21 18:05:07', 'Ужин', 500, 100000),
       ('2021-06-21 00:00:00', 'Еда на граничное значение', 100, 100000),
       ('2021-06-21 07:05:07', 'Завтрак', 1000, 100000),
       ('2021-06-21 13:05:07', 'Обед', 500, 100000),
       ('2021-06-21 19:05:07', 'Ужин', 410, 100000),
       ('2022-06-21 07:05:07', 'Завтрак', 1000, 100001),
       ('2022-06-21 13:05:07', 'Обед', 500, 100001),
       ('2022-06-21 19:05:07', 'Ланч', 410, 100001);