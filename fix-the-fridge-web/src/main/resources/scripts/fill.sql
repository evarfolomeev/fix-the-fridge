DELETE FROM PUBLIC.IDEA;
DELETE FROM PUBLIC.USER;
DELETE FROM PUBLIC.IDEA_COMMENT;
DELETE FROM PUBLIC.COMMENT_VOTERS;
DELETE FROM PUBLIC.COMMENT;

INSERT INTO PUBLIC.USER (NICKNAME, EMAIL, ENABLED, NAME, PASSWORD)
VALUES ('admin', 'admin@admin.com', true, 'Denis', '$2a$10$px74E8JVQ748MkRubGLOWOQz0uNdPfad5ooLt5tSLXB2GdlEtQuIW');
INSERT INTO PUBLIC.USER (NICKNAME, EMAIL, ENABLED, NAME, PASSWORD)
VALUES ('user', 'user@user.com', true, 'Ivan', '$2a$10$px74E8JVQ748MkRubGLOWOQz0uNdPfad5ooLt5tSLXB2GdlEtQuIW');

INSERT INTO PUBLIC.USER_ROLE (USER_ID, ROLE)
VALUES ('admin', 'ADMIN');
INSERT INTO PUBLIC.USER_ROLE (USER_ID, ROLE)
VALUES ('admin', 'USER');
INSERT INTO PUBLIC.USER_ROLE (USER_ID, ROLE)
VALUES ('user', 'USER');

INSERT INTO PUBLIC.IDEA (ID, CREATING_DATE, DESCRIPTION, STATUS, TITLE, USER_NICKNAME)
VALUES (1, null, 'Buy batteries for joystick', 'OPEN', 'Buy batteries', 'admin');
INSERT INTO PUBLIC.IDEA (ID, CREATING_DATE, DESCRIPTION, STATUS, TITLE, USER_NICKNAME)
VALUES (2, null, 'Buy second microwave', 'OPEN', 'Buy microwave', 'user');


INSERT INTO PUBLIC.COMMENT (ID, CREATING_DATE, TEXT, AUTHOR_NICKNAME, IDEA_ID)
VALUES (1, '2014-07-03 18:36:53.609', 'perfect idea! I love it! cgo! go! go!', 'admin', 1);

INSERT INTO PUBLIC.COMMENT_VOTERS (COMMENT_ID, USER_ID) VALUES (1, 'admin');
INSERT INTO PUBLIC.COMMENT_VOTERS (COMMENT_ID, USER_ID) VALUES (1, 'user');



INSERT INTO PUBLIC.IDEA_COMMENT (IDEA_ID, COMMENTS_ID) VALUES (1, 1);