CREATE TABLE `seller`
(
    id int NOT NULL AUTO_INCREMENT,
    name   varchar(255) NOT NULL,
    username   varchar(255) NOT NULL,
    password_hash   varchar(255) NOT NULL,
    phonenumber   varchar(255) NOT NULL,
    address   varchar(255) NOT NULL,
    gender   varchar(255) NOT NULL,
    birthdate   date NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (username)
);