INSERT INTO chat.users (login, password)
VALUES ('user1', '111111');
INSERT INTO chat.users (login, password)
VALUES ('user2', '222222');
INSERT INTO chat.users (login, password)
VALUES ('user3', '333333');
INSERT INTO chat.users (login, password)
VALUES ('user4', '444444');
INSERT INTO chat.users (login, password)
VALUES ('user5', '55555555');

INSERT INTO chat.rooms (name, owner)
VALUES ('General', 1);
INSERT INTO chat.rooms (name, owner)
VALUES ('Special', 2);
INSERT INTO chat.rooms (name, owner)
VALUES ('Jokes', 3);
INSERT INTO chat.rooms (name, owner)
VALUES ('Work', 4);
INSERT INTO chat.rooms (name, owner)
VALUES ('Hobby', 5);

INSERT INTO chat.messages (author, room, text, timestamp)
VALUES (1, 1, 'aaaaaaaaa', '2000-01-01 00:00:01');
INSERT INTO chat.messages (author, room, text, timestamp)
VALUES (2, 3, 'bbbb', '2000-01-01 00:00:02');
INSERT INTO chat.messages (author, room, text, timestamp)
VALUES (5, 3, 'ccccccc', '2000-01-01 00:00:03');
INSERT INTO chat.messages (author, room, text, timestamp)
VALUES (4, 4, 'ddddddddd', '2000-01-01 00:00:04');
INSERT INTO chat.messages (author, room, text, timestamp)
VALUES (5, 5, 'eeeeeeeeee', '2000-01-01 00:00:05');