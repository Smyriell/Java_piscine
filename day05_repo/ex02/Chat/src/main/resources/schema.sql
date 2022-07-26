DROP SCHEMA IF EXISTS chat CASCADE;

CREATE SCHEMA IF NOT EXISTS  chat;

CREATE TABLE IF NOT EXISTS chat.user (
    id SERIAL PRIMARY KEY,
    name VARCHAR(10) NOT NULL UNIQUE,
    password VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS chat.chatroom (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    owner INTEGER NOT NULL,
    FOREIGN KEY (owner) REFERENCES chat.user(id)
);

CREATE TABLE IF NOT EXISTS chat.message (
    id SERIAL PRIMARY KEY,
    author INTEGER NOT NULL,
    room INTEGER NOT NULL,
    text TEXT NOT NULL,
    localDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author) REFERENCES chat.user(id),
    FOREIGN KEY (room) REFERENCES chat.chatroom
);

CREATE TABLE IF NOT EXISTS chat.user_chatroom (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    chat_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES chat.user(id),
    FOREIGN KEY (chat_id) REFERENCES chat.chatroom(id)
);