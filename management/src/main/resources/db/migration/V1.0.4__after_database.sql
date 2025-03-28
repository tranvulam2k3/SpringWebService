use
manager_n1224c1;

CREATE TABLE user
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(255)          NULL,
    password VARCHAR(255)          NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE `role`
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES user (id);

INSERT INTO user(username,password)
VALUES ('user','$2a$12$Uh4CLzrIGTBbTMxo/vnRyOayaFnD9vnJhPts4KGyLuniLjRmawgKG'),
       ('admin','$2a$12$WkEkjGqvnoyOUIK56WbA8u8p4P.9i5tO0uGVf56apUWSmVmhlv5Z2');

INSERT INTO Role(name)
VALUES ('USER'),
       ('ADMIN');

INSERT INTO user_roles(user_id,role_id)
VALUES (1,1),
       (2,1),
       (2,2)


