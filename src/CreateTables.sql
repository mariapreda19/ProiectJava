CREATE SEQUENCE ID_SEQ
START WITH 1
INCREMENT BY 1;



CREATE TABLE PLAYERS (
                         id INT PRIMARY KEY,
                         username VARCHAR(50),
                         password VARCHAR(50) NOT NULL
);



CREATE TABLE SCORES (
                         id INT PRIMARY KEY,
                         user_id INT,
                         score INT NOT NULL,
                         level_id INT NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES PLAYERS(id),
                         FOREIGN KEY (level_id) REFERENCES LEVELS(id)
);

CREATE TABLE ENEMIES (
                         id INT PRIMARY KEY,
                         sprite VARCHAR(100),
                         positionX INT,
                         positionY INT
);


CREATE TABLE DOGS (
                         id INT PRIMARY KEY,
                         name VARCHAR(50),
                         sprite VARCHAR(50),
                         positionX INT,
                         positionY INT
);

CREATE TABLE CLASSIC_DOGS (
                         id INT PRIMARY KEY,
                         healPoints INT,
                         points INT,
                         FOREIGN KEY (id) REFERENCES DOGS(id)
);

CREATE TABLE MOTO_DOGS (
                         id INT PRIMARY KEY,
                         healPoints INT,
                         points INT,
                         ability VARCHAR(50),
                         FOREIGN KEY (id) REFERENCES DOGS(id)
);






CREATE TABLE LEVELS(
                       id INT PRIMARY KEY,
                       map_dimension INT,
                       number_of_enemies INT

);



drop table LEVELS;

CREATE OR REPLACE TRIGGER new_player
        BEFORE INSERT ON PLAYERS
        FOR EACH ROW
BEGIN
    SELECT ID_SEQ.NEXTVAL INTO :new.id FROM DUAL;
END;

CREATE OR REPLACE TRIGGER new_score
        BEFORE INSERT ON SCORES
        FOR EACH ROW
BEGIN
    SELECT ID_SEQ.NEXTVAL INTO :new.id FROM DUAL;
END;


CREATE OR REPLACE TRIGGER new_dog
        BEFORE INSERT ON DOGS
        FOR EACH ROW
BEGIN
    SELECT ID_SEQ.NEXTVAL INTO :new.id FROM DUAL;
END;

CREATE OR REPLACE TRIGGER new_enemy
        BEFORE INSERT ON ENEMIES
        FOR EACH ROW
BEGIN
    SELECT ID_SEQ.NEXTVAL INTO :new.id FROM DUAL;
END;

CREATE OR REPLACE TRIGGER new_level
        BEFORE INSERT ON LEVELS
        FOR EACH ROW
BEGIN
    SELECT ID_SEQ.NEXTVAL INTO :new.id FROM DUAL;
END;




INSERT INTO ENEMIES (sprite, positionX, positionY) VALUES ('../assets/enemy1.png', 5, 6);
INSERT INTO ENEMIES (sprite, positionX, positionY) VALUES ('../assets/enemy1.png', 7, 8);
INSERT INTO ENEMIES (sprite, positionX, positionY) VALUES ('../assets/enemy1.png', 3, 3);

INSERT INTO DOGS (name, sprite, positionX, positionY) VALUES ('Rex', '../assets/2-removebg-preview.png', 4, 5);
INSERT INTO DOGS (name, sprite, positionX, positionY) VALUES ('Buddy', '../assets/3-removebg-preview.png', 6, 7);
INSERT INTO DOGS (name, sprite, positionX, positionY) VALUES ('Max', '../assets/4-removebg-preview.png', 2, 2);

INSERT INTO CLASSIC_DOGS (id, healPoints, points) VALUES (40, 10, 1);
INSERT INTO MOTO_DOGS (id, healPoints, points, ability) VALUES (41, 10, 2, 'speed');
INSERT INTO CLASSIC_DOGS (id, healPoints, points) VALUES (42, 10, 1);

insert into LEVELS (id, map_dimension, number_of_enemies) values (1, 14, 1);
insert into LEVELS (id, map_dimension, number_of_enemies) values (2, 16, 1);
insert into LEVELS (id, map_dimension, number_of_enemies) values (3, 18, 2);


select * from dogs;



commit;