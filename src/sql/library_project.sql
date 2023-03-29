CREATE TABLE person
(
    id        SERIAL PRIMARY KEY,
    fullname  VARCHAR(30) NOT NULL UNIQUE,
    birthdate INT NOT NULL
);
CREATE TABLE book
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(30) NOT NULL,
    author    VARCHAR(30) NOT NULL,
    edition   INT NOT NULL,
    person_id INT REFERENCES person (id) ON DELETE SET NULL
);

INSERT INTO person(fullname, birthdate)
VALUES ('Макарова Инна Евгеньевна', 1988),
       ('Макаров Петр Сидорович', 1987),
       ('Леонтьев Дмитрий Алексеевич', 1966),
       ('Кажебулатов Бахром Персистович', 1901),
       ('Семен Семенович Берестович', 1975);

INSERT INTO book(name, author, edition)
VALUES ('Городок в табакерке','Владимир Одоевский', 1834),
       ('Три толстяка', 'Юрий Олеша', 1924),
       ('Приключения Буратино','Алексей Толстой', 1935),
       ('Два капитана','Вениамин Каверин', 1944),
       ('Приключения Васи Куролесова','Юрий Коваль', 1971);