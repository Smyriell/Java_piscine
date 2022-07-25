INSERT INTO chat.user(name, password) VALUES
('Maria', 'maria'), ('Paul','paul'), ('Bob', 'bob'), ('Mike', 'mike'), ('John', 'john')
ON CONFLICT (name) DO NOTHING;

INSERT INTO chat.chatroom(name, owner) VALUES
('room1', (SELECT id FROM chat.user WHERE name = 'Maria')),
('room2', (SELECT id FROM chat.user WHERE name = 'Paul')),
('room3', (SELECT id FROM chat.user WHERE name = 'Bob')),
('room4', (SELECT id FROM chat.user WHERE name = 'Mike')),
('room5', (SELECT id FROM chat.user WHERE name = 'John')),
('room6', (SELECT id FROM chat.user WHERE name = 'Maria'))
ON CONFLICT (name) DO NOTHING;

INSERT INTO chat.message(author, room, text) VALUES
((SELECT id FROM chat.user WHERE name = 'Maria'), (SELECT id FROM chat.chatroom WHERE name = 'room1'), 'text1'),
((SELECT id FROM chat.user WHERE name = 'Paul'), (SELECT id FROM chat.chatroom WHERE name = 'room2'), 'text2'),
((SELECT id FROM chat.user WHERE name = 'Bob'), (SELECT id FROM chat.chatroom WHERE name = 'room3'), 'text3'),
((SELECT id FROM chat.user WHERE name = 'Mike'), (SELECT id FROM chat.chatroom WHERE name = 'room4'), 'text4'),
((SELECT id FROM chat.user WHERE name = 'John'), (SELECT id FROM chat.chatroom WHERE name = 'room5'), 'text5'),
((SELECT id FROM chat.user WHERE name = 'Maria'), (SELECT id FROM chat.chatroom WHERE name = 'room2'), 'text6'),
((SELECT id FROM chat.user WHERE name = 'Maria'), (SELECT id FROM chat.chatroom WHERE name = 'room6'), 'text7')
ON CONFLICT DO NOTHING;

INSERT INTO chat.user_chatroom(user_id, chat_id) VALUES
((SELECT id FROM chat.user WHERE name = 'Maria'), (SELECT id FROM chat.chatroom WHERE name = 'room1')),
((SELECT id FROM chat.user WHERE name = 'Paul'), (SELECT id FROM chat.chatroom WHERE name = 'room2')),
((SELECT id FROM chat.user WHERE name = 'Maria'), (SELECT id FROM chat.chatroom WHERE name = 'room2')),
((SELECT id FROM chat.user WHERE name = 'Bob'), (SELECT id FROM chat.chatroom WHERE name = 'room3')),
((SELECT id FROM chat.user WHERE name = 'Mike'), (SELECT id FROM chat.chatroom WHERE name = 'room4')),
((SELECT id FROM chat.user WHERE name = 'John'), (SELECT id FROM chat.chatroom WHERE name = 'room5')),
((SELECT id FROM chat.user WHERE name = 'Maria'), (SELECT id FROM chat.chatroom WHERE name = 'room6'))
ON CONFLICT DO NOTHING;
